package uz.pdp.securitytest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String filename;
    private String contentType;
    private String filePath;
    private Long size;


    public Attachment(String fileName, String contentType, String filePath, long size) {
        this.filename = filename;
        this.contentType = contentType;
        this.filePath = filePath;
        this.size = size;
    }
}
