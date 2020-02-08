package rs.ac.uns.quiz.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.quiz.dto.QuestionDto;
import rs.ac.uns.quiz.dto.SettingsDto;
import rs.ac.uns.quiz.service.QuestionService;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(path = "/question")
public class QuestionController {

    private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Boolean> addQuestion(@RequestPart("dto")QuestionDto questionDto, @RequestPart("file") MultipartFile file){
        questionService.store(file);
        try {
            questionService.save(questionDto,file.getOriginalFilename());
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(false);
        }

return ResponseEntity.ok().body(true);
    }

    @PostMapping(value = "/saveQuestion")
    public ResponseEntity<Boolean> addQuestions(@RequestBody QuestionDto questionDto){
        try {
            questionService.save(questionDto,"");
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(false);
        }

        return ResponseEntity.ok().body(true);
    }



    @GetMapping(value="/getAll")
    public ResponseEntity<List<QuestionDto>> getUsers() {

        List<QuestionDto> questionDtos = questionService.getAllQuestions();

        return ResponseEntity.ok().body(questionDtos);
    }



    @GetMapping(value="/getSettings")
    public ResponseEntity<SettingsDto> getSettings() {

        System.out.println("Pogodjen settings");

        SettingsDto settingsDto=new SettingsDto();

        System.out.println("Pogodv "+settingsDto.getDayOfQuiz()+" "+settingsDto.getHoursBegin());

        return ResponseEntity.ok().body(settingsDto);
    }



    @DeleteMapping(value="/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) throws IOException {

       questionService.delete(id);
        return ResponseEntity.ok().body(true);
    }




}
