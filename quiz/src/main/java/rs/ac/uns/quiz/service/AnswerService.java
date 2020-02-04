package rs.ac.uns.quiz.service;

import rs.ac.uns.quiz.dto.AnswerDto;

public interface AnswerService {

    boolean saveAnswer(AnswerDto answerDto,String username);
}
