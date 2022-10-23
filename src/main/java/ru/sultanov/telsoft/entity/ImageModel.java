package ru.sultanov.telsoft.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

//Модель изображения пользователя
@Data
@Entity
public class ImageModel {

    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @OneToOne
    @JoinColumn(name = "person_id",referencedColumnName = "id")
    private Person person;

    //Изображение в виде массива байт
    @Column(columnDefinition = "BYTEA")
    private byte[] imageBytes;
}
