package android.aspectjdemo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hiphonezhu@gmail.com
 * @version [AspectJDemo, 17/1/12 14:15]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MPermisson {
    String value();
}
