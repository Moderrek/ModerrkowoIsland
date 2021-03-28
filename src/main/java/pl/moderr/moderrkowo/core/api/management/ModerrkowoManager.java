package pl.moderr.moderrkowo.core.api.management;

import pl.moderr.moderrkowo.core.Main;

public class ModerrkowoManager {

    // Main
    private final Main main;
    // Managers
    private final UserManager userManager;

    public ModerrkowoManager(Main main){
        this.main = main;
        userManager = new UserManager();
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
