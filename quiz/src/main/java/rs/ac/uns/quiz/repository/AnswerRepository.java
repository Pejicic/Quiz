package rs.ac.uns.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.quiz.model.Answer;
import rs.ac.uns.quiz.model.CorrectAnswer;
import rs.ac.uns.quiz.model.Person;
import rs.ac.uns.quiz.model.Question;

import java.util.Optional;


@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long> {

   Answer findFirstByQuestionAndIsAnswerCorrectOrderByTimeAsc(Question question, CorrectAnswer isAnswerCorrect);

   Optional<Answer> findByPersonAndQuestion(Person person, Question question);

}
