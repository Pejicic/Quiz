package rs.ac.uns.quiz.model;


public final class Globals {


    public static final int HOURS_LOGIN_START=10;  //početak logovanja

    public static final int MINUTES_LOGIN_START=0;

    public static final int HOURS_LOGIN_END=10;  // kraj logovanja i početak kviza

    public static final int MINUTES_LOGIN_END=45;

    public static final int HOURS_QUIZ_END=10;  // kraj kviza

    public static final int MINUTES_QUIZ_END=7;

    //pocinje od nedelje i broja 1
    public static final int DAY=3;



    public static final String CRON_QUESTIONS = "45 "+MINUTES_LOGIN_START+" "+HOURS_LOGIN_START+" * * *";

    public static final String SEND_QUESTION = "0 "+MINUTES_LOGIN_END+" "+HOURS_LOGIN_END+" * * *";

    public static final String CRON_RESULT = "5 "+MINUTES_QUIZ_END+" "+HOURS_QUIZ_END+" * * *";
    ;




}