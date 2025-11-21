package it.telespazio.work.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ContentFile", indexes = {
        @Index(name = "idx_contentfile_id", columnList = "id, startContent, endContent")
})
public class ContentFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Long startContent;

    private Long endContent;

    private Long lengthContent;

    @Column(columnDefinition = "BYTEA")
    private byte[] content;

    @ManyToOne
    @JoinColumn
    private File file;

}