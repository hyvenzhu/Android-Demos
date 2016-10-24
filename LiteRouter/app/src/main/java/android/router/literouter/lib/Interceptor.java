package android.router.literouter.lib;

/**
 * Interceptor before actual executor
 * @author hiphonezhu@gmail.com
 * @version [Android-BaseLine, 16/10/22 10:25]
 */

public interface Interceptor {
    /**
     * return true to intercept executor
     * @param intentWrapper the intent wrapper
     * @return
     */
    boolean intercept(IntentWrapper intentWrapper);
}
