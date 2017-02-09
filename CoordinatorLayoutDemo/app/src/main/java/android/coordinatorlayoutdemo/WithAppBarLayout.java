package android.coordinatorlayoutdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * @author hiphonezhu@gmail.com
 * @version [CoordinatorLayoutDemo, 17/2/7 17:57]
 */

public class WithAppBarLayout extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_appbarlayout);

        TextView tv = (TextView)findViewById(R.id.tv);
        for (int i = 0; i < 50; i++) {
            tv.append((i + 1) + "\n");
        }
    }
}
