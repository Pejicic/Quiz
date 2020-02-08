package rs.ac.uns.quiz.model;


public final class Globals {


    public static final int HOURS_LOGIN_START=15;

    public static final int MINUTES_LOGIN_START=25;

    public static final int HOURS_LOGIN_END=15;

    public static final int MINUTES_LOGIN_END=30;

    public static final int HOURS_QUIZ_END=15;

    public static final int MINUTES_QUIZ_END=36;

    //pocinje od nedelje i broja 1
    public static final int DAY=7;



    public static final String CRON_QUESTIONS = "13 "+MINUTES_LOGIN_START+" "+HOURS_LOGIN_START+" * * *";

    public static final String SEND_QUESTION = " */60 * * * * *";


    public static final String CRON_RESULT = "5 "+MINUTES_QUIZ_END+" "+HOURS_QUIZ_END+" * * *";
    ;



}