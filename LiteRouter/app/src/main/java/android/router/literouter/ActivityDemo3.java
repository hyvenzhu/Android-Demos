package android.router.literouter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class ActivityDemo3 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo3);

        Intent intent = getIntent();
        String platform = intent.getStringExtra("platform");
        int year = intent.getIntExtra("year", 0);
        Log.e("platform: ", platform);
        Log.e("year: ", String.valueOf(year));
    }
}
