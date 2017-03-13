# photoarchive
Simple tool to archive files into directories structured as year/month/day

# Screenshots
![Main screen](/docs/MainScreen.png)
The application will transform a directory with a content like this:
![Source](/docs/Source.png)
To a structured directory like this:
![Target](/docs/Target.png)

# Building the application from source

## Prerequisites

  * Git
  * Java 8
  * Gradle

## Steps

	git clone git@github.com:IvoLimmen/photoarchive.git

	./gradlew fatJar

	java -jar app/build/libs/PhotoArchive-1.0-SNAPSHOT.jar

