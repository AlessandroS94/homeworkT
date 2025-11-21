package it.telespazio.work.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilePayload {
    private String name;
    private String content;
}
