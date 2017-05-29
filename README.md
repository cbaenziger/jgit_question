# What is this?
This repository reproduces a JGit `Missing unknown` exception. I do not understand why it gets hit. It tries to clone https://github.com/apache/oozie branch `ap-pages`.

# How to reproduce
One can run:
1. `mvn package`
2. `java -cp target/my-app-0.0.1-SNAPSHOT.jar net.clayb.Test`

One is expected to see:
```
clayb@laptop:/tmp/Test$ java -cp target/my-app-0.0.1-SNAPSHOT.jar net.clayb.Test
Local mkdir called creating temp. dir at: /tmp/Test/./git_54096211423187804262738193174874
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
Exception in thread "main" org.eclipse.jgit.api.errors.JGitInternalException: Missing unknown 6121dc0cd19c3f7d2f4412922cffd1ff47e6f756
	at org.eclipse.jgit.api.CloneCommand.call(CloneCommand.java:218)
	at net.clayb.Test.main(Test.java:44)
Caused by: org.eclipse.jgit.errors.MissingObjectException: Missing unknown 6121dc0cd19c3f7d2f4412922cffd1ff47e6f756
	at org.eclipse.jgit.internal.storage.file.WindowCursor.open(WindowCursor.java:163)
	at org.eclipse.jgit.lib.ObjectReader.open(ObjectReader.java:234)
	at org.eclipse.jgit.revwalk.RevWalk.parseAny(RevWalk.java:859)
	at org.eclipse.jgit.revwalk.RevWalk.parseCommit(RevWalk.java:772)
	at org.eclipse.jgit.api.CloneCommand.parseCommit(CloneCommand.java:437)
	at org.eclipse.jgit.api.CloneCommand.checkout(CloneCommand.java:340)
	at org.eclipse.jgit.api.CloneCommand.call(CloneCommand.java:215)
	... 1 more
```

I do not see the git repo having any inconsistencies (other than the branch being "unborn"?) but that is likely due to the incomplete nature of the repository.
```
clayb@laptop:/tmp/Test$ cd /tmp/Test/./git_54096211423187804262738193174874
clayb@laptop:/tmp/Test/git_54096211423187804262738193174874$ git fsck
notice: HEAD points to an unborn branch (master)
Checking object directories: 100% (256/256), done.
Checking objects: 100% (47433/47433), done.
clayb@laptop:/tmp/Test/git_54096211423187804262738193174874$ echo $?
0
```

# References

I have reviewed the threads which spawned:
* [JGti Bug 466858](https://bugs.eclipse.org/bugs/show_bug.cgi?id=466858) -- this is how I found `.setBranchesToClone()`
* [JGit Bug 488681](https://bugs.eclipse.org/bugs/show_bug.cgi?id=488681) -- this recommended `git fsck`
