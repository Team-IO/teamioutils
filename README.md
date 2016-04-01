# TeamIO Utils
This is a mod for the game [minecraft](https://mojang.com).  
Mostly for fun, but feel free to use it.

## Content
* Hammer
  * The Hammer is a 3*3 mining tool, which digs everthing instant.

## Contributing
If you want to contribute, you can do so [by reporting bugs](https://github.com/Team-IO/teamioutils/issues), [by helping fix the bugs](https://github.com/Team-IO/teamioutils/pulls) or by spreading the word!

You are also welcome to [support us on Patreon](https://www.patreon.com/Team_IO?ty=h)!

## Building the mod
Taam uses a fairly simple implementation of ForgeGradle. To build a ready-to-use jar, you can use the gradle wrapper delivered with the rest of the source code.  
For Windows systems, run this in the console:

    gradlew.bat build

For *nix systems, run this in the terminal:

    ./gradlew build

Installed Gradle versions should also work fine.

## Some info on the internal structure:
Mod & Dependency versions are controlled in the build.gradle and according build.properties file. Mod metadata is replaced in the mcmod.info by gradle during build.