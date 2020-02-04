package rs.ac.uns.quiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.script.ScriptException;
import java.util.regex.Pattern;

@SpringBootApplication
@EnableScheduling
public class QuizApplication {


	public static void main(String[] args) throws ScriptException {
		String s="(2+8)*9";
		s=s.replaceAll("[()]","");

		String [] result=s.split("["+ Pattern.quote("+-*/")+"]");
		for (String a:result
			 ) {

			System.out.println(a);

		}

		SpringApplication.run(QuizApplication.class, args);
	}

}
