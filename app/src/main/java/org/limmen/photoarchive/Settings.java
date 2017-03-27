package org.limmen.photoarchive;

import java.io.File;
import java.util.Arrays;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

public class Settings {

	private final static String SOURCEPATH = "photoarchive.sourcepath";
	
	private final static String TARGETPATH = "photoarchive.targetpath";
	
	private final static String OVERWRITE = "photoarchive.overwrite";
	
	private final static String PREFEREXIF = "photoarchive.preferexif";
	
	private final static String EXTENTIONS = "photoarchive.extentions";
	
	private final static String TARGETPATTERN = "photoarchive.targetpattern";
	
	public Settings() {
	}

	public static Context getInitialContext() {
		Context context = new Context();
		String sourcePath = Preferences.userRoot().get(SOURCEPATH, null);
		if (sourcePath == null) {
			context.setSourcePath(new File(System.getProperty("user.home"), "Downloads"));
		} else {
			context.setSourcePath(new File(sourcePath));
		}
		String targetPath = Preferences.userRoot().get(TARGETPATH, null);
		if (targetPath == null) {
			context.setTargetPath(new File(System.getProperty("user.home"), "Pictures"));
		} else {
			context.setTargetPath(new File(targetPath));
		}
		context.setOverwrite(Preferences.userRoot().getBoolean(OVERWRITE, false));
		context.setPreferExif(Preferences.userRoot().getBoolean(PREFEREXIF, true));

		String[] extentions = Preferences.userRoot().get(EXTENTIONS, "jpg,jpeg,mp4").split(",");
		context.getExtentions().addAll(Arrays.asList(extentions));

		context.setTargetPattern(Preferences.userRoot().get(TARGETPATTERN, "yyyy\\mm\\dd"));

		return context;
	}
	
	public static void setInitialContext(Context context) {
		Preferences.userRoot().put(SOURCEPATH, context.getSourcePath().getAbsolutePath());		
		Preferences.userRoot().put(TARGETPATH, context.getTargetPath().getAbsolutePath());		
		Preferences.userRoot().put(TARGETPATTERN, context.getTargetPattern());		
		Preferences.userRoot().put(EXTENTIONS, context.getExtentions().stream().collect(Collectors.joining(",")));		
		Preferences.userRoot().putBoolean(OVERWRITE, context.isOverwrite());		
		Preferences.userRoot().putBoolean(PREFEREXIF, context.isPreferExif());		
	}
}
