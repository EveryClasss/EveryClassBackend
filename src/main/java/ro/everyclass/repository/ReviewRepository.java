package ro.everyclass.repository;

import ro.everyclass.entity.Review;
import ro.everyclass.entity.TeacherProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByTeacher(TeacherProfile teacher);
}
