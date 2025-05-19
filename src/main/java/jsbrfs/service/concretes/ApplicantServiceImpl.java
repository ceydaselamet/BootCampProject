package jsbrfs.service.concretes;

import jsbrfs.entity.Applicant;
import jsbrfs.repository.ApplicantRepository;
import jsbrfs.service.abstracts.ApplicantService;
import jsbrfs.service.dtos.requests.applicants.CreateApplicantRequest;
import jsbrfs.service.dtos.requests.applicants.UpdateApplicantRequest;
import jsbrfs.service.dtos.responses.applicants.*;
import jsbrfs.service.rules.ApplicantBusinessRules;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final ApplicantBusinessRules businessRules;

    public ApplicantServiceImpl(ApplicantRepository applicantRepository, ApplicantBusinessRules businessRules) {
        this.applicantRepository = applicantRepository;
        this.businessRules = businessRules;
    }

    @Override
    public CreateApplicantResponse add(CreateApplicantRequest request) {
        businessRules.checkIfNationalIdentityExists(request.getNationalIdentity());
        
        Applicant applicant = new Applicant();

        applicant.setUserName(request.getUserName());
        applicant.setFirstName(request.getFirstName());
        applicant.setLastName(request.getLastName());
        applicant.setDateOfBirth(request.getDateOfBirth());
        applicant.setNationalIdentity(request.getNationalIdentity());
        applicant.setEmail(request.getEmail());
        applicant.setPassword(request.getPassword());

        applicant.setAbout(request.getAbout());

        applicant = applicantRepository.save(applicant);

        return mapToCreateApplicantResponse(applicant);
    }

    @Override
    public UpdateApplicantResponse update(UpdateApplicantRequest request) {
        businessRules.checkIfApplicantExists(request.getId());
        
        Applicant existingApplicant = applicantRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Applicant not found"));
        
        if (!existingApplicant.getNationalIdentity().equals(request.getNationalIdentity())) {
            businessRules.checkIfNationalIdentityExists(request.getNationalIdentity());
        }
        
        existingApplicant.setUserName(request.getUserName());
        existingApplicant.setFirstName(request.getFirstName());
        existingApplicant.setLastName(request.getLastName());

        existingApplicant.setDateOfBirth(request.getDateOfBirth());
        existingApplicant.setNationalIdentity(request.getNationalIdentity());
        existingApplicant.setEmail(request.getEmail());
        existingApplicant.setPassword(request.getPassword());

        existingApplicant.setAbout(request.getAbout());

        existingApplicant = applicantRepository.save(existingApplicant);

        return mapToUpdateApplicantResponse(existingApplicant);
    }

    @Override
    public DeleteApplicantResponse softDelete(Long id) {
        businessRules.checkIfApplicantExists(id);
        
        Applicant applicant = applicantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Applicant not found"));
        
        applicant.setDeletedAt(java.time.LocalDateTime.now());
        applicant = applicantRepository.save(applicant);
        
        return new DeleteApplicantResponse(applicant.getId(), true);
    }

    @Override
    public void delete(Long id) {
        businessRules.checkIfApplicantExists(id);
        
        Applicant applicant = applicantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Applicant not found"));
        applicantRepository.delete(applicant);
    }

    @Override
    public GetByIdApplicantResponse getById(Long id) {
        Applicant applicant = applicantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Applicant not found"));

        return mapToGetByIdApplicantResponse(applicant);
    }

    @Override
    public List<GetListApplicantResponse> getAll() {
        return applicantRepository.findAll().stream()
                .map(this::mapToGetListApplicantResponse)
                .collect(Collectors.toList());
    }

    // Mapping methods
    private CreateApplicantResponse mapToCreateApplicantResponse(Applicant applicant) {
        return new CreateApplicantResponse(
                applicant.getId(),
                applicant.getUserName(),
                applicant.getFirstName(),
                applicant.getLastName(),
                applicant.getDateOfBirth(),
                applicant.getNationalIdentity(),
                applicant.getEmail(),
                applicant.getAbout()
        );
    }

    private UpdateApplicantResponse mapToUpdateApplicantResponse(Applicant applicant) {
        return new UpdateApplicantResponse(
                applicant.getId(),
                applicant.getUserName(),
                applicant.getFirstName(),
                applicant.getLastName(),
                applicant.getDateOfBirth(),
                applicant.getNationalIdentity(),
                applicant.getEmail(),
                applicant.getAbout()
        );
    }

    private GetListApplicantResponse mapToGetListApplicantResponse(Applicant applicant) {
        return new GetListApplicantResponse(
                applicant.getId(),
                applicant.getUserName(),
                applicant.getFirstName(),
                applicant.getLastName(),
                applicant.getDateOfBirth(),
                applicant.getNationalIdentity(),
                applicant.getEmail(),
                applicant.getAbout()
        );
    }

    private GetByIdApplicantResponse mapToGetByIdApplicantResponse(Applicant applicant) {
        return new GetByIdApplicantResponse(
                applicant.getId(),
                applicant.getUserName(),
                applicant.getFirstName(),
                applicant.getLastName(),
                applicant.getDateOfBirth(),
                applicant.getNationalIdentity(),
                applicant.getEmail(),
                applicant.getAbout()
        );
    }
}


