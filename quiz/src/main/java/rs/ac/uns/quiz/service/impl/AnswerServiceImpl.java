package rs.ac.uns.quiz.service.impl;

import org.mariuszgromada.math.mxparser.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.quiz.dto.AnswerDto;
import rs.ac.uns.quiz.exception.NotFoundException;
import rs.ac.uns.quiz.model.*;
import rs.ac.uns.quiz.repository.AnswerRepository;
import rs.ac.uns.quiz.repository.PersonRepository;
import rs.ac.uns.quiz.repository.QuestionRepository;
import rs.ac.uns.quiz.service.AnswerService;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Override
    public boolean saveAnswer(AnswerDto answerDto, String username, long diff) {

        Person person = personRepository.findPersonByUsername(username).orElseThrow(
                () -> new NotFoundException(String.format("User with username %s not found.", username)));
        Question question = questionRepository.findById(answerDto.getId()).orElseThrow(
                () -> new NotFoundException(String.format("Question with id %s not found.", answerDto.getId())));
        Answer answer = new Answer();
        answer.setTimeBackend(diff);
        answer.setPerson(person);
        answer.setQuestion(question);
        answer.setAnswer(answerDto.getAnswer());
        answer.setTime(answerDto.getTime());
        answer.setIsAnswerCorrect(CorrectAnswer.UNCHECKED);
        Answer a=analyzeAnswer(answer);
        Answer save = answerRepository.save(a);
        if (save == null) {
            return false;
        }

        return true;
    }

    private Answer analyzeAnswer(Answer a) {

        if (a.getAnswer().equalsIgnoreCase("")) {
            a.setIsAnswerCorrect(CorrectAnswer.INNCORECT);
            a.setPoints(0);
            return a;
        }

        if (a.getQuestion().getQuestionType() == QuestionType.SIMPLE
                || a.getQuestion().getQuestionType() == QuestionType.PICTURE
                || a.getQuestion().getQuestionType() == QuestionType.VIDEO
                || a.getQuestion().getQuestionType() == QuestionType.MUSIC
                || a.getQuestion().getQuestionType() == QuestionType.ASSOCIATION) {

            String[] listCorrect = a.getQuestion().getAnswer().split(",");
            String[] listAnswers = a.getAnswer().split(",");
            double pointsCorrect = a.getQuestion().getPoints();
            double points = 0;
            if (a.getQuestion().getQuestionType() == QuestionType.ASSOCIATION) {
                points = calculatePoints(a.getQuestion().getNumOfAnswers(), pointsCorrect, listCorrect, listAnswers, "A");

            } else {
                System.out.println("Ima vise");

                points = calculatePoints(a.getQuestion().getNumOfAnswers(), pointsCorrect, listCorrect, listAnswers, "");
            }


            if (pointsCorrect == points) {
                a.setIsAnswerCorrect(CorrectAnswer.CORRECT);
            }
            else if(points==0){
                a.setIsAnswerCorrect(CorrectAnswer.INNCORECT);
            }else {
                a.setIsAnswerCorrect(CorrectAnswer.PARTIALY_CORRECT);
            }
            a.setPoints(points);
            return a;

        } else if (a.getQuestion().getQuestionType() == QuestionType.MATH) {
            String[] expression = a.getQuestion().getAnswer().split("=");
            String[] numbersOfCorrect = expression[0].split(",");

            int result = Integer.parseInt(expression[1]);

            String s = a.getAnswer().replaceAll("[()]", "");


            String[] numbersOfAnswer = s.split("[" + Pattern.quote("+-*/") + "]");

            List<String> list = Arrays.asList(numbersOfCorrect);


            for (String num : numbersOfAnswer
            ) {
                if (!list.contains(num)) {
                    a.setIsAnswerCorrect(CorrectAnswer.INNCORECT);
                    a.setPoints(0);
                    return a;
                }

            }

            Expression e = new Expression(a.getAnswer());
            double v = e.calculate();


            if (v == result) {
                a.setPoints(2);
                a.setIsAnswerCorrect(CorrectAnswer.CORRECT);
            } else {
                a.setPoints(0);
                a.setIsAnswerCorrect(CorrectAnswer.INNCORECT);
            }

            return a;

        }

        return a;
    }

    private double calculatePoints(int numOfAns, double correctPoints, String[] correct, String[] answers, String a) {

        double points = 0;
        double pointPerAnswer = (Double) correctPoints / numOfAns;

        System.out.println("po odgovoru je poena "+pointPerAnswer);

        for (int i = 0; i <= numOfAns - 1; i++) {

            if (a.equals("A")) {
                if (i == numOfAns - 1) {
                    if (answers[i].trim().equalsIgnoreCase(correct[i].trim())) {
                        points = points + 1;
                    }

                } else {
                    if (answers[i].trim().equalsIgnoreCase(correct[i].trim())) {
                        points = points + 0.25;
                    }
                }


            } else {
                System.out.println("NIje asocijacija");

                if (answers[i].trim().equalsIgnoreCase(correct[i].trim())) {
                    System.out.println("Tacan");

                    points = points + pointPerAnswer;
                }
            }
        }

        return points;


    }


}
