package rs.ac.uns.quiz.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "score")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, columnDefinition = "DATE")
    Date date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    Person person;


    @Column(nullable = false)
    double finalScore;

    @Column(nullable = false)
    double scorePlusBonus;

    int bonus;


    public Score() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Person getUser() {
        return person;
    }

    public void setUser(Person user) {
        this.person = user;
    }

    public double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
    }

    public double getScorePlusBonus() {
        return scorePlusBonus;
    }

    public void setScorePlusBonus(double scorePlusBonus) {
        this.scorePlusBonus = scorePlusBonus;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
}