package uz.pdp.securitytest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.securitytest.entity.Attachment;

import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
  @Query("select a from Attachment a where a.filePath=:filePath")
    Optional<Attachment> findByFilePath(String filePath);
}
