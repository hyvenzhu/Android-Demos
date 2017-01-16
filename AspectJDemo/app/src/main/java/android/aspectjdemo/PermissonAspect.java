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
public class PermissonAspect {
    @Pointcut("execution(@android.aspectjdemo.MPermisson * *(..)) && @annotation(permisson)")
    public void methodAnnotatedWithMPermisson(MPermisson permisson) {}

    @Around("methodAnnotatedWithMPermisson(permisson)")
    public void checkPermisson(final ProceedingJoinPoint joinPoint, MPermisson permisson) throws Throwable {
        String permissonStr = permisson.value();

        // 一般使用栈顶Activity作为上下文
        MainActivity mainActivity = (MainActivity) joinPoint.getThis();
        new AlertDialog.Builder(mainActivity).setTitle("提示")
                .setMessage(permissonStr)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            joinPoint.proceed();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }

                    }
                }).create().show();
    }
}
