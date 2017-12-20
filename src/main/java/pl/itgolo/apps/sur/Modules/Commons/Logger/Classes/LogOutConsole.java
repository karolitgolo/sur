package pl.itgolo.apps.sur.Modules.Commons.Logger.Classes;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import pl.itgolo.apps.sur.Modules.Commons.Utils.ConsoleUtils;
import pl.itgolo.apps.sur.Modules.Commons.Logger.Interfaces.ILogOut;

/**
 * The type Log out console.
 */
public class LogOutConsole implements ILogOut {

    private Log log;

    @Override
    public void setLog(Log log) {
        this.log = log;
    }

    @Override
    public void printOut() {
        if (log == null) {
            return;
        }
        switch (log.getLevel()) {
            case INFO:
                logOutInfo();
                break;
            case DEBUG:
                logOutDebug();
                break;
            case WARNING:
                logOutWarn();
                break;
            case ERROR:
                logOutError();
                break;
            case TEST:
                logOutTest();
                break;
        }
    }

    private void logOutError() {
        String message = String.format("ERROR: [%1$s] (%2$s.java:%4$s %3$s)   %5$s",
                DateFormatUtils.format(log.getDate(), "yyyy-MM-dd HH:mm:ss"),
                log.getCallerClass(),
                log.getCallerMethod(),
                log.getCallerLine(),
                (log.getMessage() == null) ? "" : log.getMessage()
        );
        if (log.getException() != null){
            String stackTrace = ExceptionUtils.getStackTrace(log.getException());
            message += "\n" + stackTrace.trim();
        }
        System.out.println(ConsoleUtils.setColorRed(message));
    }

    private void logOutWarn() {
        String message = String.format("WARN:  [%1$s] (%2$s.java:%4$s %3$s)   %5$s",
                DateFormatUtils.format(log.getDate(), "yyyy-MM-dd HH:mm:ss"),
                log.getCallerClass(),
                log.getCallerMethod(),
                log.getCallerLine(),
                log.getMessage()
        );
        System.out.println(ConsoleUtils.setColorYellow(message));
    }

    private void logOutTest() {
        String message = String.format("TEST:  [%1$s] (%2$s.java:%4$s %3$s)   %5$s",
                DateFormatUtils.format(log.getDate(), "yyyy-MM-dd HH:mm:ss"),
                log.getCallerClass(),
                log.getCallerMethod(),
                log.getCallerLine(),
                log.getMessage()
        );
        System.out.println(ConsoleUtils.setColorGreen(message));
    }

    private void logOutDebug() {
        String message = String.format("DEBUG: [%1$s] (%2$s.java:%4$s %3$s)   %5$s",
                DateFormatUtils.format(log.getDate(), "yyyy-MM-dd HH:mm:ss"),
                log.getCallerClass(),
                log.getCallerMethod(),
                log.getCallerLine(),
                log.getMessage()
        );
        System.out.println(ConsoleUtils.setColorCyan(message));
    }

    private void logOutInfo() {
        String message = String.format("INFO:  [%1$s] (%2$s.java:%4$s %3$s)   %5$s",
                DateFormatUtils.format(log.getDate(), "yyyy-MM-dd HH:mm:ss"),
                log.getCallerClass(),
                log.getCallerMethod(),
                log.getCallerLine(),
                log.getMessage()
        );
        System.out.println(ConsoleUtils.setColorWhite(message));
    }
}
