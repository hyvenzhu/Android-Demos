package android.floatingoval;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

/**
 * @author hiphonezhu@gmail.com
 * @version [FloatingOval, 16/12/29 21:08]
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(new FloatingDragger(this, layoutResID).getView());
    }
}
