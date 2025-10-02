package ro.everyclass.repository;

import ro.everyclass.entity.Report;
import ro.everyclass.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByReportedUser(User reportedUser);
    List<Report> findByReporter(User reporter);
}
