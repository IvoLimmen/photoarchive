package org.limmen.photoarchive.pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.limmen.photoarchive.FileMetadata;

public class PatternApplierTest {

   private PatternApplier fixture;

	@Test
	public void testOneDirectorySimplePattern() {
		fixture = new PatternApplier("yymmdd");
		
		FileMetadata fileMetadata = new FileMetadata();
		fileMetadata.setLocalDateTime(LocalDateTime.of(2017, 3, 19, 12, 5, 24));
		String[] directories = fixture.apply(fileMetadata);
		
		assertNotNull(directories);
		assertEquals(1, directories.length);
		assertEquals("170319", directories[0]);		
	}

	@Test
	public void testOneDirectoryBiggerPattern() {
		fixture = new PatternApplier("yyyymmdd");
		
		FileMetadata fileMetadata = new FileMetadata();
		fileMetadata.setLocalDateTime(LocalDateTime.of(2017, 3, 19, 12, 5, 24));
		String[] directories = fixture.apply(fileMetadata);
		
		assertNotNull(directories);
		assertEquals(1, directories.length);
		assertEquals("20170319", directories[0]);		
	}

	@Test
	public void testThreeDirectoryDefaultPattern() {
		fixture = new PatternApplier("yyyy\\mm\\dd");
		
		FileMetadata fileMetadata = new FileMetadata();
		fileMetadata.setLocalDateTime(LocalDateTime.of(2017, 3, 19, 12, 5, 24));
		String[] directories = fixture.apply(fileMetadata);
		
		assertNotNull(directories);
		assertEquals(3, directories.length);
		assertEquals("2017", directories[0]);		
		assertEquals("03", directories[1]);		
		assertEquals("19", directories[2]);		
	}

	@Test
	public void testTwoDirectoryYearWithMonthDayPattern() {
		fixture = new PatternApplier("yyyy\\mm-dd");
		
		FileMetadata fileMetadata = new FileMetadata();
		fileMetadata.setLocalDateTime(LocalDateTime.of(2017, 3, 19, 12, 5, 24));
		String[] directories = fixture.apply(fileMetadata);
		
		assertNotNull(directories);
		assertEquals(2, directories.length);
		assertEquals("2017", directories[0]);		
		assertEquals("03-19", directories[1]);		
	}

	@Test
	public void testFullMonthPattern() {
		fixture = new PatternApplier("yyyy\\mmm-dd");
		
		FileMetadata fileMetadata = new FileMetadata();
		fileMetadata.setLocalDateTime(LocalDateTime.of(2017, 3, 19, 12, 5, 24));
		String[] directories = fixture.apply(fileMetadata);
		
		assertNotNull(directories);
		assertEquals(2, directories.length);
		assertEquals("2017", directories[0]);		
		assertEquals("March-19", directories[1]);		
	}	

	@Test
	public void testFullMonthPattern2() {
		fixture = new PatternApplier("yyyy\\dd-mmm");
		
		FileMetadata fileMetadata = new FileMetadata();
		fileMetadata.setLocalDateTime(LocalDateTime.of(2017, 3, 19, 12, 5, 24));
		String[] directories = fixture.apply(fileMetadata);
		
		assertNotNull(directories);
		assertEquals(2, directories.length);
		assertEquals("2017", directories[0]);		
		assertEquals("19-March", directories[1]);		
	}	

	@Test
	public void testYearDayPatterm() {
		fixture = new PatternApplier("yyyy\\ddd");
		
		FileMetadata fileMetadata = new FileMetadata();
		fileMetadata.setLocalDateTime(LocalDateTime.of(2017, 3, 19, 12, 5, 24));
		String[] directories = fixture.apply(fileMetadata);
		
		assertNotNull(directories);
		assertEquals(2, directories.length);
		assertEquals("2017", directories[0]);		
		assertEquals("78", directories[1]);		
	}	
}
