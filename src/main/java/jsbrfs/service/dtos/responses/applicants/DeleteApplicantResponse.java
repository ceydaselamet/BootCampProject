package jsbrfs.service.dtos.responses.applicants;

public class DeleteApplicantResponse {
    private Long id;
    private boolean success;

    public DeleteApplicantResponse(Long id, boolean success) {
        this.id = id;
        this.success = success;
    }
    
    public DeleteApplicantResponse(Long id) {
        this.id = id;
    }

    public DeleteApplicantResponse() {
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
