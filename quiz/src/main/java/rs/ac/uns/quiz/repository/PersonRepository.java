package rs.ac.uns.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.quiz.model.Person;
import rs.ac.uns.quiz.model.Role;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
    Optional<List<Person>> findAllByRoleNot(Role role);
    Optional<Person> findPersonByUsername(String username);
    Optional<Person> findByUsernameAndPassword(String username,String password);
    Optional<Person> findPersonById(Long id);
}
