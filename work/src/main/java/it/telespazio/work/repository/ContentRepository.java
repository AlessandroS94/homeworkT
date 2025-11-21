package it.telespazio.work.repository;

import it.telespazio.work.model.ContentFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContentRepository extends JpaRepository<ContentFile, Long> {
    List<ContentFile> findByFileIdAndStartContentLessThanAndEndContentGreaterThanOrderByStartContent(
            Long fileId, Long end, Long start
    );
}