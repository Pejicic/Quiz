package rs.ac.uns.quiz.dto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static rs.ac.uns.quiz.model.Globals.*;

public class SettingsDto {

    private int hoursBegin;    //pocetak prijave za kviz
    private int hoursEnd;        //kraj prijave za kviz i pocetak real time kviza
    private int minutesBegin;   //pocetak prijave za kviz
    private int minutesEnd;      //kraj prijave za kviz i pocetak real time kviza
    private int quizFinishedHours;   //kraj kviza
    private int quizFinishedMinutes;  //kraj kviza
    private int dayOfQuiz;// pocinje od nedjelje sa 0

    public SettingsDto() {
        this.hoursBegin=HOURS_LOGIN_START-1;  //jer se na frontu gleda utc vrijeme
        this.minutesBegin=MINUTES_LOGIN_START;
        this.hoursEnd=HOURS_LOGIN_END-1;
        this.minutesEnd=MINUTES_LOGIN_END;
        this.dayOfQuiz=DAY-1;
        this.quizFinishedHours=HOURS_QUIZ_END-1;
        this.quizFinishedMinutes=MINUTES_QUIZ_END;

    }

    public int getHoursBegin() {
        return hoursBegin;
    }

    public void setHoursBegin(int hoursBegin) {
        this.hoursBegin = hoursBegin;
    }

    public int getHoursEnd() {
        return hoursEnd;
    }

    public void setHoursEnd(int hoursEnd) {
        this.hoursEnd = hoursEnd;
    }

    public int getMinutesBegin() {
        return minutesBegin;
    }

    public void setMinutesBegin(int minutesBegin) {
        this.minutesBegin = minutesBegin;
    }

    public int getMinutesEnd() {
        return minutesEnd;
    }

    public void setMinutesEnd(int minutesEnd) {
        this.minutesEnd = minutesEnd;
    }

    public int getQuizFinishedHours() {
        return quizFinishedHours;
    }

    public void setQuizFinishedHours(int quizFinishedHours) {
        this.quizFinishedHours = quizFinishedHours;
    }

    public int getQuizFinishedMinutes() {
        return quizFinishedMinutes;
    }

    public void setQuizFinishedMinutes(int quizFinishedMinutes) {
        this.quizFinishedMinutes = quizFinishedMinutes;
    }

    public int getDayOfQuiz() {
        return dayOfQuiz;
    }

    public void setDayOfQuiz(int dayOfQuiz) {
        this.dayOfQuiz = dayOfQuiz;
    }


}
