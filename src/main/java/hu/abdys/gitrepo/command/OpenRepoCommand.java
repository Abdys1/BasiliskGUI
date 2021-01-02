package hu.abdys.gitrepo.command;

import hu.abdys.gitrepo.GUIStateHandler;
import hu.abdys.gitrepo.IRepository;
import hu.abdys.gitrepo.RepositoryCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.Optional;

final class OpenRepoCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(OpenRepoCommand.class);

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
        logger.info("Command execution start! Try open git repository at path: " + repoPath.toAbsolutePath().toString());
        Optional<IRepository> optionalRepo = creator.createNewRepository(repoPath);
        optionalRepo.ifPresentOrElse(guiHandler::refreshCurrentRepoScene,
                () -> guiHandler.showAlertMsg("A megadott elérési úton nem található repository!")); //TODO I18N
    }
}
