package rs.ac.uns.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.quiz.model.Person;
import rs.ac.uns.quiz.model.Score;

import java.util.Date;
import java.util.List;


@Repository
public interface ScoreRepository extends JpaRepository<Score,Long> {

    List<Score> findAllByOrderByScorePlusBonusDesc();

    Score findScoreByPersonAndDate(Person person, Date date);
}
