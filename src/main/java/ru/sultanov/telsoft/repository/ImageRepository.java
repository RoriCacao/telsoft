package ru.sultanov.telsoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sultanov.telsoft.entity.ImageModel;
import ru.sultanov.telsoft.entity.Person;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageModel,Long> {

    Optional<ImageModel> findByPerson (Person person);

}
