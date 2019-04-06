/**
 * 
 */
package de.oderkerk.tools.filetransfermanager.ITTest;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import de.oderkerk.tools.filetransfermanager.controller.upload.FileUploadResponse;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

/**
 * @author Odin
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class ITFileUploadRestControllerTest {

	@LocalServerPort
	int randomServerPort;

	@LocalManagementPort
	int randomManagementPort;

	MockMultipartFile file;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = randomServerPort;
		RestAssured.defaultParser = Parser.JSON;
		file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUploadOk() throws Exception {
		File f = new File("target/download/downloadtest.txt");
		if (!f.exists())
			f.createNewFile();
		BufferedWriter buf = new BufferedWriter(new FileWriter(f));
		buf.write("Test");
		buf.flush();
		buf.close();

		Response resultResponse = given().header("Authorization",
				"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE1NTQ1MzAwNjcsImV4cCI6NDA3OTA1MTY2NywiYXVkIjoid3d3Lm9kZXJrZXJrLmRlIiwic3ViIjoidGVzdHVzZXIiLCJSb2xlIjoiTWFuYWdlciIsImF1dGhvcml0aWVzIjoiYWRtaW4sZmlsZXVwbG9hZCxmaWxlZG93bmxvYWQifQ.DYeTuUephWJ2CjWdhMPzek5Vn2bqoQGaks_u8J6qXWQ")
				.multiPart(f).when().log().all().post("/uploadFile");
		assertEquals(200, resultResponse.getStatusCode());
		try {
			FileUploadResponse result = resultResponse.as(FileUploadResponse.class);
			assertTrue(result.isUploadSuccessfull());
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

	}

	@Test
	public void testUploadExpiredTokenError() throws Exception {
		File f = new File("target/download/downloadtest.txt");
		if (!f.exists())
			f.createNewFile();
		BufferedWriter buf = new BufferedWriter(new FileWriter(f));
		buf.write("Test");
		buf.flush();
		buf.close();

		Response resultResponse = given().header("Authorization",
				"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE1NTQ1MzAwNjcsImV4cCI6MTUyMjkwNzY2NywiYXVkIjoid3d3Lm9kZXJrZXJrLmRlIiwic3ViIjoidGVzdHVzZXIiLCJSb2xlIjoiTWFuYWdlciIsImF1dGhvcml0aWVzIjoiYWRtaW4sZmlsZXVwbG9hZCxmaWxlZG93bmxvYWQifQ.BGlkt55UYRA5rbDBjq6kLXtSzz3ptnhObWMirSY5pxM")
				.multiPart(f).when().log().all().post("/uploadFile");
		assertEquals(401, resultResponse.getStatusCode());

	}

	@Test
	public void testUploadNotAuthorized() throws Exception {
		File f = new File("target/download/downloadtest.txt");
		if (!f.exists())
			f.createNewFile();
		BufferedWriter buf = new BufferedWriter(new FileWriter(f));
		buf.write("Test");
		buf.flush();
		buf.close();

		Response resultResponse = given().header("Authorization",
				"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE1NTQ1MzAwNjcsImV4cCI6NDA3OTA1MTY2NywiYXVkIjoid3d3Lm9kZXJrZXJrLmRlIiwic3ViIjoidGVzdHVzZXIiLCJSb2xlIjoiTWFuYWdlciIsImF1dGhvcml0aWVzIjoidXNlciJ9.GB6wZ67gnTEE9_1Re3LQaKrK-zbqycro0ufXWQEHtgI")
				.multiPart(f).when().log().all().post("/uploadFile");
		assertEquals(403, resultResponse.getStatusCode());

	}

}
