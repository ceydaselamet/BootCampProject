package jsbrfs.service.dtos.responses.instructors;

public class DeleteInstructorResponse {
    private Long id;
    private boolean success;

    public DeleteInstructorResponse(Long id, boolean success) {
        this.id = id;
        this.success = success;
    }
    
    public DeleteInstructorResponse(Long id) {
        this.id = id;
    }

    public DeleteInstructorResponse() {
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
