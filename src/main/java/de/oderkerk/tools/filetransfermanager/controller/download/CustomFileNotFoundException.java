/**
 * 
 */
package de.oderkerk.tools.filetransfermanager.controller.download;

import org.apache.logging.log4j.EventLogger;
import org.apache.logging.log4j.message.StructuredDataMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.oderkerk.tools.filetransfermanager.controller.CorrelationHeaderFilter;

/**
 * Exception used for downloadoperations if the file which should be downlaoded
 * is not found
 * 
 * @author Odin
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomFileNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4924256825092944739L;

	public CustomFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
		logEvent(message, cause);
	}

	public CustomFileNotFoundException(String message) {
		super(message);
		logEvent(message, null);
	}

	private void logEvent(String message, Throwable cause) {

		StructuredDataMessage dmsg = new StructuredDataMessage(
				CorrelationHeaderFilter.getTransactionid().get().substring(1, 32), "CustomFileNotFoundException",
				"Exception");
		dmsg.put("Error", message);
		EventLogger.logEvent(dmsg);
	}

}
