package de.oderkerk.tools.filetransfermanager.ITTest;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

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
import org.springframework.test.context.junit4.SpringRunner;

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
public class ITFileDownloadRestControllerTest {
	@LocalServerPort
	int randomServerPort;

	@LocalManagementPort
	int randomManagementPort;

	@Before
	public void setUp() throws Exception {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = randomServerPort;
		RestAssured.defaultParser = Parser.JSON;

		File f = new File("target/download/downloadtest.txt");
		if (!f.exists())
			f.createNewFile();
		BufferedWriter buf = new BufferedWriter(new FileWriter(f));
		buf.write("Test");
		buf.flush();
		buf.close();

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testTokenExpired() throws Exception {
		Response resultResponse = given().header("Authorization",
				"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE1NTQ1MzAwNjcsImV4cCI6MTUyMjkwNzY2NywiYXVkIjoid3d3Lm9kZXJrZXJrLmRlIiwic3ViIjoidGVzdHVzZXIiLCJSb2xlIjoiTWFuYWdlciIsImF1dGhvcml0aWVzIjoiYWRtaW4sZmlsZXVwbG9hZCxmaWxlZG93bmxvYWQifQ.BGlkt55UYRA5rbDBjq6kLXtSzz3ptnhObWMirSY5pxM")
				.when().log().all().get("/downloadFile/downloadtext.txt");
		assertEquals(401, resultResponse.getStatusCode());
	}

	@Test
	public final void testFileNotFoung() throws Exception {
		Response resultResponse = given().header("Authorization",
				"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE1NTQ1MzAwNjcsImV4cCI6NDA3OTA1MTY2NywiYXVkIjoid3d3Lm9kZXJrZXJrLmRlIiwic3ViIjoidGVzdHVzZXIiLCJSb2xlIjoiTWFuYWdlciIsImF1dGhvcml0aWVzIjoiYWRtaW4sZmlsZXVwbG9hZCxmaWxlZG93bmxvYWQifQ.DYeTuUephWJ2CjWdhMPzek5Vn2bqoQGaks_u8J6qXWQ")
				.when().log().all().get("/downloadFile/downloadtext.txt");
		assertEquals(404, resultResponse.getStatusCode());
	}

	@Test
	public final void testFileDownloadOk() throws Exception {
		Response resultResponse = given().header("Authorization",
				"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE1NTQ1MzAwNjcsImV4cCI6NDA3OTA1MTY2NywiYXVkIjoid3d3Lm9kZXJrZXJrLmRlIiwic3ViIjoidGVzdHVzZXIiLCJSb2xlIjoiTWFuYWdlciIsImF1dGhvcml0aWVzIjoiYWRtaW4sZmlsZXVwbG9hZCxmaWxlZG93bmxvYWQifQ.DYeTuUephWJ2CjWdhMPzek5Vn2bqoQGaks_u8J6qXWQ")
				.when().log().all().get("/downloadFile/downloadtest.txt");
		assertEquals(200, resultResponse.getStatusCode());
		byte[] fileContents = resultResponse.getBody().asByteArray();
		assertEquals(4, fileContents.length);

		assertEquals(new String(fileContents), "Test");
	}

}
