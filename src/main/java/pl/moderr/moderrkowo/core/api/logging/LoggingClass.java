package pl.moderr.moderrkowo.core.api.logging;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LoggingClass {
    LogType type() default LogType.Plugin;
}
