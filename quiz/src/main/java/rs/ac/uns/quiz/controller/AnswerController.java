package rs.ac.uns.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import rs.ac.uns.quiz.dto.AnswerDto;
import rs.ac.uns.quiz.dto.QuestionDto;
import rs.ac.uns.quiz.exception.BadRequestException;
import rs.ac.uns.quiz.exception.ForbiddenException;
import rs.ac.uns.quiz.service.AnswerService;
import rs.ac.uns.quiz.service.PersonService;
import rs.ac.uns.quiz.service.QuestionService;
import rs.ac.uns.quiz.time.Time;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class AnswerController {


    final String CRON_QUESTIONS = "0 5 11 * * TUE";

    final String SEND_QUESTION = "*/61 * 11-13 * * TUE";


    @Value("${hours.login.start}")
    int HOURS_LOGIN_START;
    @Value("${minutes.login.start}")
    int MINUTES_LOGIN_START;
    @Value("${hours.login.end}")
    int HOURS_LOGIN_END;
    @Value("${minutes.login.end}")
    int MINUTES_LOGIN_END;


    @Autowired
    PersonService personService;

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    List<QuestionDto> weeklyQuestions = new ArrayList<QuestionDto>();

    SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");

    private int i = 0;

    @Autowired
    private SimpMessagingTemplate template;

    //pokrenuti jednom sedmicno prije pocetka kviza
    @Scheduled(cron = CRON_QUESTIONS, zone = "Europe/Paris")
    private void populateQuestions() throws IOException, ParseException {
        weeklyQuestions.clear();
        Date date = formater.parse(formater.format(Time.getTime()));

        weeklyQuestions.addAll(questionService.collectAllQuestionsforDate(date));
        for (QuestionDto question : weeklyQuestions
        ) {
            System.out.println("Id je: " + question.getId());

        }


    }


    @MessageMapping("/hello")
    public void receiveData(AnswerDto answerDto, Principal principal) throws InterruptedException {
        if (principal == null) {
            throw new ForbiddenException("Access denied");
        }
        System.out.println("Korisnicko je: " + principal.getName());
        boolean saved = answerService.saveAnswer(answerDto, principal.getName());
        if (saved == false) {
            throw new BadRequestException("Answer is not saved");
        }

    }


    @Scheduled(cron = SEND_QUESTION, zone = "Europe/Paris")
    public void sendData() throws InterruptedException, IOException {
        Date date = Time.getTime();
        System.out.println(date);
        if (weeklyQuestions.size() < i + 1) {
            return;
        }
        if (date.after(Time.getQuizDate(HOURS_LOGIN_END, MINUTES_LOGIN_END))) {
            System.out.println("scheduled");
            this.template.convertAndSend("/topic/greetings", weeklyQuestions.get(i));
            i++;
        }

    }

}