package at.ac.univie.se2.ws21.team0404.app.utils;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Platform independent {@link NonNull} annotation.
 * <p>
 * Can be configured in IDE as default NotNull annotation.
 */
@Documented
@Retention(CLASS)
@Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER})
public @interface NonNull {
}
