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
 * @version [AspectJDemo, 17/1/20 14:29]
 */
@Aspect
public class AdviceExecutionAspect {
    private static final String TAG = "AdviceExecutionAspect";

    /**
     * !within(AdviceExecutionAspect): 排除当前类，防止栈溢出
     */
    @Pointcut("adviceexecution() && !within(AdviceExecutionAspect)")
    public void advice(){}

//    @Before("advice()")
//    public void beforeAdvice(JoinPoint joinPoint) {
//        Log.d(TAG, "beforeAdvice: " + joinPoint.getSignature().getName());
//    }
//
//    @After("advice()")
//    public void afterAdvice(JoinPoint joinPoint) {
//        Log.d(TAG, "afterAdvice: " + joinPoint.getSignature().getName());
//    }

    @Around("advice()")
    public void aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.d(TAG, "aroundAdvice: " + joinPoint.getSignature().getName());
        // 执行原代码
        joinPoint.proceed();
    }
}
