package com.stevenwan.svlas;

import cn.hutool.core.date.DateUtil;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-02 15:48
 */
public class ScheduleTest {
    public static void main(String[] args) {
        timerTest();
    }

    private static void timerTest() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis()+"我在 rate打印");
                int hour = DateUtil.hour(new Date(), true);
                if (hour == 17){
                    System.out.println(hour);
                    timer.cancel();
                }
            }
        }, DateUtil.parse("2021-02-02 16:38:32","yyyy-MM-dd HH:mm:ss"),1000);


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis()+"我在 one time 打印");
            }
        }, DateUtil.parse("2021-02-02 16:38:30","yyyy-MM-dd HH:mm:ss"));
    }
}
