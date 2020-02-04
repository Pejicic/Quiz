package rs.ac.uns.quiz.service;

import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.quiz.dto.QuestionDto;
import rs.ac.uns.quiz.model.Question;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface QuestionService {

    void store(MultipartFile file);

    Question questionById(Long id);

    void save(QuestionDto questionDto, String path) throws ParseException;

    List<QuestionDto> getAllQuestions();

    boolean delete(Long id) throws IOException;

    List<QuestionDto> collectAllQuestionsforDate(Date d);


}
