package hu.abdys;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

interface ICommand {
    void execute();
}

class OpenRepoCommand implements ICommand {

    private final GUIStateHandler guiHandler;
    private final RepositoryCreator creator;
    private final Path repoPath;

    public OpenRepoCommand(GUIStateHandler guiHandler, RepositoryCreator creator, Path repoPath) {
        this.guiHandler = guiHandler;
        this.creator = creator;
        this.repoPath = repoPath;
    }

    @Override
    public void execute() {
        Optional<IRepository> optionalRepo = creator.createNewRepository(repoPath);
        if (optionalRepo.isEmpty()) {
            throw new CannotOpenRepositoryException(); //TODO Checked-re átírni (talán IO exceptionnel)
        }

        guiHandler.refreshCurrentRepoScene(optionalRepo.get());
    }
}

class GUIStateHandler {

    public void refreshCurrentRepoScene(IRepository repo) {

    }
}

interface IRepository {

}

class GitRepository implements IRepository {

}

interface RepositoryCreator {
    Optional<IRepository> createNewRepository(Path repoPath);
}

class CannotOpenRepositoryException extends RuntimeException {

}


public class OpenRepositoryCommandTest {


    @Test
    public void whenOpenRepositoryCommandIsSuccess_thenRefreshCurrentRepo() {
        final GUIStateHandler handler = mock(GUIStateHandler.class);
        final RepositoryCreator repoCreator = mock(RepositoryCreator.class);

        final Path repoPath = Path.of("./temp");
        final Optional<IRepository> expectedRepo = Optional.of(new GitRepository());

        when(repoCreator.createNewRepository(repoPath)).thenReturn(expectedRepo);

        ICommand command = new OpenRepoCommand(handler, repoCreator, repoPath);
        command.execute();

        verify(repoCreator).createNewRepository(repoPath);
        verify(handler).refreshCurrentRepoScene(expectedRepo.get());
    }

    @Test
    public void whenRepositoryDoesNotExists_thenCannotOpenIt() {
        final GUIStateHandler handler = mock(GUIStateHandler.class);
        final RepositoryCreator repoCreator = mock(RepositoryCreator.class);

        final Path repoPath = Path.of("./temp");

        when(repoCreator.createNewRepository(repoPath)).thenReturn(Optional.empty());

        ICommand command = new OpenRepoCommand(handler, repoCreator, repoPath);
        assertThrows(CannotOpenRepositoryException.class, command::execute);

        verify(repoCreator).createNewRepository(repoPath);
    }


}
