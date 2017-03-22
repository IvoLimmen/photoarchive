package org.limmen.photoarchive;

import java.time.LocalDateTime;

public class FileMetadata {

	private LocalDateTime localDateTime;

	private Location location;

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
