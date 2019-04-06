package de.oderkerk.tools.filetransfermanager.controller;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.oderkerk.tools.filetransfermanager.controller.upload.FileUploadResponse;

public class FileUploadResponseTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testToString() {
		FileUploadResponse resp = new FileUploadResponse();
		resp.setFileDownloadUri("Testuri");
		resp.setFileName("Testfile");
		resp.setFileType("txt");
		resp.setSize(66l);
		resp.setUploadSuccessfull(true);
		assertEquals(
				"FileUploadResponse [uploadSuccessfull=true, fileName=Testfile, fileDownloadUri=Testuri, fileType=txt, size=66]",
				resp.toString());
	}

}
