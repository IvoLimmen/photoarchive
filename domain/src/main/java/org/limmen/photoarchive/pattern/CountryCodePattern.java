package org.limmen.photoarchive.pattern;

import org.limmen.photoarchive.FileMetadata;

public class CountryCodePattern extends AbstractPattern {

	@Override
	public boolean applicable(String directory) {
		return testExactPattern(directory, "c", 2);
	}

	@Override
	public String apply(String directory, FileMetadata metadata) {
		String cc = "";
		if (metadata.getLocation() != null && metadata.getLocation().getCountryCode() != null) {
			cc = metadata.getLocation().getCountryCode().toUpperCase();
			
		}

		return directory.replace("cc", cc);			
	}	
}
