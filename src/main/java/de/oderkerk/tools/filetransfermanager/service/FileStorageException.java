package de.oderkerk.tools.filetransfermanager.service;

import org.apache.logging.log4j.EventLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StructuredDataMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.oderkerk.tools.filetransfermanager.controller.CorrelationHeaderFilter;

/**
 * Exception used for file operations
 * 
 * @author Odin
 *
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class FileStorageException extends RuntimeException {

	private static final Logger logger = LogManager.getLogger();
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2049652131914961L;

	public FileStorageException(String message) {
		super(message);
		logEvent(message, null);
	}

	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
		logEvent(message, cause);
	}

	private void logEvent(String message, Throwable cause) {

		StructuredDataMessage dmsg = new StructuredDataMessage(
				CorrelationHeaderFilter.getTransactionid().get().substring(1, 32), "CustomFileNotFoundException",
				"Exception");
		dmsg.put("Error", message);
		EventLogger.logEvent(dmsg);
	}
}
