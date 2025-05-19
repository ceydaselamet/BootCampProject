package jsbrfs.repository;

import jsbrfs.entity.Bootcamp;
import jsbrfs.entity.enums.BootcampState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BootcampRepository extends JpaRepository<Bootcamp, Long> {
    List<Bootcamp> findByBootcampState(BootcampState bootcampState);

    @Query(value = "select * from bootcamps where name =?1", nativeQuery = true)
    Optional<Bootcamp> findByName(String name);
}
