package rs.ac.uns.quiz.model;

import rs.ac.uns.quiz.exception.NotFoundException;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "question")
public class Question  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Categories category;

    @Column(nullable = false,columnDefinition = "DATE")
    Date date;

    @Column(nullable = false)
    String text;

    @Column(nullable = false)
    QuestionType questionType;

    @Column(nullable = false)
    String answer;

    @Column(nullable = false)
    int NumOfAnswers;

    String path;

    @Column(nullable = false)
    double points;

    public Question(){}

    public int getNumOfAnswers() {
        return NumOfAnswers;
    }

    public void setNumOfAnswers(int numOfAnswers) {
        NumOfAnswers = numOfAnswers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }
    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

   public void setCategory(String category){

       if(category.equalsIgnoreCase("Opšte znanje")){
           this.category=Categories.GENERAL_KNOWLEDGE;
       }
       else if(category.equalsIgnoreCase("Geografija")){
           this.category=Categories.GEOGRAPHY;
       }
       else if(category.equalsIgnoreCase("Književnost")){
           this.category=Categories.LITERATURE;
       }
       else if(category.equalsIgnoreCase("Mozgalice")){
           this.category=Categories.BRAIN_TEASERS;
       }
       else if(category.equalsIgnoreCase("Istorija")){
           this.category=Categories.HISTORY;
       }
       else if(category.equalsIgnoreCase("Oblast večeri")){
           this.category=Categories.TOPIC_OF_THE_NIGHT;
       }
       else if(category.equalsIgnoreCase("Sport")){
           this.category=Categories.SPORT;
       }
       else if(category.equalsIgnoreCase("Muzika")){
           this.category=Categories.MUSIC;
       }
       else if(category.equalsIgnoreCase("Film")){
           this.category=Categories.MOVIES;
       }
       else{
           throw new NotFoundException("Category with name "+category+" not found.");
       }

   }

   public void setQuestionType(String type){

       if (type.equalsIgnoreCase("Obično")) {

           this.questionType=QuestionType.SIMPLE;

       }
       else if (type.equalsIgnoreCase("Asocijacija")) {

           this.questionType=QuestionType.ASSOCIATION;

       }
       else if (type.equalsIgnoreCase("Moj broj")) {

           this.questionType=QuestionType.MATH;

       }
       else if (type.equalsIgnoreCase("Slika")) {

           this.questionType=QuestionType.PICTURE;

       }
       else if (type.equalsIgnoreCase("Muzika")) {

           this.questionType=QuestionType.MUSIC;

       }
       else  if (type.equalsIgnoreCase("Video")) {

           this.questionType=QuestionType.VIDEO;

       }

       else{

           throw  new NotFoundException("Question with type "+type+" is not found.");

       }



   }
}
