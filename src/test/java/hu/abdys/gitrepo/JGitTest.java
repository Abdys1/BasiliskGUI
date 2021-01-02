package hu.abdys.gitrepo;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class JGitTest {

    private File tempDir;

    @BeforeEach
    public void setUpBeforeEach() {
        try {
            tempDir = Files.createTempDirectory("gitTemp").toFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateNewRepository() throws GitAPIException {
        Git.init()
            .setDirectory(tempDir)
            .call();
        assertFalse(Arrays.asList(tempDir.listFiles()).stream().filter(f -> f.getName().equals(".git")).collect(Collectors.toList()).isEmpty());
    }

    @Test
    public void testOpenRepository() throws GitAPIException, IOException {
        Git.init()
                .setDirectory(tempDir)
                .call();
        Git git = Git.open(tempDir);
        Repository repository = git.getRepository();
        assertNotNull(repository);
    }

    @Test
    public void testNotSuccessOpenRepository() {
        assertThrows(RepositoryNotFoundException.class, () -> Git.open(tempDir));
    }

    @AfterEach
    public void tearDownAfterEach() throws IOException {
        File[] allContent = tempDir.listFiles();
        if (allContent != null) {
            for (File file : allContent) {
                file.delete();
            }
        }
        tempDir.delete();
    }
}
