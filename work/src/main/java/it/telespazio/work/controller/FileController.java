package it.telespazio.work.controller;

import it.telespazio.work.business.interfaces.FileElaborationInterface;
import it.telespazio.work.dto.response.FilePayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
public class FileController {
    private final FileElaborationInterface fileElaborationInterface;

    public FileController(FileElaborationInterface fileElaborationInterface) {
        this.fileElaborationInterface = fileElaborationInterface;
    }

    @PostMapping("")
    public ResponseEntity<FilePayload> uploadFile(@RequestBody MultipartFile fileToUpload) {
        try {
            fileElaborationInterface.saveFile(fileToUpload);
            return new ResponseEntity<>(new FilePayload("Status","OK"), HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(new FilePayload("Status","FAIL"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/elaborate/{id}/{start}/{end}")
    private ResponseEntity<?> getByte(@PathVariable Long id, @PathVariable Long start, @PathVariable Long end){
        String stringFile = fileElaborationInterface.getFile(id, start, end);
        return new ResponseEntity<>(new FilePayload("Result",stringFile), HttpStatus.OK);
    }
}

