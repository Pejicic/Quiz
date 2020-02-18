package rs.ac.uns.quiz.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.quiz.dto.QuestionDto;
import rs.ac.uns.quiz.exception.NotFoundException;
import rs.ac.uns.quiz.model.Question;
import rs.ac.uns.quiz.repository.QuestionRepository;
import rs.ac.uns.quiz.service.QuestionService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final Path rootLocation = Paths.get("quizFront/src/assets");

    SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");

    private QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public void store(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("File not saved");

        }
    }


    @Override
    public void save(QuestionDto questionDto, String path) throws ParseException {
        Question question = new Question();

        question.setPath(path);
        question = questionDtoToQuestion(question, questionDto);
        questionRepository.save(question);


    }

    @Override
    public List<QuestionDto> getAllQuestions() {
        return questionRepository.findAll().stream().map(question -> questionToQuestionDto(question, "")).collect(Collectors.toList());
    }

    @Override
    public boolean delete(Long id) throws IOException {
        Question question = questionRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Question with id %s not found.", id)));
        Files.delete(rootLocation.resolve(question.getPath()));
        questionRepository.delete(question);
        return true;

    }

    @Override
    public List<QuestionDto> collectAllQuestionsforDate(Date d) {
        return questionRepository.findAllByDate(d).stream().map(question -> questionToQuestionDto(question, "s")).collect(Collectors.toList());

    }

    private QuestionDto questionToQuestionDto(Question question, String s) {

        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(question.getId());
        questionDto.setCategory(question.getCategory().name());
        questionDto.setDate(formater.format(question.getDate()));
        questionDto.setType(question.getQuestionType().name());
        questionDto.setText(question.getText());
        questionDto.setPath(question.getPath());
        if (!s.equals("s")) {
            questionDto.setAnswer(question.getAnswer());
            questionDto.setPoints(question.getPoints());
        }
        return questionDto;


    }

    private Question questionDtoToQuestion(Question question, QuestionDto questionDto) throws ParseException {

        question.setAnswer(questionDto.getAnswer());
        question.setText(questionDto.getText());
        question.setDate(formater.parse(questionDto.getDate()));
        System.out.println(question.getDate());
        question.setNumOfAnswers(questionDto.getNumOfAnswers());
        question.setPoints(questionDto.getPoints());
        question.setCategory(questionDto.getCategory());
        question.setQuestionType(questionDto.getType());
        return question;


    }

}
