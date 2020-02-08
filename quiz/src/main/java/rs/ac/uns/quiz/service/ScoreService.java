package rs.ac.uns.quiz.service;

import rs.ac.uns.quiz.dto.ScoreDto;

import java.util.Date;
import java.util.List;

public interface ScoreService {

    void saveScore(String user, Date date);

    List<ScoreDto> getAllScores();

    void updateScore(Date date);


}
