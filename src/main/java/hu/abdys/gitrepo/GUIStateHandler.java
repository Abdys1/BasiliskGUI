package hu.abdys.gitrepo;

public interface GUIStateHandler {

    void refreshCurrentRepoScene(IRepository repo);

    void showAlertMsg(String alertMessage);
}
