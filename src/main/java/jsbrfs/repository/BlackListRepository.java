package jsbrfs.repository;

import jsbrfs.entity.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlackListRepository extends JpaRepository<BlackList, Long> {
    Optional<BlackList> findByApplicantId(Long applicantId);
}
