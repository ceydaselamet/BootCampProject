package jsbrfs.service.concretes;

import jsbrfs.entity.Bootcamp;
import jsbrfs.entity.enums.BootcampState;
import jsbrfs.repository.BootcampRepository;
import jsbrfs.service.abstracts.BootcampService;
import jsbrfs.service.rules.BootcampBusinessRules;
import jsbrfs.service.dtos.requests.bootcamps.CreateBootcampRequest;
import jsbrfs.service.dtos.requests.bootcamps.UpdateBootcampRequest;
import jsbrfs.service.dtos.responses.bootcamps.CreateBootcampResponse;
import jsbrfs.service.dtos.responses.bootcamps.DeleteBootcampResponse;
import jsbrfs.service.dtos.responses.bootcamps.GetByIdBootcampResponse;
import jsbrfs.service.dtos.responses.bootcamps.GetListBootcampResponse;
import jsbrfs.service.dtos.responses.bootcamps.UpdateBootcampResponse;
import jsbrfs.service.mappers.BootcampMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BootcampServiceImpl implements BootcampService {
    private final BootcampRepository repository;
    private final BootcampBusinessRules businessRules;

    public BootcampServiceImpl(BootcampRepository repository, BootcampBusinessRules businessRules) {
        this.repository = repository;
        this.businessRules = businessRules;
    }

    @Override
    public CreateBootcampResponse add(CreateBootcampRequest request) {
        businessRules.checkIfNameExists(request.name());
        businessRules.checkIfTheStartDateIsBeforeTheEndDate(request.startDate(), request.endDate());
        businessRules.checkIfInstructorExists(request.instructorId());
        
        Bootcamp bootcamp = BootcampMapper.INSTANCE.bootcampFromCreateRequest(request);
        bootcamp = repository.save(bootcamp);
        return BootcampMapper.INSTANCE.createResponseFromBootcamp(bootcamp);
    }

    @Override
    public UpdateBootcampResponse update(UpdateBootcampRequest request) {
        Bootcamp existing = repository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("Bootcamp not found by id: " + request.id()));
        
        if (!existing.getName().equals(request.name())) {
            businessRules.checkIfNameExists(request.name());
        }
        
        businessRules.checkIfTheStartDateIsBeforeTheEndDate(request.startDate(), request.endDate());
        businessRules.checkIfInstructorExists(request.instructorId());

        Bootcamp updated = BootcampMapper.INSTANCE.bootcampFromUpdateRequest(request);
        updated.setId(existing.getId());
        Bootcamp saved = repository.save(updated);

        return BootcampMapper.INSTANCE.updateResponseFromBootcamp(saved);
    }

    @Override
    public DeleteBootcampResponse softDelete(Long id) {
        Bootcamp bootcamp = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bootcamp not found by id: " + id));
        
        bootcamp.setDeletedAt(java.time.LocalDateTime.now());
        bootcamp = repository.save(bootcamp);
        
        return new DeleteBootcampResponse(bootcamp.getId(), true);
    }
    
    @Override
    public void delete(Long id) {
        Bootcamp bootcamp = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bootcamp not found by id: " + id));

        repository.delete(bootcamp);
    }

    @Override
    public GetByIdBootcampResponse getById(Long id) {
        Bootcamp bootcamp = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bootcamp not found by id: " + id));
        return BootcampMapper.INSTANCE.getByIdResponseFromBootcamp(bootcamp);
    }

    @Override
    public List<GetListBootcampResponse> getAll() {
        return repository.findAll().stream()
                .map(BootcampMapper.INSTANCE::getListResponseFromBootcamp)
                .collect(Collectors.toList());
    }

    @Override
    public List<GetListBootcampResponse> getByState(BootcampState state) {
        return repository.findByBootcampState(state).stream()
                .map(BootcampMapper.INSTANCE::getListResponseFromBootcamp)
                .collect(Collectors.toList());
    }
}
