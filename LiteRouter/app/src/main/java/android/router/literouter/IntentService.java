package android.router.literouter;

import android.router.literouter.lib.IntentWrapper;
import android.router.literouter.lib.annotations.ClassName;
import android.router.literouter.lib.annotations.Key;
import android.router.literouter.lib.annotations.RequestCode;

/**
 * @author hiphonezhu@gmail.com
 * @version [Android-BaseLine, 16/10/21 12:23]
 */

public interface IntentService {
    @ClassName("android.router.literouter.ActivityDemo2")
    @RequestCode(100)
    void intent2ActivityDemo2(@Key("platform") String platform, @Key("year") int year);

    @ClassName("android.router.literouter.ActivityDemo2")
    IntentWrapper intent2ActivityDemo2Raw(@Key("platform") String platform, @Key("year") int year);
}
