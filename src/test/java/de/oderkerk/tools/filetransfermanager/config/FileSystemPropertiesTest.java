package de.oderkerk.tools.filetransfermanager.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FileSystemPropertiesTest {

	@Autowired
	FileSystemProperties filesystemproperties;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		assertEquals("target/upload", filesystemproperties.getUploadfolder());
		assertEquals("target/download", filesystemproperties.getDownloadfolder());
		assertTrue(filesystemproperties.isReplaceExistingFile());
	}

}
