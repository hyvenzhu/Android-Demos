package android.aspectjdemo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author hiphonezhu@gmail.com
 * @version [AspectJDemo, 17/1/12 14:17]
 */
@Aspect
public class PermissionAspect {
    @Pointcut("execution(@android.aspectjdemo.MPermission * *(..)) && @annotation(permission)")
    public void methodAnnotatedWithMPermission(MPermission permission) {}

    @Around("methodAnnotatedWithMPermission(permission)")
    public void checkPermission(final ProceedingJoinPoint joinPoint, MPermission permission) throws Throwable {
        // 权限
        String permissionStr = permission.value();

        // 模拟权限申请
        MainActivity mainActivity = (MainActivity) joinPoint.getThis();  // 一般使用栈顶Activity作为上下文
        new AlertDialog.Builder(mainActivity).setTitle("提示")
                .setMessage(permissionStr)
                .setNegativeButton("取消", null)
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            // 继续执行原方法
                            joinPoint.proceed();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }

                    }
                }).create().show();
    }
}
