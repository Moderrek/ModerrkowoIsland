package pl.moderr.moderrkowo.core.api.logging;

import java.util.HashMap;

public class ModerrkowoLogger {

    public final static HashMap<LogType, Boolean> logs = new HashMap<LogType, Boolean>(){
        {
            put(LogType.Unknown, true);
            put(LogType.Plugin, true);
            put(LogType.Mysql, true);
        }
    };

    public static void EnableLog(LogType type){
        logs.replace(type, true);
    }
    public static void DisableLog(LogType type){
        logs.replace(type, false);
    }
    public static boolean GetLog(LogType type){
        return logs.get(type);
    }

    public static void log(String message, Object type){
        log(message, type.getClass());
    }
    public static void log(String message, Class type){
        LoggingClass annotation = (LoggingClass) type.getAnnotation(LoggingClass.class);
        LogType logType = LogType.Unknown;
        if(!logs.get(logType)){
            return;
        }
        if(annotation != null){
            logType = annotation.type();
        }
        //System.out.println("[" + type.getSimpleName().toUpperCase() + "] [" + logType.toString().toUpperCase() + "] " + message);
        System.out.println("[" + logType.toString().toUpperCase() + "] " + type.getSimpleName() + ": " + message);
    }

}
