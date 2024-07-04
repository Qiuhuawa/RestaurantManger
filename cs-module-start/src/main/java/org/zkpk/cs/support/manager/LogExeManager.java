package org.zkpk.cs.support.manager;

import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @Description: 日志操作任务运行管理器
 * @author HUCHAO
 * @date 2018-10-19 11:10:07
 */
public class LogExeManager {

    private static final int LOG_DELAY_TIME = 10;

    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(20);

    private static LogExeManager logExeManager = new LogExeManager();
    
    private LogExeManager() {

    }

    public static LogExeManager getInstance() {
        return logExeManager;
    }

    public void executeLogTask(TimerTask timerTask) {
        executor.schedule(timerTask, LOG_DELAY_TIME, TimeUnit.MICROSECONDS);
    }
}
