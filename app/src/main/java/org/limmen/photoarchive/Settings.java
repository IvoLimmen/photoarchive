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
      context.setSourcePath(getPath(sourcePath, "Downloads"));
      String targetPath = Preferences.userRoot().get(TARGETPATH, null);
      context.setTargetPath(getPath(targetPath, "Pictures"));
      context.setOverwrite(Preferences.userRoot().getBoolean(OVERWRITE, false));
      context.setPreferExif(Preferences.userRoot().getBoolean(PREFEREXIF, true));

      String[] extentions = Preferences.userRoot().get(EXTENTIONS, "jpg,jpeg,mp4").split(",");
      context.getExtentions().addAll(Arrays.asList(extentions));

      context.setTargetPattern(Preferences.userRoot().get(TARGETPATTERN, "yyyy\\mm\\dd"));

      return context;
   }

   private static File getPath(String path, String defaultPath) {
      File file = null;

      if (path != null && path.length() > 0) {
         file = new File(path);
      }

      if (file == null || !file.exists()) {
         file = new File(System.getProperty("user.home"), defaultPath);

         if (!file.exists()) {
            file = new File(System.getProperty("user.home"));
         }
      }

      return file;
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
