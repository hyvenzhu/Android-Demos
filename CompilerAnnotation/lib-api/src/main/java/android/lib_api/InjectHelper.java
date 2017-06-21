package android.lib_api;

import android.app.Activity;

import java.lang.reflect.Constructor;

/**
 * @author hiphonezhu@gmail.com
 * @version [CompilerAnnotation, 17/6/20 11:28]
 */

public class InjectHelper {
    public static void inject(Activity host) {
        String classFullName = host.getClass().getName() + "$$ViewInjector";
        try {
            Class proxy = Class.forName(classFullName);
            Constructor constructor = proxy.getConstructor(host.getClass());
            constructor.newInstance(host);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
