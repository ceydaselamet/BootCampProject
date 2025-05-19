package jsbrfs.repository;

import jsbrfs.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    Optional<Instructor> findByCompanyName(String companyName);
    Optional<Instructor> findByNationalIdentity(String nationalIdentity);
}
