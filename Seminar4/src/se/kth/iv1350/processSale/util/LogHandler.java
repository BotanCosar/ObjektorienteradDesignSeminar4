package se.kth.iv1350.processSale.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Singleton responsible for logging error messages. Logs the errors to the screen.
 */
public class LogHandler {
	
	private static final LogHandler LOG_HANDLER=new LogHandler();

	private LogHandler() {
	}
	
	/**
	 * Creates the only instance.
	 * 
	 * @return the only instance.
	 */
	public static LogHandler getLogHandler() {
		return LOG_HANDLER;
	}
	
	/**
	 * Logs the stack trace of the exception to the screen. 
	 * 
	 * @param e the exception being logged.
	 */
	public void logException(Exception e) {
		StringBuilder errorMsgBuilder = new StringBuilder();
        errorMsgBuilder.append("(---Log---)");
        errorMsgBuilder.append("\n");
        errorMsgBuilder.append(createTime());
        System.out.println(errorMsgBuilder);
        e.printStackTrace();
	}
	
	private String createTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        return now.format(formatter);
    }

}
