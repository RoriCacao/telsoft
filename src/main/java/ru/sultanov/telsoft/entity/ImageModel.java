package ru.sultanov.telsoft.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ImageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @OneToOne
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Person person;

    //@Lob
    @Column(columnDefinition = "BYTEA")
    private byte[] imageBytes;
}
