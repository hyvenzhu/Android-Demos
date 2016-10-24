package android.router.literouter.lib.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Dest ClassName Annotation
 * @author hiphonezhu@gmail.com
 * @version [Android-BaseLine, 16/10/21 11:30]
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassName {
    String value();
}
