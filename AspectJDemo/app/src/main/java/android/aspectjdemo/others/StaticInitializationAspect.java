package android.aspectjdemo.others;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author hiphonezhu@gmail.com
 * @version [AspectJDemo, 17/1/20 10:28]
 */
@Aspect
public class StaticInitializationAspect {
    private static final String TAG = "StaticAspect";

    @Pointcut("staticinitialization(android.aspectjdemo.animal.Animal)")
    public void staticBlock() {}

    @Before("staticBlock()")
    public void beforeStaticBlock(JoinPoint joinPoint) {
        Log.d(TAG, "beforeStaticBlock: ");
    }

//    @After("staticBlock()")
//    public void afterStaticBlock(JoinPoint joinPoint) {
//        Log.d(TAG, "afterStaticBlock: ");
//    }

//    /**
//     * @param joinPoint
//     * @throws Throwable
//     */
//    @Around("staticBlock()")
//    public void aroundStaticBlock(ProceedingJoinPoint joinPoint) throws Throwable {
//        // 执行原代码
////        joinPoint.proceed();
//        Log.d(TAG, "aroundStaticBlock: ");
//    }
}
