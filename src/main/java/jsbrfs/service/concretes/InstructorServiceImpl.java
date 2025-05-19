package jsbrfs.service.concretes;

import jsbrfs.entity.Instructor;
import jsbrfs.repository.InstructorRepository;
import jsbrfs.service.abstracts.InstructorService;
import jsbrfs.service.rules.InstructorBusinessRules;
import jsbrfs.service.dtos.requests.instructors.CreateInstructorRequest;
import jsbrfs.service.dtos.requests.instructors.UpdateInstructorRequest;
import jsbrfs.service.dtos.responses.instructors.CreateInstructorResponse;
import jsbrfs.service.dtos.responses.instructors.DeleteInstructorResponse;
import jsbrfs.service.dtos.responses.instructors.GetByIdInstructorResponse;
import jsbrfs.service.dtos.responses.instructors.GetListInstructorResponse;
import jsbrfs.service.dtos.responses.instructors.UpdateInstructorResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository repository;
    private final InstructorBusinessRules businessRules;

    public InstructorServiceImpl(InstructorRepository repository, InstructorBusinessRules businessRules) {
        this.repository = repository;
        this.businessRules = businessRules;
    }

    @Override
    public CreateInstructorResponse add(CreateInstructorRequest request) {
        businessRules.checkIfCompanyNameExists(request.getCompanyName());
        businessRules.checkIfNationalIdentityExists(request.getNationalIdentity());
        
        Instructor instructor = mapToInstructor(request);
        instructor = repository.save(instructor);
        return mapToCreateResponse(instructor);
    }

    @Override
    public UpdateInstructorResponse update(UpdateInstructorRequest request) {
        businessRules.checkIfInstructorExists(request.getId());
        
        Instructor instructor = repository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Instructor not found"));
        
        if (!instructor.getCompanyName().equals(request.getCompanyName())) {
            businessRules.checkIfCompanyNameExists(request.getCompanyName());
        }
        
        if (!instructor.getNationalIdentity().equals(request.getNationalIdentity())) {
            businessRules.checkIfNationalIdentityExists(request.getNationalIdentity());
        }

        updateInstructorFromRequest(instructor, request);
        instructor = repository.save(instructor);

        return mapToUpdateResponse(instructor);
    }

    @Override
    public DeleteInstructorResponse softDelete(Long id) {
        businessRules.checkIfInstructorExists(id);
        
        Instructor instructor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));
        
        instructor.setDeletedAt(java.time.LocalDateTime.now());
        instructor = repository.save(instructor);
        
        return new DeleteInstructorResponse(instructor.getId(), true);
    }

    @Override
    public void delete(Long id) {
        businessRules.checkIfInstructorExists(id);
        
        Instructor instructor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));
        repository.delete(instructor);
    }

    @Override
    public GetByIdInstructorResponse getById(Long id) {
        Instructor instructor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));
        return mapToGetByIdResponse(instructor);
    }

    @Override
    public List<GetListInstructorResponse> getAll() {
        return repository.findAll().stream()
                .map(this::mapToGetListResponse)
                .collect(Collectors.toList());
    }

    // Mapping methods

    private Instructor mapToInstructor(CreateInstructorRequest request) {
        Instructor instructor = new Instructor();
        instructor.setUserName(request.getUserName());
        instructor.setFirstName(request.getFirstName());
        instructor.setLastName(request.getLastName());
        instructor.setDateOfBirth(request.getDateOfBirth());
        instructor.setNationalIdentity(request.getNationalIdentity());
        instructor.setEmail(request.getEmail());
        instructor.setPassword(request.getPassword());
        instructor.setCompanyName(request.getCompanyName());
        return instructor;
    }

    private void updateInstructorFromRequest(Instructor instructor, UpdateInstructorRequest request) {
        instructor.setUserName(request.getUserName());
        instructor.setFirstName(request.getFirstName());
        instructor.setLastName(request.getLastName());
        instructor.setDateOfBirth(request.getDateOfBirth());
        instructor.setNationalIdentity(request.getNationalIdentity());
        instructor.setEmail(request.getEmail());
        instructor.setPassword(request.getPassword());
        instructor.setCompanyName(request.getCompanyName());
    }

    private CreateInstructorResponse mapToCreateResponse(Instructor instructor) {
        return new CreateInstructorResponse(
                instructor.getId(),
                instructor.getFirstName(),
                instructor.getLastName(),
                instructor.getEmail(),
                instructor.getCompanyName()
        );
    }

    private UpdateInstructorResponse mapToUpdateResponse(Instructor instructor) {
        return new UpdateInstructorResponse(
                instructor.getId(),
                instructor.getFirstName(),
                instructor.getLastName(),
                instructor.getEmail(),
                instructor.getCompanyName()
        );
    }

    private GetByIdInstructorResponse mapToGetByIdResponse(Instructor instructor) {
        return new GetByIdInstructorResponse(
                instructor.getId(),
                instructor.getFirstName(),
                instructor.getLastName(),
                instructor.getEmail(),
                instructor.getCompanyName()
        );
    }

    private GetListInstructorResponse mapToGetListResponse(Instructor instructor) {
        return new GetListInstructorResponse(
                instructor.getId(),
                instructor.getFirstName(),
                instructor.getLastName(),
                instructor.getEmail(),
                instructor.getCompanyName()
        );
    }
}
