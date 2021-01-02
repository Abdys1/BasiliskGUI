package hu.abdys.gitrepo.command;

import hu.abdys.gitrepo.GUIStateHandler;
import hu.abdys.gitrepo.IRepository;
import hu.abdys.gitrepo.RepositoryCreator;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Optional;

import static org.mockito.Mockito.*;


public class OpenRepositoryCommandTest {

    @Test
    public void whenOpenRepositoryCommandIsSuccess_thenRefreshCurrentRepo() {
        final GUIStateHandler handler = mock(GUIStateHandler.class);
        final RepositoryCreator repoCreator = mock(RepositoryCreator.class);
        final Path repoPath = mock(Path.class);
        final IRepository repo = mock(IRepository.class);
        final Optional<IRepository> expectedRepo = Optional.of(repo);

        when(repoCreator.createNewRepository(repoPath)).thenReturn(expectedRepo);

        ICommand command = new OpenRepoCommand(handler, repoCreator, repoPath);
        command.execute();

        verify(repoCreator).createNewRepository(repoPath);
        verify(handler).refreshCurrentRepoScene(expectedRepo.get());
    }

    @Test
    public void whenRepositoryDoesNotExists_thenShowAlertMsg() {
        final GUIStateHandler handler = mock(GUIStateHandler.class);
        final RepositoryCreator repoCreator = mock(RepositoryCreator.class);
        final Path repoPath = mock(Path.class);
        final String alertMessage = "A megadott elérési úton nem található repository!"; // TODO I18N

        when(repoCreator.createNewRepository(repoPath)).thenReturn(Optional.empty());

        ICommand command = new OpenRepoCommand(handler, repoCreator, repoPath);
        command.execute();

        verify(handler).showAlertMsg(alertMessage);
        verify(repoCreator).createNewRepository(repoPath);
    }

}
