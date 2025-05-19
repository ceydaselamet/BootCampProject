package jsbrfs.service.dtos.responses.applications;

public class DeleteApplicationResponse {
    private Long id;
    private Long applicantId;
    private Long bootcampId;
    private boolean success;

    public DeleteApplicationResponse(Long id, Long applicantId, Long bootcampId, boolean success) {
        this.id = id;
        this.applicantId = applicantId;
        this.bootcampId = bootcampId;
        this.success = success;
    }
    
    public DeleteApplicationResponse(Long id) {
        this.id = id;
    }

    public DeleteApplicationResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public Long getBootcampId() {
        return bootcampId;
    }

    public void setBootcampId(Long bootcampId) {
        this.bootcampId = bootcampId;
    }
    
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
