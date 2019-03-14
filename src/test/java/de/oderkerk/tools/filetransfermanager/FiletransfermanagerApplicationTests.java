package de.oderkerk.tools.filetransfermanager;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FiletransfermanagerApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void TestMain() {
		try {
			FiletransfermanagerApplication.main(new String[] { "" });
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

}
