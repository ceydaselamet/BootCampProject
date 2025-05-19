package jsbrfs.service.abstracts;

import jsbrfs.service.dtos.requests.employees.CreateEmployeeRequest;
import jsbrfs.service.dtos.requests.employees.UpdateEmployeeRequest;
import jsbrfs.service.dtos.responses.employees.*;

import java.util.List;

public interface EmployeeService {
    CreateEmployeeResponse add(CreateEmployeeRequest request);
    UpdateEmployeeResponse update(UpdateEmployeeRequest request);
    DeleteEmployeeResponse softDelete(Long id);
    void delete(Long id);
    List<GetListEmployeeResponse> getAll();
    GetByIdEmployeeResponse getById(Long id);
}
