package ru.sultanov.telsoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sultanov.telsoft.entity.Person;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Person,Long> {

    Optional<Person> findPersonByUsername(String username);

    Optional<Person> findPersonByEmail(String email);

    Optional<Person> findPersonById(Long id);
}
