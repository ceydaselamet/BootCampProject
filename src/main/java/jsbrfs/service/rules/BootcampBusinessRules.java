package jsbrfs.service.rules;

import jsbrfs.core.exceptions.types.BusinessException;
import jsbrfs.entity.Bootcamp;
import jsbrfs.entity.enums.BootcampState;
import jsbrfs.repository.BootcampRepository;
import jsbrfs.service.abstracts.InstructorService;
import jsbrfs.service.dtos.responses.instructors.GetByIdInstructorResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class BootcampBusinessRules {
    private final BootcampRepository repository;
    private final InstructorService instructorService;

    public BootcampBusinessRules(BootcampRepository repository, InstructorService instructorService) {
        this.repository = repository;
        this.instructorService = instructorService;
    }

    public void checkIfTheStartDateIsBeforeTheEndDate(LocalDate startDate, LocalDate endDate){
        if(startDate.isAfter(endDate))
            throw new BusinessException("Start date must be before end date");
    }

    public void checkIfNameExists(String name){
        Optional<Bootcamp> existingBootcamp = repository.findByName(name);
        if(existingBootcamp.isPresent())
            throw new BusinessException("A bootcamp with the same name already exists.");
    }

    public void checkIfBootcampStateIsClosed(BootcampState bootcampState){
        if(bootcampState.equals(BootcampState.CANCELLED))
            throw new BusinessException("You can not apply to a bootcamp that is closed.");
    }
    
    public void checkIfBootcampIsOpenForApplication(BootcampState bootcampState) {
        if(!bootcampState.equals(BootcampState.OPEN_FOR_APPLICATION))
            throw new BusinessException("Applications can only be made to bootcamps that are open for application.");
    }

    public void checkIfInstructorExists(Long instructorId){
        GetByIdInstructorResponse instructor = instructorService.getById(instructorId);
        if(instructor == null)
            throw new BusinessException("The instructor is not registered in the system.");
    }

}
