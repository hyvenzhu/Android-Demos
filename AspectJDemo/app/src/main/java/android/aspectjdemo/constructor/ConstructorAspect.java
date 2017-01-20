package android.aspectjdemo.constructor;

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
public class ConstructorAspect {
    private static final String TAG = "ConstructorAspect";

    @Pointcut("call(android.aspectjdemo.animal..*.new(..))")
    public void callConstructor() {}

//    @Before("callConstructor()")
//    public void beforeConstructorCall(JoinPoint joinPoint) {
//        Log.e(TAG, "before->" + joinPoint.getThis().toString() + "#" + joinPoint.getSignature().getName());
//    }
//
//    @After("callConstructor()")
//    public void afterConstructorCall(JoinPoint joinPoint) {
//        Log.e(TAG, "after->" + joinPoint.getThis().toString() + "#" + joinPoint.getSignature().getName());
//    }

    /**
     * 不能和Before、After一起使用
     * @param joinPoint
     * @throws Throwable
     */
//    @Around("callConstructor()")
//    public void aroundConstructorCall(ProceedingJoinPoint joinPoint) throws Throwable {
//        Log.e(TAG, "around->" + joinPoint.getThis().toString() + "#" + joinPoint.getSignature().getName());
//
//        // 执行原代码
////        joinPoint.proceed();
//    }

    @Pointcut("execution(android.aspectjdemo.animal.Animal.new(..))")
    public void executionConstructor() {}

//    @Before("executionConstructor()")
//    public void beforeConstructorExecution(JoinPoint joinPoint) {
//        Log.e(TAG, "before->" + joinPoint.getThis().toString() + "#" + joinPoint.getSignature().getName());
//    }
//
//    @After("executionConstructor()")
//    public void afterConstructorExecution(JoinPoint joinPoint) {
//        Log.e(TAG, "after->" + joinPoint.getThis().toString() + "#" + joinPoint.getSignature().getName());
//    }

    /**
     * 不能和Before、After一起使用
     * @param joinPoint
     * @throws Throwable
     */
    @Around("executionConstructor()")
    public void aroundConstructorExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.e(TAG, "around->" + joinPoint.getThis().toString() + "#" + joinPoint.getSignature().getName());

        // 执行原代码
        joinPoint.proceed();
    }
}
