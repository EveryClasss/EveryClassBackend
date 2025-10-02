package ro.everyclass.repository;

import ro.everyclass.entity.TeacherProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherProfileRepository extends JpaRepository<TeacherProfile, Long> {
    List<TeacherProfile> findByCityIgnoreCase(String city);
    List<TeacherProfile> findByCountyIgnoreCase(String county);
    List<TeacherProfile> findByPricePerHourBetween(Double min, Double max);
}
