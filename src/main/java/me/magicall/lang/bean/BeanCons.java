package me.magicall.lang.bean;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;

/**
 * @author Liang Wenjian
 */
public interface BeanCons {

    String IS = "is";
    int IS_LEN = IS.length();

    String GET = "get";
    int GET_LEN = GET.length();

    String SET = "set";
    int SET_LEN = SET.length();

    static boolean isPropGetter(final Method method) {
        return isGetter(method) && !"getClass".equals(method.getName());
    }

    static boolean isGetter(final Method method) {
        return method != null//
                && isGetterName(method.getName())//
                && method.getReturnType() != Void.TYPE//
                && method.getParameterCount() == 0//
                && !Modifier.isStatic(method.getModifiers());
    }

    static boolean isPropGetterName(final String methodName) {
        return isGetterName(methodName) && !"getClass".equals(methodName);
    }

    static boolean isGetterName(final String methodName) {
        if (methodName == null) {
            return false;
        }
        final int length = methodName.length();
        if (methodName.startsWith(GET) && length > GET_LEN) {
            final char ch = methodName.charAt(GET_LEN);
            //注:在无大小写区别的语言中(比如汉语),isLowerCase和isUpperCase都返回false,
            //因此只能检查该字符是否小写字符,若是,则认为是一个以"get"开头的单词的一部分,而非一个"get xxx"
            if (Character.isLowerCase(ch) || !Character.isLetter(ch)) {
                return false;
            } else {
                for (int i = GET_LEN + 1; i < length; i++) {
                    if (!Character.isLetter(methodName.charAt(i))) {
                        return false;
                    }
                }
                return true;
            }
        } else if (methodName.startsWith(IS) && length > IS_LEN) {
            final char ch = methodName.charAt(IS_LEN);
            //注:在无大小写区别的语言中(比如汉语),isLowerCase和isUpperCase都返回false,
            //因此只能检查该字符是否小写字符,若是,则认为是一个以"get"开头的单词的一部分,而非一个"get xxx"
            if (Character.isLowerCase(ch) || !Character.isLetter(ch)) {
                return false;
            } else {
                for (int i = IS_LEN + 1; i < length; i++) {
                    if (!Character.isLetter(methodName.charAt(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    Predicate<String> GETTER_NAME_PREDICATE = BeanCons::isGetterName;

    Predicate<String> PROP_GETTER_NAME_PREDICATE = BeanCons::isPropGetterName;

    Predicate<Method> GETTER_PREDICATE = BeanCons::isGetter;

    Predicate<Method> PROP_GETTER_PREDICATE = BeanCons::isPropGetter;

    static boolean isSetterName(final String methodName) {
        if (methodName == null) {
            return false;
        }
        final int length = methodName.length();
        if (methodName.startsWith(SET) && length > SET_LEN) {
            final char ch = methodName.charAt(SET_LEN);
            //注:在无大小写区别的语言中(比如汉语),isLowerCase和isUpperCase都返回false,
            //因此只能检查该字符是否小写字符,若是,则认为是一个以"set"开头的单词的一部分,而非一个"set xxx"
            if (Character.isLowerCase(ch) || !Character.isLetter(ch)) {
                return false;
            } else {
                for (int i = SET_LEN + 1; i < length; i++) {
                    if (!Character.isLetter(methodName.charAt(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    static boolean isSetter(final Method method) {
        return method != null//
                && isSetterName(method.getName())//
                && method.getReturnType() == Void.TYPE//
                && method.getParameterCount() == 1//
                && !Modifier.isStatic(method.getModifiers());
    }

    Predicate<String> SETTER_NAME_PREDICATE = BeanCons::isSetterName;

    Predicate<Method> SETTER_PREDICATE = m -> isSetterName(m.getName());

}
