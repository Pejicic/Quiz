package rs.ac.uns.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.quiz.model.Question;

import java.util.Date;
import java.util.List;


@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

    List<Question> findAllByDate(Date d);
}
