package pl.moderr.moderrkowo.core.api.management;

import pl.moderr.moderrkowo.core.Main;

public class ModerrkowoManager {

    // Managers
    private final UserManager userManager;

    public ModerrkowoManager(Main main){
        // Main
        userManager = new UserManager(main);
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
