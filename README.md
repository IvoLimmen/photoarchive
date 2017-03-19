# photoarchive
Simple tool to archive files into directories structured as year/month/day

# Screenshots

![Main screen](/docs/MainScreen.png)

The application will transform a directory with a content like this:

![Source](/docs/Source.png)

To a structured directory like this:

![Target](/docs/Target.png)

# Using the application

 1. Select a source directory to read all the media files from.
 1. Select a target directory to copy all media to.
 1. Modify (if needed) the extentions to wish to copy.
 1. Changed the pattern of the directory structure as you like it.

# Using a pattern

Using the pattern 'yyyy\mm\dd' will result in a directory structure 2017\03\19.
Using a pattern 'yy-ddd' will result in directories 17-78.
Look at the tooltip for more information.

# Building the application from source

## Prerequisites

  * Git
  * Java 8
  * Gradle

## Steps

	git clone git@github.com:IvoLimmen/photoarchive.git

	./gradlew fatJar

	java -jar app/build/libs/PhotoArchive-1.0-SNAPSHOT.jar

