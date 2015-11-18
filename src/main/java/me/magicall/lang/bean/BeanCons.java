package me.magicall.lang.bean;

import java.lang.reflect.Method;
import java.util.function.Predicate;

/**
 * @author Liang Wenjian
 */
public interface BeanCons {

    String GET = "get";
    int GET_LEN = GET.length();
    int GETTER_FIELD_NAME = GET_LEN + 1;

    String SET = "set";
    int SET_LEN = SET.length();
    int SETTER_FIELD_NAME = SET_LEN + 1;

    static boolean isGetterName(final String s) {
        return s != null//
                && s.length() > GET_LEN//
                && s.startsWith(GET)//
                && Character.isUpperCase(s.charAt(GETTER_FIELD_NAME));
    }

    Predicate<String> GETTER_NAME_PREDICATE = BeanCons::isGetterName;

    Predicate<Method> GETTER_PREDICATE = m -> isGetterName(m.getName());

    static boolean isSetterName(final String s) {
        return s != null//
                && s.length() > SET_LEN//
                && s.startsWith(SET)//
                && Character.isUpperCase(s.charAt(SETTER_FIELD_NAME));
    }

    Predicate<String> SETTER_NAME_PREDICATE = BeanCons::isSetterName;

    Predicate<Method> SETTER_PREDICATE = m -> isSetterName(m.getName());

}
