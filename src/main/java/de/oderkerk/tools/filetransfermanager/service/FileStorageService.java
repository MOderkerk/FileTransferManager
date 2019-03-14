/**
 * 
 */
package de.oderkerk.tools.filetransfermanager.service;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import de.oderkerk.tools.filetransfermanager.config.FileSystemProperties;
import de.oderkerk.tools.filetransfermanager.controller.download.CustomFileNotFoundException;

/**
 * Service class handling all file storage operations
 * 
 * @author Odin
 *
 */
@Service
public class FileStorageService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7502263391311498111L;
	private final Path centralFileUploadLocation;
	private final Path centralFileDownloadLocation;
	private final boolean replaceExistingFiles;

	/**
	 * Getting the main paths for the fileoperations and creating them if not
	 * existing. if the path exists no exception will be thrown.
	 * 
	 * @param filesystemProperties
	 */
	@Autowired(required = true)
	public FileStorageService(FileSystemProperties filesystemProperties) {

		this.centralFileUploadLocation = Paths.get(filesystemProperties.getUploadfolder()).toAbsolutePath().normalize();
		this.centralFileDownloadLocation = Paths.get(filesystemProperties.getDownloadfolder()).toAbsolutePath()
				.normalize();
		this.replaceExistingFiles = filesystemProperties.isReplaceExistingFile();

		try {
			Files.createDirectories(this.centralFileUploadLocation);
			Files.createDirectories(this.centralFileDownloadLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}

	/**
	 * Method to store a file on the server. if the filename already exists the file
	 * will be replaced
	 * 
	 * @param file
	 * @return new filename in the upload folder
	 */
	public String storeFile(MultipartFile file) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException(
						"Filename contains invalid path sequence .. Due to security reasons this is not allowed");
			}
			Path targetLocation = this.centralFileUploadLocation.resolve(fileName);
			if (replaceExistingFiles) {
				Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			} else {
				Files.copy(file.getInputStream(), targetLocation);
			}
			return fileName;
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file. " + fileName, ex);
		}
	}

	public Resource downloadFileAsResource(String fileName) {
		try {
			Path filePath = this.centralFileDownloadLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new CustomFileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new CustomFileNotFoundException("File not found " + fileName, ex);
		}
	}
}
