package jsbrfs.service.concretes;

import jsbrfs.entity.Application;
import jsbrfs.repository.ApplicationRepository;
import jsbrfs.service.abstracts.ApplicationService;
import jsbrfs.service.dtos.requests.applications.CreateApplicationRequest;
import jsbrfs.service.dtos.requests.applications.UpdateApplicationRequest;
import jsbrfs.service.dtos.responses.applications.*;
import jsbrfs.service.mappers.ApplicationMapper;
import jsbrfs.service.rules.ApplicationBusinessRules;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository repository;
    private final ApplicationBusinessRules businessRules;

    public ApplicationServiceImpl(ApplicationRepository repository, ApplicationBusinessRules businessRules) {
        this.repository = repository;
        this.businessRules = businessRules;
    }

    @Override
    public CreateApplicationResponse add(CreateApplicationRequest request) {
        businessRules.checkIfApplicantAlreadyAppliedToBootcamp(request.applicantId(), request.bootcampId());
        businessRules.checkIfBootcampActive(request.bootcampId());
        businessRules.checkIfApplicantInBlackList(request.applicantId());

        Application application = ApplicationMapper.INSTANCE.applicationFromCreateRequest(request);
        application = repository.save(application);
        return ApplicationMapper.INSTANCE.createResponseFromApplication(application);
    }

    @Override
    public UpdateApplicationResponse update(UpdateApplicationRequest request) {
        Application existing = repository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("Application not found by id: " + request.id()));
        
        if (!existing.getBootcamp().getId().equals(request.bootcampId())) {
            businessRules.checkIfBootcampActive(request.bootcampId());
            businessRules.checkIfApplicantAlreadyAppliedToBootcamp(request.applicantId(), request.bootcampId());
        }
        
        if (!existing.getApplicant().getId().equals(request.applicantId())) {
            businessRules.checkIfApplicantInBlackList(request.applicantId());
            businessRules.checkIfApplicantAlreadyAppliedToBootcamp(request.applicantId(), request.bootcampId());
        }

        Application updated = ApplicationMapper.INSTANCE.applicationFromUpdateRequest(request);
        updated.setId(existing.getId());
        Application saved = repository.save(updated);

        return ApplicationMapper.INSTANCE.updateResponseFromApplication(saved);
    }

    @Override
    public DeleteApplicationResponse softDelete(Long id) {
        Application application = repository.findById(id).orElseThrow(() -> new RuntimeException("Application not found"));
        
        application.setDeletedAt(LocalDateTime.now());
        Application deletedApplication = repository.save(application);
        DeleteApplicationResponse response = ApplicationMapper.INSTANCE.deleteResponseFromApplication(deletedApplication);
        return response;
    }

    @Override
    public void delete(Long id) {
        Application application = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found by id: " + id));

        repository.delete(application);
    }

    @Override
    public GetByIdApplicationResponse getById(Long id) {
        Application application = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found by id: " + id));
        return ApplicationMapper.INSTANCE.getByIdResponseFromApplication(application);
    }

    @Override
    public List<GetListApplicationResponse> getAll() {
        return repository.findAll().stream()
                .map(ApplicationMapper.INSTANCE::getListResponseFromApplication)
                .collect(Collectors.toList());
    }

    @Override
    public List<GetListApplicationResponse> getByApplicantId(Long applicantId) {
        return repository.findByApplicantId(applicantId).stream()
                .map(ApplicationMapper.INSTANCE::getListResponseFromApplication)
                .collect(Collectors.toList());
    }
}
