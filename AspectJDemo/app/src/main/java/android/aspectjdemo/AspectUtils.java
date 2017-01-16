package android.aspectjdemo;

import android.text.TextUtils;

/**
 * @author hiphonezhu@gmail.com
 * @version [AspectJDemo, 17/1/16 10:36]
 */

public class AspectUtils {
    private static String BOOLEAN_STR = "boolean";
    private static String BYTE_STR = "byte";
    private static String INT_STR = "int";
    private static String LONG_STR = "long";
    private static String FLOAT_STR = "float";
    private static String DOUBLE_STR = "double";
    private static String SHORT_STR = "short";

    public static String[] parseArgTypeArrayFromSignature(String signature) {
        if (TextUtils.isEmpty(signature)) {
            return new String[0];
        } else {
            String argTypeStr = signature.substring(signature.indexOf('(') + 1, signature.indexOf(')'));
            String[] argTypes = argTypeStr.split(",");

            return argTypes;
        }
    }

    public static Class<?> getBaseTypeBySimpleName(String simpleName) {
        String t = simpleName.trim();
        if (BOOLEAN_STR.equals(t)) {
            return boolean.class;
        } else if (BYTE_STR.equals(t)) {
            return byte.class;
        } else if (INT_STR.equals(t)) {
            return int.class;
        } else if (LONG_STR.equals(t)) {
            return long.class;
        } else if (FLOAT_STR.equals(t)) {
            return float.class;
        } else if (DOUBLE_STR.equals(t)) {
            return double.class;
        } else if (SHORT_STR.equals(t)) {
            return short.class;
        }

        return null;
    }
}
