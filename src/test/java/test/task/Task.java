package test.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @program: hl_ssm_rc
 * @description: ${description}
 * @author: renchao
 * @create: 2018-09-25 14:58
 **/
public class Task extends TimerTask {
    @Override
    public void run() {
        System.out.println(new Date().getTime());
    }


    public static void main(String[] args) throws ParseException {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Timer t = new Timer();
        System.out.println(new Date().getTime());
        t.schedule(new Task(), s.parse("2018-9-25 15:05:00"));
    }
}
