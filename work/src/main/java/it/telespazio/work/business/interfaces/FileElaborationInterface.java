package it.telespazio.work.business.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface FileElaborationInterface {
    void saveFile(MultipartFile file) throws IOException;
    String getFile(Long id, Long start, Long end);
}
