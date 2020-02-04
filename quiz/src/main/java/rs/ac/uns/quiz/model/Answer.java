package rs.ac.uns.quiz.model;

import javax.persistence.*;

@Entity
@Table(name = "answer")
public class Answer  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    Person person;

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    Question question;

    @Column(nullable = false)
    String answer;

    @Column(nullable = false)
    Double time;

    @Column(nullable = false)
    CorrectAnswer isAnswerCorrect;

    double points;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public Answer(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public CorrectAnswer getIsAnswerCorrect() {
        return isAnswerCorrect;
    }

    public void setIsAnswerCorrect(CorrectAnswer isAnswerCorrect) {
        this.isAnswerCorrect = isAnswerCorrect;
    }
}
