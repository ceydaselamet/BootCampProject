package jsbrfs.service.rules;

import jsbrfs.core.exceptions.types.BusinessException;
import jsbrfs.entity.Applicant;
import jsbrfs.repository.ApplicantRepository;
import jsbrfs.service.abstracts.BlackListService;

import java.util.Optional;

public class ApplicantBusinessRules {
    private final ApplicantRepository repository;
    private final BlackListService blackListService;

    public ApplicantBusinessRules(ApplicantRepository repository, BlackListService blackListService) {
        this.repository = repository;
        this.blackListService = blackListService;
    }

    public void checkIfNationalIdentityExists(String nationalIdentity){
        Optional<Applicant> existingApplicant = repository.findByNationalIdentity(nationalIdentity);
        if(existingApplicant.isPresent())
            throw new BusinessException("A user with this national identity has already applied.");
    }

    public void checkIfApplicantInBlackList(Long applicantId){
        if(blackListService.isApplicantInBlackList(applicantId))
            throw new BusinessException("This applicant in black list.");
    }
    
    public void checkIfApplicantExists(Long applicantId) {
        if(!repository.existsById(applicantId))
            throw new BusinessException("Applicant not found in the system.");
    }
}
