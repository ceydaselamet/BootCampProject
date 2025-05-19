package jsbrfs.service.abstracts;

import jsbrfs.service.dtos.requests.instructors.CreateInstructorRequest;
import jsbrfs.service.dtos.requests.instructors.UpdateInstructorRequest;
import jsbrfs.service.dtos.responses.instructors.*;

import java.util.List;

public interface InstructorService {
    CreateInstructorResponse add(CreateInstructorRequest request);
    UpdateInstructorResponse update(UpdateInstructorRequest request);
    DeleteInstructorResponse softDelete(Long id);
    void delete(Long id);
    List<GetListInstructorResponse> getAll();
    GetByIdInstructorResponse getById(Long id);
}
