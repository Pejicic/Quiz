package rs.ac.uns.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.quiz.dto.ScoreDto;
import rs.ac.uns.quiz.model.Answer;
import rs.ac.uns.quiz.model.CorrectAnswer;
import rs.ac.uns.quiz.model.Question;
import rs.ac.uns.quiz.repository.AnswerRepository;
import rs.ac.uns.quiz.repository.QuestionRepository;
import rs.ac.uns.quiz.service.ScoreService;
import rs.ac.uns.quiz.time.Time;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import static rs.ac.uns.quiz.model.Globals.*;

@RestController
@RequestMapping(path = "/score")

public class ScoreController {


    SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");


    @Autowired
    private ScoreService scoreService;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;


    private List<ScoreDto> finalList = new ArrayList<ScoreDto>();

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<ScoreDto>> getScore() {

        while (finalList.isEmpty()) {

        }

        return ResponseEntity.ok().body(finalList);
    }


    //
    @Scheduled(cron = CRON_RESULT, zone = "Europe/Paris")
    private void processScores() throws IOException, ParseException {
        finalList.clear();
        Date date = formater.parse(formater.format(Time.getTime()));

        List<Question> questions = questionRepository.findAllByDate(date);

        List<Answer> answers = questions.stream().map(question -> answerRepository.findFirstByQuestionAndIsAnswerCorrectOrderByTimeAsc(question, CorrectAnswer.CORRECT)).collect(Collectors.toList());

        answers.removeIf(Objects::isNull);


        Set<String> set = new HashSet<>();
        answers.stream().filter(p -> set.add(p.getPerson().getUsername())).collect(Collectors.toList());

        set.forEach(a -> scoreService.saveScore(a, date));

        scoreService.updateScore(date);

        finalList.addAll(scoreService.getAllScores());


    }


}
