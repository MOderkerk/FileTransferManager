/**
 * 
 */
package de.oderkerk.tools.filetransfermanager.controller.download;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import de.oderkerk.tools.filetransfermanager.service.FileStorageService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

/**
 * Restcontroller for filedownloads
 * 
 * @author Odin
 *
 */
@RestController
@ApiModel(description = "Controller for FileDownloadRestController")
public class FileDownloadRestController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4378636791362702063L;
	private static final Logger logger = LogManager.getLogger(FileDownloadRestController.class);
	@Autowired
	private FileStorageService fileStorageService;

	/**
	 * Download a file from te Downloadfolder
	 * 
	 * @param fileName to be downloaded
	 * @param request  used for
	 * @return Responseentity
	 */
	@GetMapping("/downloadFile/{fileName:.+}")
	@ApiOperation("downloadFile to download a file")
	@Authorization(value = "Bearer")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		if (logger.isDebugEnabled())
			logger.debug("Start download file ", fileName);

		Resource resource = fileStorageService.downloadFileAsResource(fileName);

		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			logger.error("Could not determine file type.");
		}
		if (logger.isDebugEnabled())
			logger.debug("contentType {}", contentType);
		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}
