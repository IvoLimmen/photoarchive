package org.limmen.photoarchive;

import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {

		Context c = new Context();

		c.setSourcePath(new File("/home/ivo/Downloads/drive-download"));
		c.setTargetPath(new File("/home/ivo/Downloads/test"));
		
		c.addExtention("jpg");
		c.addExtention("jpeg");
		c.addExtention("png");
		c.addExtention("mp4");

		FileArchiver fa = new FileArchiver(c);
		fa.archive();
	}
}
