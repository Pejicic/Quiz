package rs.ac.uns.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import static rs.ac.uns.quiz.model.Globals.*;

@Controller
public class AnswerController {



    @Autowired
    PersonService personService;

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    List<QuestionDto> weeklyQuestions = new ArrayList<QuestionDto>();

    List<Date> dates=new ArrayList<Date>();

    SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");

    private int i = 0;

    @Autowired
    private SimpMessagingTemplate template;

    //pokrenuti jednom sedmicno prije pocetka kviza
    @Scheduled(cron = CRON_QUESTIONS, zone = "Europe/Paris")
    private void populateQuestions() throws IOException, ParseException {
        weeklyQuestions.clear();
        dates.clear();
        i=0;
        Date date = formater.parse(formater.format(Time.getTime()));

        weeklyQuestions.addAll(questionService.collectAllQuestionsforDate(date));
        for (QuestionDto question : weeklyQuestions
        ) {
            System.out.println("Id je: " + question.getId());

        }


    }


    @MessageMapping("/hello")
    public void receiveData(AnswerDto answerDto, Principal principal) throws InterruptedException, IOException {
        Date date = Time.getTime();
        long diff=date.getTime()-dates.get(i-1).getTime();
        if (principal == null) {
            throw new ForbiddenException("Access denied");
        }
        System.out.println("Korisnicko je: " + principal.getName());


        boolean saved = answerService.saveAnswer(answerDto, principal.getName(),diff);
        if (saved == false) {
            throw new BadRequestException("Answer is not saved");
        }

    }


    @Scheduled(cron = SEND_QUESTION, zone = "Europe/Paris")
    public void sendData() throws IOException, InterruptedException {


        if (weeklyQuestions.size() < i + 1) {
            if(weeklyQuestions.size()==0){
                System.out.println("Questions not added yet");
            }
            else{
            System.out.println("List size");
            }
        }
        else{
        Date date = Time.getTime();
        Date quizDate=Time.getQuizDate(HOURS_LOGIN_END, MINUTES_LOGIN_END);
        if (date.after((quizDate)) || date.equals(quizDate)) {
            System.out.println("scheduled");
            this.template.convertAndSend("/topic/greetings", weeklyQuestions.get(i));
            dates.add(date);
            System.out.println("question num "+i);

            i++;
        }}

    }





}