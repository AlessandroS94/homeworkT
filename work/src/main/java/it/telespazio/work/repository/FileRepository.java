package it.telespazio.work.repository;

import it.telespazio.work.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Projection for {@link it.telespazio.work.model.File}
 */
@Repository
public interface FileRepository extends JpaRepository<File, Long> {

}