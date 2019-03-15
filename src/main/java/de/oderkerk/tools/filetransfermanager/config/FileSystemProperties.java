/**
 * 
 */
package de.oderkerk.tools.filetransfermanager.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Getting the main settings for file operatins
 * 
 * @author Odin
 *
 */
@Component
@Configuration
@ConfigurationProperties(prefix = "de.oderkerk.tools")
@RefreshScope
public class FileSystemProperties {
	private String uploadfolder;
	private String downloadfolder;
	private boolean replaceExistingFile;

	public String getUploadfolder() {
		return uploadfolder;
	}

	public void setUploadfolder(String uploadfolder) {
		this.uploadfolder = uploadfolder;
	}

	public String getDownloadfolder() {
		return downloadfolder;
	}

	public void setDownloadfolder(String downloadfolder) {
		this.downloadfolder = downloadfolder;
	}

	/**
	 * @return the replaceExistingFile
	 */
	public boolean isReplaceExistingFile() {
		return replaceExistingFile;
	}

	/**
	 * @param replaceExistingFile the replaceExistingFile to set
	 */
	public void setReplaceExistingFile(boolean replaceExistingFile) {
		this.replaceExistingFile = replaceExistingFile;
	}

	public FileSystemProperties() {
		super();
	}

}
