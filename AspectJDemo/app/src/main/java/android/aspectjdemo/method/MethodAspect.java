package android.aspectjdemo.method;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author hiphonezhu@gmail.com
 * @version [AspectJDemo, 17/1/20 10:28]
 */
@Aspect
public class MethodAspect {
    private static final String TAG = "MethodAspect";

    @Pointcut("call(* android.aspectjdemo.animal.Animal.fly(..))")
    public void callMethod() {}

//    @Before("callMethod()")
//    public void beforeMethodCall(JoinPoint joinPoint) {
//        Log.e(TAG, "before->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());
//    }

//    @After("callMethod()")
//    public void afterMethodCall(JoinPoint joinPoint) {
//        Log.e(TAG, "after->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());
//    }

    /**
     * 不能和Before、After一起使用
     * @param joinPoint
     * @throws Throwable
     */
    @Around("callMethod()")
    public void aroundMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.e(TAG, "around->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());

        // 执行原代码
        joinPoint.proceed();
    }

    @Pointcut("execution(* android.aspectjdemo.animal.Animal.fly(..))")
    public void executionMethod() {}

    @Before("executionMethod()")
    public void beforeMethodExecution(JoinPoint joinPoint) {
        Log.e(TAG, "before->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());
    }

//    @After("executionMethod()")
//    public void afterMethodExecution(JoinPoint joinPoint) {
//        Log.e(TAG, "after->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());
//    }

//    /**
//     * 不能和Before、After一起使用
//     * @param joinPoint
//     * @throws Throwable
//     */
//    @Around("executionMethod()")
//    public void aroundMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
//        Log.e(TAG, "around->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());
//
//        // 执行原代码
////        joinPoint.proceed();
//    }

    /**
     * 替换原方法返回值
     * 注：@Pointcut可以不单独定义方法，直接使用，如下：
     * @Around("execution(* android.aspectjdemo.animal.Animal.getWeight(..))")
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* android.aspectjdemo.animal.Animal.getWeight(..))")
    public int aroundGetAgeMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        int weight = Integer.parseInt(joinPoint.proceed().toString());
        Log.e(TAG, "原始weight: " + weight);
        return 100;
    }

    /**
     * handler
     * 不支持@After、@Around
     */
    @Before("handler(java.lang.ArithmeticException)")
    public void handler() {
        Log.e(TAG, "handler");
    }

    /**
     * @AfterThrowing
     * @param throwable
     */
    @AfterThrowing(pointcut = "call(* *..*(..))", throwing = "throwable")
    public void anyFuncThrows(Throwable throwable) {
        Log.e(TAG, "hurtThrows: ", throwable);
    }

    /**
     * @AfterReturning
     * @param height
     */
    @AfterReturning(pointcut = "execution(* android.aspectjdemo.animal.Animal.getHeight(..)) && args(int)", returning = "height")
    public void getHeight(int height) {
        Log.d(TAG, "getHeight: " + height);
    }
}
