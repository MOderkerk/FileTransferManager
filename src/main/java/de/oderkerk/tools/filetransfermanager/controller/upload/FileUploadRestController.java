/**
 * 
 */
package de.oderkerk.tools.filetransfermanager.controller.upload;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.oderkerk.tools.filetransfermanager.service.FileStorageService;

/**
 * 
 * @author Odin
 *
 */
@RestController
@RefreshScope
public class FileUploadRestController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5642279038215401877L;
	private static final Logger logger = LogManager.getLogger(FileUploadRestController.class);
	@Autowired
	private FileStorageService fileStorageService;

	@PostMapping("/uploadFile")
	public FileUploadResponse uploadSingleFile(@RequestParam("file") MultipartFile file) {
		if (logger.isDebugEnabled())
			logger.debug("Start receiving file ", file.getName());
		String fileName = fileStorageService.storeFile(file);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName).toUriString();

		return new FileUploadResponse(true, fileName, fileDownloadUri, file.getContentType(), file.getSize());
	}
}
