package android.fileproviderdemo;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

import static android.support.v4.content.FileProvider.getUriForFile;

public class MainActivity extends AppCompatActivity {

    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView)findViewById(R.id.iv);
    }

    File picFile;
    // 7.0以下系统拍照,不考虑6.0动态权限了
    public void takePhotoOld(View v)
    {
        PermissionsActivity.actionStartForResult(this, 98, "拍照", new String[]{Manifest.permission.CAMERA} );
    }

    void realOldTake()
    {
        String cachePath = getApplicationContext().getExternalCacheDir().getPath();
        picFile = new File(cachePath, "test.jpg");
        Uri picUri = Uri.fromFile(picFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
        startActivityForResult(intent, 100);
    }

    // 7.0系统拍照
    public void takePhotoNew(View v)
    {
        //PermissionsActivity.actionStartForResult(this, 99, "拍照", new String[]{Manifest.permission.CAMERA} );
        realNewTake();
    }

    void realNewTake()
    {
        // 重新构造Uri：content://
        File imagePath = new File(getApplicationContext().getExternalCacheDir(), "images");
        if (!imagePath.exists())
        {
            imagePath.mkdirs();
        }
        picFile = new File(imagePath, "test.jpg");
        Uri picUri = getUriForFile(this,
                "com.mydomain.fileprovider", picFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
        // 授予目录临时共享权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 98:
                if (resultCode == PermissionsActivity.PERMISSIONS_GRANTED)
                {
                    realOldTake();
                    Log.d("ActivityDemo3", "权限申请成功");
                }
                else
                {
                    Log.d("ActivityDemo3", "权限申请失败");
                }
                break;
            case 99:
                if (resultCode == PermissionsActivity.PERMISSIONS_GRANTED)
                {
                    realNewTake();
                    Log.d("ActivityDemo3", "权限申请成功");
                }
                else
                {
                    Log.d("ActivityDemo3", "权限申请失败");
                }
                break;
            case 100:
                if (resultCode == RESULT_OK)
                {
                    iv.setImageURI(Uri.fromFile(picFile));
                }
                break;
        }
    }
}
