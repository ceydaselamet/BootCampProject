package jsbrfs.service.rules;

import jsbrfs.core.exceptions.types.BusinessException;
import jsbrfs.entity.enums.ApplicationState;
import jsbrfs.entity.enums.BootcampState;
import jsbrfs.repository.ApplicationRepository;
import jsbrfs.service.abstracts.BlackListService;
import jsbrfs.service.abstracts.BootcampService;
import jsbrfs.service.dtos.responses.bootcamps.GetByIdBootcampResponse;

public class ApplicationBusinessRules {
    private final ApplicationRepository repository;
    private final BootcampService bootcampService;
    private final BlackListService blackListService;

    public ApplicationBusinessRules(ApplicationRepository repository, BootcampService bootcampService, BlackListService blackListService) {
        this.repository = repository;
        this.bootcampService = bootcampService;
        this.blackListService = blackListService;
    }

    public void checkIfBootcampActive(Long bootcampId){
        GetByIdBootcampResponse bootcamp = bootcampService.getById(bootcampId);
        if(bootcamp == null) {
            throw new BusinessException("Bootcamp not found");
        }
        
        if(!bootcamp.bootcampState().equals(BootcampState.OPEN_FOR_APPLICATION)){
            throw new BusinessException("Bootcamp is not active");
        }
    }

    public void checkIfApplicantAlreadyAppliedToBootcamp(Long applicantId, Long bootcampId){
        boolean exists = repository.existsByApplicantIdAndBootcampId(applicantId, bootcampId);
        if(exists)
            throw new BusinessException("This applicant already applied To This Bootcamp");
    }

    public void checkIfApplicantInBlackList(Long applicantId){
        if(blackListService.isApplicantInBlackList(applicantId))
            throw new BusinessException("This applicant in black list.");
    }

    public void checkValidApplicationStateTransition(ApplicationState currentState, ApplicationState newState){
        boolean isValidTransition = false;
        
        switch (currentState) {
            case PENDING:
                isValidTransition = newState == ApplicationState.APPROVED || 
                                   newState == ApplicationState.REJECTED || 
                                   newState == ApplicationState.IN_REVIEW || 
                                   newState == ApplicationState.CANCELLED;
                break;
            case IN_REVIEW:
                isValidTransition = newState == ApplicationState.APPROVED || 
                                   newState == ApplicationState.REJECTED || 
                                   newState == ApplicationState.CANCELLED;
                break;
            case APPROVED:
                isValidTransition = newState == ApplicationState.CANCELLED;
                break;
            case REJECTED:
                isValidTransition = newState == ApplicationState.CANCELLED;
                break;
            case CANCELLED:
                isValidTransition = false;
                break;
        }
        
        if (!isValidTransition) {
            throw new BusinessException("The " + currentState + " → " + newState + " transition is not allowed.");
        }
    }


}
