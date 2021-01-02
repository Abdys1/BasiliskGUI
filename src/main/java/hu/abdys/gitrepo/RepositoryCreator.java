package hu.abdys.gitrepo;

import java.nio.file.Path;
import java.util.Optional;

public interface RepositoryCreator {
    Optional<IRepository> createNewRepository(Path repoPath);
}
