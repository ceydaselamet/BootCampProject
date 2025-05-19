package jsbrfs.service.concretes;

import jsbrfs.entity.BlackList;
import jsbrfs.repository.BlackListRepository;
import jsbrfs.service.abstracts.BlackListService;
import jsbrfs.service.dtos.requests.blackLists.CreateBlackListRequest;
import jsbrfs.service.dtos.requests.blackLists.UpdateBlackListRequest;
import jsbrfs.service.dtos.responses.blackLists.*;
import jsbrfs.service.mappers.BlackListMapper;
import jsbrfs.service.rules.BlackListBusinessRules;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlackListServiceImpl implements BlackListService {
    private final BlackListRepository repository;
    private final BlackListBusinessRules businessRules;

    public BlackListServiceImpl(BlackListRepository repository, BlackListBusinessRules businessRules) {
        this.repository = repository;
        this.businessRules = businessRules;
    }


    @Override
    public CreateBlackListResponse add(CreateBlackListRequest request) {
        businessRules.checkIfUserAlreadyInBlackList(request.applicantId());
        businessRules.checkIfReasonEmpty(request.reason());

        BlackList blackList = BlackListMapper.INSTANCE.blackListFromCreateRequest(request);
        blackList = repository.save(blackList);
        return BlackListMapper.INSTANCE.createResponseFromBlackList(blackList);
    }

    @Override
    public UpdateBlackListResponse update(UpdateBlackListRequest request) {
        BlackList existing = repository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("BlackList not found by id: " + request.id()));

        businessRules.checkIfUserAlreadyInBlackList(request.applicantId());
        businessRules.checkIfReasonEmpty(request.reason());

        BlackList updated = BlackListMapper.INSTANCE.blackListFromUpdateRequest(request);
        updated.setId(existing.getId());
        BlackList saved = repository.save(updated);

        return BlackListMapper.INSTANCE.updateResponseFromBlackList(saved);
    }

    @Override
    public DeleteBlackListResponse softDelete(Long id) {
        BlackList blackList = repository.findById(id).orElseThrow(() -> new RuntimeException("Black List Not Found"));
        blackList.setDeletedAt(LocalDateTime.now());
        BlackList deletedBlackList = repository.save(blackList);

        DeleteBlackListResponse response = BlackListMapper.INSTANCE.deletedBlackListResponseFromBlackList(deletedBlackList);
        return response;
    }

    @Override
    public void delete(Long id) {
        BlackList blackList = repository.findById(id).orElseThrow(() -> new RuntimeException("BlackList not found by id:"+ id));

        repository.delete(blackList);
    }

    @Override
    public GetByIdBlackListResponse getById(Long id) {
        BlackList blackList = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("BlackList not found by id: " + id));
        return BlackListMapper.INSTANCE.getByIdResponseFromBlackList(blackList);
    }

    @Override
    public List<GetListBlackListResponse> getAll() {
        return repository.findAll().stream()
                .map(BlackListMapper.INSTANCE::getListResponseFromBlackList)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isApplicantInBlackList(Long applicantId) {
        return repository.findByApplicantId(applicantId).isPresent();
    }
}
