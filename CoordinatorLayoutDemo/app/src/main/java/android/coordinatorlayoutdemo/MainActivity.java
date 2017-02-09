package android.coordinatorlayoutdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void intentTo(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                startActivity(new Intent(this, WithFloatingActionButton.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(this, WithAppBarLayout.class));
                break;
            case R.id.btn3:
                startActivity(new Intent(this, WithCollapsingToolbar.class));
                break;
            case R.id.btn4:
                startActivity(new Intent(this, WithCustomBehavior.class));
                break;
        }
    }
}
