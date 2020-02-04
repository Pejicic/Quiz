package rs.ac.uns.quiz.service;

import rs.ac.uns.quiz.dto.ScoreDto;
import rs.ac.uns.quiz.model.Person;
import rs.ac.uns.quiz.model.Score;

import java.util.Date;
import java.util.List;

public interface ScoreService {

    void saveScore(Person user, Date date);

    List<ScoreDto> getAllScores();

    void updateScore(Date date);




}
