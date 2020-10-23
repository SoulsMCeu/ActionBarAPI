# ActionBarAPI
ActionBarAPI Plugin for SoulsMC.de

How To (Server Admins)
------
ActionBarAPI is a jar file that you can download and run just like a normal jar file.

Download a copy of ActionBarAPI-1.0.1-SNAPSHOT.jar from [releases](https://github.com/SoulsMCde/ActionBarAPI/releases/download/v1.0.1/ActionBarAPI-1.0.1-SNAPSHOT.jar).

Paste the jar directly from your server. Just like old times

## How To (Plugin Developers)
------
 * Maven Repo:
```xml
<repository>
    <id>arrow-systems</id>
    <url>https://mvn.arrow-systems.de/</url>
</repository>
```
 * Artifact Information:
```xml
<dependency>
    <groupId>de.soulsmc</groupId>
    <artifactId>actionbarapi</artifactId>
    <version>1.0.1-SNAPSHOT</version>
    <type>jar</type>
    <scope>provided</scope>
</dependency>
 ```

How To (Compiling Jar From Source)
------
To compile ActionBarAPI, you need JDK 8, maven, and an internet connection.

Clone this repo, run `mvn clean install`
