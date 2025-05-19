package jsbrfs.service.mappers;

import jsbrfs.entity.Application;
import jsbrfs.service.dtos.requests.applications.CreateApplicationRequest;
import jsbrfs.service.dtos.requests.applications.UpdateApplicationRequest;
import jsbrfs.service.dtos.responses.applicants.DeleteApplicantResponse;
import jsbrfs.service.dtos.responses.applications.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ApplicationMapper {
    ApplicationMapper INSTANCE = Mappers.getMapper(ApplicationMapper.class);

    @Mapping(source = "applicantId", target = "applicant.id")
    @Mapping(source = "bootcampId", target = "bootcamp.id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Application applicationFromCreateRequest(CreateApplicationRequest request);

    @Mapping(source = "applicantId", target = "applicant.id")
    @Mapping(source = "bootcampId", target = "bootcamp.id")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Application applicationFromUpdateRequest(UpdateApplicationRequest request);

    @Mapping(source = "applicant.id", target = "applicantId")
    @Mapping(source = "bootcamp.id", target = "bootcampId")
    CreateApplicationResponse createResponseFromApplication(Application application);

    @Mapping(source = "applicant.id", target = "applicantId")
    @Mapping(source = "bootcamp.id", target = "bootcampId")
    UpdateApplicationResponse updateResponseFromApplication(Application application);

    @Mapping(source = "applicant.id", target = "applicantId")
    @Mapping(source = "bootcamp.id", target = "bootcampId")
    GetByIdApplicationResponse getByIdResponseFromApplication(Application application);

    @Mapping(source = "applicant.id", target = "applicantId")
    @Mapping(source = "bootcamp.id", target = "bootcampId")
    GetListApplicationResponse getListResponseFromApplication(Application application);

    @Mapping(source = "applicant.id", target = "applicantId")
    @Mapping(source = "bootcamp.id", target = "bootcampId")
    @Mapping(target = "success", constant = "true")
    DeleteApplicationResponse deleteResponseFromApplication(Application application);
}
