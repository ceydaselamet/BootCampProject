package jsbrfs.service.rules;

import jsbrfs.core.exceptions.types.BusinessException;
import jsbrfs.entity.BlackList;
import jsbrfs.repository.BlackListRepository;

public class BlackListBusinessRules {
    private final BlackListRepository repository;

    public BlackListBusinessRules(BlackListRepository repository) {
        this.repository = repository;
    }

    public void checkIfUserAlreadyInBlackList(long applicantId){
        boolean exists = repository.findByApplicantId(applicantId).isPresent();
        if(exists){
            throw new BusinessException("This applicant already has an active blacklist record");
        }
    }

    public void checkIfReasonEmpty(String reason){
        if(reason == null || reason.trim().isEmpty()){
            throw new BusinessException("Reason can not be empty");
        }
    }
}
