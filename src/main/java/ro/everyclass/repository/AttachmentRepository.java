package ro.everyclass.repository;

import ro.everyclass.entity.Attachment;
import ro.everyclass.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findByMessage(Message message);
}
