package jsbrfs.service.dtos.responses.blackLists;

public class DeleteBlackListResponse {
    private Long id;
    private Long applicantId;
    private boolean success;

    public DeleteBlackListResponse(Long id, Long applicantId, boolean success) {
        this.id = id;
        this.applicantId = applicantId;
        this.success = success;
    }
    
    public DeleteBlackListResponse(Long id) {
        this.id = id;
    }

    public DeleteBlackListResponse() {
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
    
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
