package org.limmen.photoarchive;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import org.json.JSONObject;

public class LocationExtractor {

	private static final String OPENSTREETMAP = "http://nominatim.openstreetmap.org/reverse?format=json&lat=|lat|&lon=|lng|&addressdetails=1";
	
	public LocationExtractor(Context context) {
	}

	public void extractLocation(Path file, FileMetadata fileMetadata) throws IOException {

		try {
			Metadata metadata = ImageMetadataReader.readMetadata(file.toFile());
			if (metadata.containsDirectoryOfType(GpsDirectory.class)) {
				GpsDirectory exif = metadata.getFirstDirectoryOfType(GpsDirectory.class);

				if (exif != null && exif.getGeoLocation() != null) {
					GeoLocation geoLocation = exif.getGeoLocation();

					String url = OPENSTREETMAP.replace("|lat|", Double.toString(geoLocation.getLatitude()));
					url = url.replace("|lng|", Double.toString(geoLocation.getLongitude()));
										
					parseResponse(fileMetadata, downloadJSON(url));
				}
			}
		}
		catch (ImageProcessingException ipe) {
			throw new IOException(ipe);
		}
	}

	private JSONObject downloadJSON(String url) throws IOException {
		try {
			URI uri = new URI(url);
			HttpURLConnection httpConn = (HttpURLConnection) uri.toURL().openConnection();
			int responseCode = httpConn.getResponseCode();

			// always check HTTP response code first
			if (responseCode == HttpURLConnection.HTTP_OK) {
				StringBuilder json = new StringBuilder(4096);

				// opens input stream from the HTTP connection
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()))) {
					String line;
					while ((line = reader.readLine()) != null) {
						json.append(line);
					}
					return new JSONObject(json.toString());
				}
			}
			httpConn.disconnect();
		}
		catch (URISyntaxException ex) {
			//
		}
		return null;
	}

	private void parseResponse(FileMetadata fileMetadata, JSONObject object) {
		if (object == null) {
			return;
		}
		
		if (object.has("address")) {
			JSONObject address = object.getJSONObject("address");
			Location location = new Location();
			
			if (address.has("house_number")) {
			   location.setHouseNumber(address.getString("house_number"));
			}
			if (address.has("road")) {
			   location.setRoad(address.getString("road"));
			}
			if (address.has("village")) {
			   location.setVillage(address.getString("village"));
			}
			if (address.has("town")) {
			   location.setTown(address.getString("town"));
			}
			if (address.has("city")) {
			   location.setCity(address.getString("city"));
			}
			if (address.has("county")) {
			   location.setCounty(address.getString("county"));
			}
			if (address.has("postcode")) {
			   location.setPostCode(address.getString("postcode"));
			}
			if (address.has("country")) {
			   location.setCountry(address.getString("country"));
			}
			if (address.has("country_code")) {
			   location.setCountryCode(address.getString("country_code"));
			}
			
			fileMetadata.setLocation(location);
		}
	}
}
