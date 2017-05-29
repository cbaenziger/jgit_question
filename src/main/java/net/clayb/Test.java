package net.clayb;

import java.io.File;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Arrays;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.transport.*;
import org.eclipse.jgit.util.FS;

public class Test {
    public static void main(String[] args) {
        File tempD = null;
        try {
            tempD = new File(Files.createTempDirectory(
                Paths.get("."),
                "git_" + Long.toString(System.nanoTime()),
                PosixFilePermissions
                    .asFileAttribute(PosixFilePermissions
                       .fromString("rwx------")))
                .toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create directory: " + e);
        }

        System.out.println("Local mkdir called creating temp. dir at: " +
            tempD.getAbsolutePath());

        CloneCommand cloneCommand = Git.cloneRepository();
        cloneCommand.setURI("https://github.com/apache/oozie");

        cloneCommand.setDirectory(tempD);
        // set our branch identifier
        cloneCommand.setBranchesToClone(Arrays.asList("refs/heads/ap-pages"));
        
        try {
            cloneCommand.call();
        } catch (GitAPIException e) {
            e.printStackTrace();
            throw new RuntimeException("Was unable to clone Git repo: " + e);
        }
    }
}
