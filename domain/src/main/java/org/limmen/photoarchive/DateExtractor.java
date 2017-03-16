package org.limmen.photoarchive;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class DateExtractor {

	private final boolean preferExif;

	public DateExtractor(boolean preferExif) {
		this.preferExif = preferExif;
	}

	public LocalDateTime getCreationDate(Path file) throws IOException {

		LocalDateTime localDateTime = null;
		if (preferExif) {
			try {
				localDateTime = fromExif(file.toFile());
			}
			catch (ImageProcessingException ex) {
				// no exif info...
			}
		}

		if (localDateTime == null) {
			localDateTime = fromFile(file);
		}

		return localDateTime;
	}

	private LocalDateTime fromExif(File file) throws ImageProcessingException, IOException {
		LocalDateTime localDateTime = null;
		Metadata metadata = ImageMetadataReader.readMetadata(file);
		if (metadata.containsDirectoryOfType(ExifSubIFDDirectory.class)) {
			ExifSubIFDDirectory exif = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

			if (exif != null && exif.getDateOriginal() != null) {
				localDateTime = LocalDateTime.ofInstant(exif.getDateOriginal().toInstant(), ZoneId.systemDefault());
			}
		}

		return localDateTime;
	}

	private LocalDateTime fromFile(Path file) throws IOException {
		BasicFileAttributes bfa = Files.readAttributes(file, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
		return LocalDateTime.ofInstant(bfa.creationTime().toInstant(), ZoneOffset.UTC);
	}
}
