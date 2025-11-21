package it.telespazio.work.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "file",cascade = CascadeType.ALL)
    @Lazy
    private List<ContentFile> contentFile = new ArrayList<>();

    public void addContentFile(ContentFile contentFile) {
        contentFile.setFile(this);
        this.contentFile.add(contentFile);
    }

}
