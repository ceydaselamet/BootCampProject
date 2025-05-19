package jsbrfs.service.abstracts;

import jsbrfs.entity.enums.BootcampState;
import jsbrfs.service.dtos.requests.bootcamps.CreateBootcampRequest;
import jsbrfs.service.dtos.requests.bootcamps.UpdateBootcampRequest;
import jsbrfs.service.dtos.responses.bootcamps.*;

import java.util.List;

public interface BootcampService {
    CreateBootcampResponse add(CreateBootcampRequest request);
    UpdateBootcampResponse update(UpdateBootcampRequest request);
    DeleteBootcampResponse softDelete(Long id);
    void delete(Long id);

    GetByIdBootcampResponse getById(Long id);
    List<GetListBootcampResponse> getAll();
    List<GetListBootcampResponse> getByState(BootcampState state);
}
