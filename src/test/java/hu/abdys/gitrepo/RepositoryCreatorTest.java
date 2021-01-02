package hu.abdys.gitrepo;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.Repository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GitRepositoryCreator implements RepositoryCreator {

    @Override
    public Optional<IRepository> createNewRepository(Path repoPath) {
        try {
            Git git = Git.open(repoPath.toFile());
            return Optional.of(new GitRepository());
        } catch (IOException | NullPointerException e) {
            return Optional.empty();
        }
    }
}

class GitRepository implements IRepository {

}

class GitRepositoryCreatorTest {

    private static RepositoryCreator creator;
    private static MockedStatic<Git> gitMockedStatic;
    private static Path repoPath;

    @BeforeAll
    public static void setUp() {
        creator = new GitRepositoryCreator();

        gitMockedStatic = mockStatic(Git.class);
        repoPath = mock(Path.class);
        File repoFile = mock(File.class);

        when(repoPath.toFile()).thenReturn(repoFile);
    }

    @BeforeEach
    public void setUpBeforeEach() {
        gitMockedStatic.reset();
    }

    @Test
    public void whenPathPointToValidRepo_thenCreateNewRepoObject() throws IOException {
        Git git = mock(Git.class);

        when(Git.open(repoPath.toFile())).thenReturn(git);

        assertTrue(creator.createNewRepository(repoPath).isPresent());
    }

    @Test
    public void whenRepoPathIsInvalid_thenDoesNotCreateNewRepo() throws IOException {
        when(Git.open(repoPath.toFile())).thenThrow(RepositoryNotFoundException.class);

        assertTrue(creator.createNewRepository(null).isEmpty());
        assertTrue(creator.createNewRepository(repoPath).isEmpty());
    }

    @Test
    public void whenOpenIsSuccess_thenSaveRepoInfoToRepoObject() throws IOException {
        Git git = mock(Git.class);
        Repository repository = mock(Repository.class);

        when(Git.open(repoPath.toFile())).thenReturn(git);
        when(git.getRepository()).thenReturn(repository);

        IRepository gitRepo = creator.createNewRepository(repoPath).get();
    }

}