package android.aspectjdemo.field;

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
public class FieldAspect {
    private static final String TAG = "FieldAspect";

    @Pointcut("get(int android.aspectjdemo.animal.Animal.age)")
    public void getField() {}

//    @Before("getField()")
//    public void beforeFieldGet(JoinPoint joinPoint) {
//        Log.e(TAG, "before->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());
//    }
//
//    @After("getField()")
//    public void afterFieldGet(JoinPoint joinPoint) {
//        Log.e(TAG, "after->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());
//    }

//    /**
//     * @param joinPoint
//     * @throws Throwable
//     */
//    @Around("getField()")
//    public int aroundFieldGet(ProceedingJoinPoint joinPoint) throws Throwable {
//        // 执行原代码
//        Object obj = joinPoint.proceed();
//        int age = Integer.parseInt(obj.toString());
//        Log.e(TAG, "age: " + age);
//        return 100;
//    }

    /**
     * 排除Animal构造函数中"写"变量的JPoint：
     * !withincode(android.aspectjdemo.animal..*.new(..))
     */
    @Pointcut("set(int android.aspectjdemo.animal.Animal.age) && !withincode(android.aspectjdemo.animal..*.new(..))")
    public void fieldSet() {}

//    @Before("fieldSet()")
//    public void beforeFieldSet(JoinPoint joinPoint) {
//        Log.e(TAG, "before->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());
//    }
//
//    @After("fieldSet()")
//    public void afterFieldSet(JoinPoint joinPoint) {
//        Log.e(TAG, "after->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());
//    }

    /**
     * 不能和Before、After一起使用
     * @param joinPoint
     * @throws Throwable
     */
    @Around("fieldSet()")
    public void aroundFieldSet(ProceedingJoinPoint joinPoint) throws Throwable {
//        Log.e(TAG, "around->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());

        // 执行原代码
//        joinPoint.proceed();
    }
//
//    /**
//     * 测试target
//     * @param joinPoint
//     */
//    @Before("call(* *..targetFunc(..)) && target(android.aspectjdemo.animal.Animal)")
//    public void target(JoinPoint joinPoint) {
//        Log.d(TAG, "target: " + joinPoint.getTarget().toString());
//    }
}
