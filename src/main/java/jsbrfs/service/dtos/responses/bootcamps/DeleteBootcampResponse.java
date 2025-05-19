package jsbrfs.service.dtos.responses.bootcamps;

public class DeleteBootcampResponse {
    private Long id;
    private boolean success;

    public DeleteBootcampResponse(Long id, boolean success) {
        this.id = id;
        this.success = success;
    }
    
    public DeleteBootcampResponse(Long id) {
        this.id = id;
    }

    public DeleteBootcampResponse() {
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
