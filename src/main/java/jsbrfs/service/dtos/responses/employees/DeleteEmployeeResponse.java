package jsbrfs.service.dtos.responses.employees;

public class DeleteEmployeeResponse {
    private Long id;
    private boolean success;

    public DeleteEmployeeResponse(Long id, boolean success) {
        this.id = id;
        this.success = success;
    }
    
    public DeleteEmployeeResponse(Long id) {
        this.id = id;
    }

    public DeleteEmployeeResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
