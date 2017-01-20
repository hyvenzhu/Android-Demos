package android.aspectjdemo;

import android.Manifest;
import android.aspectjdemo.constructor.ConstructorActivity;
import android.aspectjdemo.field.FieldActivity;
import android.aspectjdemo.method.MethodActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.method:
                startActivity(new Intent(this, MethodActivity.class));
                break;
            case R.id.constructor:
                startActivity(new Intent(this, ConstructorActivity.class));
                break;
            case R.id.field:
                startActivity(new Intent(this, FieldActivity.class));
                break;
        }
    }

    @MPermisson(value = Manifest.permission.CAMERA)
    public void camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(getExternalCacheDir() + "photo.jpg")));
        startActivity(intent);
    }
}
