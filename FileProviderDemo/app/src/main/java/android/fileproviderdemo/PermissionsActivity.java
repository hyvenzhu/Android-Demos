package android.fileproviderdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 6.0权限申请界面
 * @author hiphonezhu@gmail.com
 * @version [Android-BaseLine, 16/9/5 17:12]
 */
public class PermissionsActivity extends AppCompatActivity {
    /**
     * 启动Activity
     * @param activity
     * @param requestCode
     * @param permissionDesc 权限描述, 例如:发送短信、访问相机。如为空, 会显示"必要"字样
     * @param permissions 权限列表
     */
    public static void actionStartForResult(Activity activity, int requestCode, String permissionDesc, String... permissions)
    {
        Intent intent = new Intent(activity, PermissionsActivity.class);
        intent.putExtra("permissionDesc", permissionDesc);
        intent.putExtra("permissions", permissions);
        activity.startActivityForResult(intent, requestCode);
    }

    String[] permissions; // 待申请权限
    String permissionDesc; // 权限描述, 例如:发送短信、访问相机
    public static final int PERMISSIONS_GRANTED = 0; // 权限授权
    public static final int PERMISSIONS_DENIED = 1; // 权限拒绝

    final int REQUEST_PERMISSIONS_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissions = getIntent().getStringArrayExtra("permissions");
        permissionDesc = getIntent().getStringExtra("permissionDesc");

        if (hasAllPermissionsGranted(permissions))
        {
            setResult(PERMISSIONS_GRANTED);
            finish();
        }
        else
        {
            requestPermissions();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isRequesting && !hasAllPermissionsGranted(permissions))
        {
            requestPermissions();
        }
        else if (!isRequesting && hasAllPermissionsGranted(permissions))
        {
            setResult(PERMISSIONS_GRANTED);
            finish();
        }
    }

    /**
     * 请求权限
     */
    private void requestPermissions()
    {
        permissions = getAllDeniedPermissions();

        isRequesting = true;
        /**
         * 一句话: 被用户拒绝过(第一次申请), 再次申请时如果直接requestPermissions显得比较唐突(会显示一个带有”Don’t ask again”的系统对话框)
         * , 所以需要一个友好的对话框(我们自己的)向用户解释下为何需要这个权限。
         *
         * 场景如下:
         * 1、如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
         * 2、如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
         * 3、如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
         */
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                permissions[0])) // 如果非第一个权限用户勾选了”Don’t ask again”, 并且拒绝了。那么不会提示该类权限, 所以建议每次申请一个权限。
        {
            showMissingPermissionDialog(true);
        }
        else
        {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSIONS_CODE && hasAllPermissionsGranted(grantResults))
        {
            setResult(PERMISSIONS_GRANTED);
            finish();
        }
        else
        {
            showMissingPermissionDialog(false);
        }
    }

    boolean isRequesting;
    /**
     * 权限提示对话框
     * @param isShowRationale
     */
    private void showMissingPermissionDialog(final boolean isShowRationale) {
        String formatStr = null;
        if (isShowRationale)
        {
            formatStr = getString(R.string.permission_desc_text1);
        }
        else
        {
            formatStr = getString(R.string.permission_desc_text2);
        }
        String message = String.format(formatStr, TextUtils.isEmpty(permissionDesc)? "必要" : permissionDesc);

        Snackbar.make(getWindow().getDecorView(), message, Snackbar.LENGTH_LONG).setAction(R.string.settings, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRequesting = false;
                if (isShowRationale)
                {
                    ActivityCompat.requestPermissions(PermissionsActivity.this, permissions, REQUEST_PERMISSIONS_CODE);
                }
                else
                {
                    startAppSettings();
                }
            }
        }).setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                // 不是点击按钮取消的
                if (event != Snackbar.Callback.DISMISS_EVENT_ACTION)
                {
                    setResult(PERMISSIONS_DENIED);
                    finish();
                }
            }
        }).show();
    }

    /**
     * 所有权限都已通过
     * @param grantResults
     * @return
     */
    private boolean hasAllPermissionsGranted(@NonNull int[] grantResults)
    {
        for(int grantResult: grantResults)
        {
            if (grantResult == PackageManager.PERMISSION_DENIED)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 所有权限都已通过
     * @param permissions
     * @return
     */
    private boolean hasAllPermissionsGranted(@NonNull String[] permissions)
    {
        for(String permission: permissions)
        {
            if (ActivityCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 返回所有被拒绝的权限
     * @return
     */
    private String[] getAllDeniedPermissions()
    {
        List<String> deniedPermissions = new ArrayList<>();
        for(String permission: permissions)
        {
            if (ActivityCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED)
            {
                deniedPermissions.add(permission);
            }
        }
        String[] permissions = new String[deniedPermissions.size()];
        deniedPermissions.toArray(permissions);
        return permissions;
    }

    /**
     * 启动应用设置
     */
    private void startAppSettings()
    {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
    }
}
