package android.router.literouter;

import android.content.Intent;
import android.router.literouter.lib.IntentWrapper;
import android.router.literouter.lib.Interceptor;
import android.router.literouter.lib.LiteRouter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LiteRouter liteRouter = new LiteRouter.Builder().interceptor(new Interceptor() {
            @Override
            public boolean intercept(IntentWrapper intentWrapper) {
                return false;
            }
        }).build();
        final IntentService intentService = liteRouter.create(IntentService.class, this);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentService.intent2ActivityDemo2("android", 2016);
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentWrapper intentWrapper = intentService.intent2ActivityDemo2Raw("android", 2016);
                // intent
                Intent intent = intentWrapper.getIntent();
                // add your flags
                intentWrapper.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                // start
                intentWrapper.start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("requestCode: ", String.valueOf(requestCode));
        Log.e("resultCode: ", String.valueOf(resultCode));
    }
}
