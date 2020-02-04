package rs.ac.uns.quiz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.quiz.dto.ScoreDto;
import rs.ac.uns.quiz.model.Answer;
import rs.ac.uns.quiz.model.CorrectAnswer;
import rs.ac.uns.quiz.model.Person;
import rs.ac.uns.quiz.model.Score;
import rs.ac.uns.quiz.repository.AnswerRepository;
import rs.ac.uns.quiz.repository.QuestionRepository;
import rs.ac.uns.quiz.repository.ScoreRepository;
import rs.ac.uns.quiz.service.ScoreService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private ScoreRepository scoreRepository;


    @Override
    public void saveScore(Person user, Date date) {

        List<Answer> answers = questionRepository.findAllByDate(date).stream().map(question -> answerRepository.findAllByPersonAndQuestion(user, question)).collect(Collectors.toList());

        double score = 0;

        for (Answer a : answers) {

            score = score + a.getPoints();

        }

        Score s = new Score();
        s.setDate(date);
        s.setBonus(0);
        s.setUser(user);
        s.setFinalScore(score);
        s.setScorePlusBonus(score);

        scoreRepository.save(s);

    }

    @Override
    public List<ScoreDto> getAllScores() {
        return scoreRepository.findAllByOrderByScorePlusBonusDesc().stream().map(score -> scoreToScoreDto(score)).collect(Collectors.toList());
    }

    @Override
    public void updateScore(Date date) {
        List<Answer> answers = questionRepository.findAllByDate(date).stream().map(question -> answerRepository.findFirstByQuestionAndIsAnswerCorrectOrderByTimeAsc(question, CorrectAnswer.CORRECT)).collect(Collectors.toList());
        for (Answer a:answers) {

            Score s=scoreRepository.findScoreByPersonAndDate(a.getPerson(),date);
            int bonus=s.getBonus();
            s.setBonus(bonus+1);
            s.setScorePlusBonus(s.getFinalScore()+s.getBonus());
            scoreRepository.save(s);

        }

    }


    private ScoreDto scoreToScoreDto(Score score){

        ScoreDto scoreDto=new ScoreDto();
        scoreDto.setUsername(score.getUser().getUsername());
        scoreDto.setBonus(score.getBonus());
        scoreDto.setPoints(score.getFinalScore());
        scoreDto.setPointsPlusBonus(score.getScorePlusBonus());
        return scoreDto;


    }
}
