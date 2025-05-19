package jsbrfs.repository;

import jsbrfs.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByApplicantId(Long applicantId);

    @Query("select count(a) > 0 from Application a where a.applicant.id = :applicantId and a.bootcamp.id = :bootcampId")
    boolean existsByApplicantIdAndBootcampId(@Param("applicantId") Long applicantId, @Param("bootcampId") Long bootcampId);
}
