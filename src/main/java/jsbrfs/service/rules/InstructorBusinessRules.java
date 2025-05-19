package jsbrfs.service.rules;

import jsbrfs.core.exceptions.types.BusinessException;
import jsbrfs.entity.Instructor;
import jsbrfs.repository.InstructorRepository;

import java.util.Optional;

public class InstructorBusinessRules {
    private final InstructorRepository repository;

    public InstructorBusinessRules(InstructorRepository repository) {
        this.repository = repository;
    }

    public void checkIfInstructorExists(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("Instructor not found in the system.");
        }
    }
    
    public void checkIfCompanyNameExists(String companyName) {
        Optional<Instructor> existingInstructor = repository.findByCompanyName(companyName);
        if (existingInstructor.isPresent()) {
            throw new BusinessException("An instructor with this company name already exists.");
        }
    }
    
    public void checkIfNationalIdentityExists(String nationalIdentity) {
        Optional<Instructor> existingInstructor = repository.findByNationalIdentity(nationalIdentity);
        if (existingInstructor.isPresent()) {
            throw new BusinessException("An instructor with this national identity already exists.");
        }
    }
}
