EpicFramework
=============

The all-powerfull Epic Framework.  This is a giant pile of code, not ready for consumption by anyone.

At one point, I wanted to turn this into a polished x-platform platform for app development, but I became convinced that we were "on the wrong side of history", especially with respect to native/web hybrid apps.
Plus, it was an act of insane hubris to even start this project in the first place; What a monstrous undertaking!  Yet, ... it worked!! This platform was used to ship WordFarm on iOS, Android, and Blackberry with a unified Java codebase.

Android was the native test platform, running modern Java.

Blackberry's old 1.3 JVM was handled with the help of http://retrotranslator.sourceforge.net/ and some extensions to their default set of compatibility shims for BB specific deficits.

iOS was handled using XMLVM (with a crap-ton of bugfixes) to mechanically transpile the stack-base JVM bytecode into an equivalent C implementation.

Feel free to go hunting around for scraps of ideas and reusable code.

If anyone stumbles upon this and is curious, this was my baby for a long time, so I'd be happy to explain more.
