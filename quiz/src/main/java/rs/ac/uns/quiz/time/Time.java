package rs.ac.uns.quiz.time;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Date;
import static rs.ac.uns.quiz.model.Globals.*;


public class Time {
    public static final String TIME_SERVER = "time-a.nist.gov";



    public static Date getTime() throws IOException {
        NTPUDPClient timeClient = new NTPUDPClient();
        InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
        TimeInfo timeInfo = timeClient.getTime(inetAddress);
        long returnTime = timeInfo.getReturnTime();
        Date time = new Date(returnTime);
        long systemtime = System.currentTimeMillis();
        timeInfo.computeDetails();
        Date realdate = new Date(systemtime + timeInfo.getOffset());
       return realdate;
    }

    public static Date getQuizDate(int hours, int minutes){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,hours);
        cal.set(Calendar.DAY_OF_WEEK, DAY);
        cal.set(Calendar.MINUTE,minutes);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);

        Date d = cal.getTime();
        System.out.println(d);
        return d;

    }




}
