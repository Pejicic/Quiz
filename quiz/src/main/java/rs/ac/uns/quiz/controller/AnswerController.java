package rs.ac.uns.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import rs.ac.uns.quiz.dto.AnswerDto;
import rs.ac.uns.quiz.dto.QuestionDto;
import rs.ac.uns.quiz.exception.BadRequestException;
import rs.ac.uns.quiz.exception.ForbiddenException;
import rs.ac.uns.quiz.service.AnswerService;
import rs.ac.uns.quiz.service.QuestionService;
import rs.ac.uns.quiz.time.Time;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static rs.ac.uns.quiz.model.Globals.SEND_QUESTION;

@Controller
public class AnswerController {

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    List<QuestionDto> weeklyQuestions = new ArrayList<QuestionDto>();

    List<Date> dates = new ArrayList<Date>();

    SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");

    private int i = 0;

    @Autowired
    private SimpMessagingTemplate template;

    ScheduledExecutorService scheduledPool;

    @MessageMapping("/hello")
    public void receiveData(AnswerDto answerDto, Principal principal) throws IOException {
        Date date = Time.getTime();
        long diff = date.getTime() - dates.get(i - 1).getTime();
        if (principal == null) {
            throw new ForbiddenException("Access denied");
        }


        boolean saved = answerService.saveAnswer(answerDto, principal.getName(), diff);
        if (saved == false) {
            throw new BadRequestException("Answer is not saved");
        }

    }


    @Scheduled(cron = SEND_QUESTION, zone = "Europe/Paris")
    public void sendData() throws IOException, InterruptedException,ParseException {
        weeklyQuestions.clear();
        dates.clear();
        i = 0;
        Date date = formater.parse(formater.format(Time.getTime()));
        System.out.println(date);

        weeklyQuestions.addAll(questionService.collectAllQuestionsforDate(date));

        Runnable runnabledelayedTask = new Runnable() {
            @Override
            public void run() {
                sendQuestions();
            }
        };
        scheduledPool = Executors.newScheduledThreadPool(weeklyQuestions.size());
        scheduledPool.scheduleWithFixedDelay(runnabledelayedTask, 0, 1, TimeUnit.MINUTES);


    }

    public void sendQuestions() {

        if (weeklyQuestions.size() < i + 1) {
            if (weeklyQuestions.size() == 0) {
                System.out.println("Questions not added yet");
            } else {
                System.out.println("List size");
                scheduledPool.shutdown();
            }
        } else {

            Date date=new Date();
            try {
                date = Time.getTime();
            }catch (IOException e){
              e.printStackTrace();

            }
            System.out.println("scheduled");
            this.template.convertAndSend("/topic/greetings", weeklyQuestions.get(i));
            dates.add(date);
            System.out.println("question num " + i);
            i++;


        }


    }

}