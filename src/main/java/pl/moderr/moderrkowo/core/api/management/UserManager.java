package pl.moderr.moderrkowo.core.api.management;

import org.jetbrains.annotations.Contract;
import pl.moderr.moderrkowo.core.api.exceptions.UserIsNotLoaded;
import pl.moderr.moderrkowo.core.api.logging.LogType;
import pl.moderr.moderrkowo.core.api.logging.LoggingClass;
import pl.moderr.moderrkowo.core.api.logging.ModerrkowoLogger;
import pl.moderr.moderrkowo.core.api.mysql.callback.IMySQLUpdateCallback;
import pl.moderr.moderrkowo.core.api.mysql.struct.LocalUser;

import java.util.HashMap;
import java.util.UUID;

@LoggingClass(type = LogType.UserManager)
public class UserManager {

    HashMap<UUID, LocalUser> userMap;

    @Contract(pure = true)
    public UserManager(){
        userMap = new HashMap<>();
    }

    public LocalUser getLocalUser(UUID MinecraftId) throws UserIsNotLoaded {
        if(userMap.containsKey(MinecraftId)){
            return userMap.get(MinecraftId);
        }else{
            ModerrkowoLogger.log("Użytkownik nie jest załadowany " + MinecraftId, this);
            throw new UserIsNotLoaded();
        }
    }
    public LocalUser getLocalUser(String MinecraftId) throws UserIsNotLoaded {
        UUID uuid = UUID.fromString(MinecraftId);
        if(userMap.containsKey(uuid)){
            return userMap.get(uuid);
        }else{
            ModerrkowoLogger.log("Użytkownik nie jest załadowany " + MinecraftId, this);
            throw new UserIsNotLoaded();
        }
    }

    public void unloadLocalUser(UUID MinecraftId, boolean save) throws UserIsNotLoaded {
        if(userMap.containsKey(MinecraftId)){
            if(save){
                ModerrkowoLogger.log("Próba zapisywania użytkownika " + MinecraftId, this);
                userMap.get(MinecraftId).Save(new IMySQLUpdateCallback() {
                    @Override
                    public void startUpdate() {
                        ModerrkowoLogger.log("Zapisywanie użytkownika " + MinecraftId, this);
                    }

                    @Override
                    public void onUpdated() {
                        ModerrkowoLogger.log("Zapisano użytkownika " + MinecraftId, this);
                        userMap.remove(MinecraftId);
                        ModerrkowoLogger.log("Usunięto użytkownika z pamięci" + MinecraftId, this);
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        ModerrkowoLogger.log("Wystąpił błąd podczas zapisywania " + MinecraftId + " [" + exception.getMessage() + "]", this);
                    }
                });
            }else{
                ModerrkowoLogger.log("Usuwanie bez zapisywania użytkownika" + MinecraftId, this);
                userMap.remove(MinecraftId);
                ModerrkowoLogger.log("Usunięto użytkownika z pamięci" + MinecraftId, this);
            }
        }else{
            ModerrkowoLogger.log("Użytkownik nie jest załadowany " + MinecraftId, this);
            throw new UserIsNotLoaded();
        }
    }
    public void unloadLocalUser(String MinecraftId, boolean save) throws UserIsNotLoaded {
        UUID uuid = UUID.fromString(MinecraftId);
        if(userMap.containsKey(uuid)){
            if(save){
                ModerrkowoLogger.log("Próba zapisywania użytkownika " + MinecraftId, this);
                userMap.get(uuid).Save(new IMySQLUpdateCallback() {
                    @Override
                    public void startUpdate() {
                        ModerrkowoLogger.log("Zapisywanie użytkownika " + MinecraftId, this);
                    }

                    @Override
                    public void onUpdated() {
                        ModerrkowoLogger.log("Zapisano użytkownika " + MinecraftId, this);
                        userMap.remove(uuid);
                        ModerrkowoLogger.log("Usunięto użytkownika z pamięci" + MinecraftId, this);
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        ModerrkowoLogger.log("Wystąpił błąd podczas zapisywania " + MinecraftId + " [" + exception.getMessage() + "]", this);
                    }
                });
            }else{
                ModerrkowoLogger.log("Usuwanie bez zapisywania użytkownika" + MinecraftId, this);
                userMap.remove(uuid);
                ModerrkowoLogger.log("Usunięto użytkownika z pamięci" + MinecraftId, this);
            }
        }else{
            ModerrkowoLogger.log("Użytkownik nie jest załadowany " + MinecraftId, this);
            throw new UserIsNotLoaded();
        }
    }

}
