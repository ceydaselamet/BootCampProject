package jsbrfs.service.abstracts;

import jsbrfs.service.dtos.requests.blackLists.CreateBlackListRequest;
import jsbrfs.service.dtos.requests.blackLists.UpdateBlackListRequest;
import jsbrfs.service.dtos.responses.blackLists.*;

import java.util.List;

public interface BlackListService {
    CreateBlackListResponse add(CreateBlackListRequest request);
    UpdateBlackListResponse update(UpdateBlackListRequest request);
    DeleteBlackListResponse softDelete(Long id);
    void delete(Long id);

    GetByIdBlackListResponse getById(Long id);
    List<GetListBlackListResponse> getAll();

    boolean isApplicantInBlackList(Long applicantId);
}
