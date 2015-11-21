package me.magicall.lang.bean;

import me.magicall.lang.ArrayUtil;
import me.magicall.lang.reflect.ClassUtil;
import me.magicall.lang.reflect.MethodUtil;
import me.magicall.lang.reflect.proxy.ProxyUtil;
import me.magicall.lang.reflect.proxy.bean.BeanProxy;
import me.magicall.util.kit.Kits;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanUtil {

    private static final Map<Class<?>, Collection<Method>> ALL_GETTERS_WITHOUT_GET_CLASS_CACHE
            = new ConcurrentHashMap<>();
    private static final Map<Class<?>, Collection<Method>> ALL_SETTERS_CACHE = new ConcurrentHashMap<>();

    /**
     * 通过属性名获取add方法的名字。
     * xxx -> addXxx
     *
     * @param fieldName
     * @return
     */
    public static String fieldNameToAdderName(final String fieldName) {
        return "add" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }

    /**
     * 通过属性名获取getter的名字。
     * xxx -> getXxx
     *
     * @param fieldName
     * @return
     */
    public static String fieldNameToGetterName(final String fieldName) {
        return BeanCons.GET + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }

    /**
     * 通过属性名获得setter的名字。
     * xxx -> setXxx
     *
     * @param fieldName
     * @return
     */
    public static String fieldNameToSetterName(final String fieldName) {
        return BeanCons.SET + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }

    /**
     * 通过getter的名字获得属性名。
     * getXxx -> xxx
     *
     * @param methodName
     * @return
     */
    public static String getterNameToFieldName(final String methodName) {
        return Character.toLowerCase(methodName.charAt(BeanCons.GET_LEN)) + methodName
                .substring(BeanCons.GETTER_FIELD_NAME);
    }

    /**
     * 通过setter的名字获得属性名。
     * setXxx -> xxx
     *
     * @param methodName
     * @return
     */
    public static String setterNameToFieldName(final String methodName) {
        return Character.toLowerCase(methodName.charAt(BeanCons.SET_LEN)) + methodName
                .substring(BeanCons.SETTER_FIELD_NAME);
    }

    /**
     * 创建参数Class的一个实例对象. 如果参数Class代表一个类(class),则使用其无参构造函数创建一个实例,如果该类没有无参构造函数,则返回null;
     * 如果参数Class代表一个接口(interface),则使用动态代理生成一个代理实例.由于是bean,因此该代理实例仅代理getter和setter方法.
     *
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T newBean(final Class<? extends T> clazz) {
        return (T) (clazz.isInterface() ? ProxyUtil.newProxyInstance(new BeanProxy(), clazz)
                                        : ClassUtil.newInstance(clazz));
    }

    /**
     * 判断一个方法是否setter
     *
     * @param method
     * @param checkReturnType
     * @return
     */
    public static boolean isSetter(final Method method, final boolean checkReturnType) {
        {//name check
            final String methodName = method.getName();
            if (methodName.length() <= BeanCons.SET_LEN) {
                return false;
            }
            if (!methodName.startsWith(BeanCons.SET)) {
                return false;
            }
            //set后的首字母不能是小写.
            //注:在无大小写区别的语言中(比如汉语),isLowerCase和isUpperCase都返回false,
            //因此只能检查该字符是否小写字符,若是,则认为是一个以"set"开头的单词的一部分,而非一个"set xxx"
            if (Character.isLowerCase(methodName.charAt(BeanCons.SET_LEN))) {
                return false;
            }
        }
        {//param check
            final Class<?>[] paramClasses = method.getParameterTypes();
            if (ArrayUtil.isEmpty(paramClasses)) {
                return false;
            }
            if (paramClasses.length > 1) {//没有参数的setXxx()方法或有2个以上参数的setXxx(),不处理
                return false;
            }
        }
        {//return type check
            if (checkReturnType && method.getReturnType() != Void.TYPE) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一个方法是否getter。
     *
     * @param method
     * @return
     */
    public static boolean isGetter(final Method method) {
        {//name check
            final String methodName = method.getName();
            if (methodName.length() <= BeanCons.GET_LEN) {
                return false;
            }
            if (!methodName.startsWith(BeanCons.GET)) {
                return false;
            }
            //get后的首字母不能是小写.
            //注:在无大小写区别的语言中(比如汉语),isLowerCase和isUpperCase都返回false,
            //因此只能检查该字符是否小写字符,若是,则认为是一个以"get"开头的单词的一部分,而非一个"get xxx"
            if (Character.isLowerCase(methodName.charAt(BeanCons.GET_LEN))) {
                return false;
            }
        }
        {//param check
            final Class<?>[] paramClasses = method.getParameterTypes();
            if (!ArrayUtil.isEmpty(paramClasses)) {
                return false;
            }
        }
        {//return type check
            if (method.getReturnType() == Void.TYPE) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取指定类的指定字段的adder方法（addFieldName），忽略名字大小写，并且是参数包容的。该字段的名字为Class的简单名，首字母小写。
     * （如：某类A有setString(Object o)方法，用A.class和String.class调用本方法，可以获得此Method对象)。
     *
     * @param clazz
     * @param fieldClass
     * @return
     */
    public static Method getAdder(final Class<?> clazz, final Class<?> fieldClass) {
        return MethodUtil.findMethodIgnoreCaseAndArgsTypesAssigned(clazz.getMethods(),
                fieldNameToAdderName(fieldClass.getSimpleName()), fieldClass);
    }

    /**
     * 获取某字段的setter，忽略名字大小写，并且是参数包容的。该字段的名字为Class的简单名，首字母小写。
     * （如：某类A有setString(Object o)方法，用A.class和String.class调用本方法，可以获得此Method对象)。
     *
     * @param clazz
     * @param fieldClass
     * @return
     */
    public static Method getSetterIgnoreNameCaseAndTypeAssigned(final Class<?> clazz,
                                                                final Class<?> fieldClass) {//ignore case and type
        return MethodUtil.findMethodIgnoreCaseAndArgsTypesAssigned(clazz.getMethods(),
                fieldNameToSetterName(fieldClass.getSimpleName()), fieldClass);
    }

    /**
     * 获取一个类的所有getter,不包括getClass
     *
     * @param clazz
     * @return
     */
    public static Collection<Method> getAllFieldGetters(final Class<?> clazz) {
        Collection<Method> collection = ALL_GETTERS_WITHOUT_GET_CLASS_CACHE.get(clazz);
        if (collection == null) {
            collection = new ArrayList<>();
            final Method[] methods = clazz.getMethods();
            for (final Method m : methods) {
                final String name = m.getName();
                if (name.startsWith(BeanCons.GET)//以get开头
                        && !MethodUtil.isGetClass(m)//不是getClass
                        && name.length() > BeanCons.GET_LEN//get后面还有字
                        && Character.isUpperCase(name.charAt(BeanCons.GET_LEN))//get后面的字符是大写字符
                        && m.getParameterTypes().length == 0//没有参数
                        && m.getReturnType() != Void.class//有返回值
                        ) {
                    collection.add(m);
                }
            }
            ALL_GETTERS_WITHOUT_GET_CLASS_CACHE.put(clazz, collection);
        }
        return collection;
    }

    /**
     * 获取一个类的所有setter.
     * 对返回结果进行修改是安全的.
     *
     * @param clazz
     * @return
     */
    public static Collection<Method> getAllSetters(final Class<?> clazz) {
        Collection<Method> collection = ALL_SETTERS_CACHE.get(clazz);
        if (collection == null) {
            final Method[] methods = clazz.getMethods();
            collection = new ArrayList<>(methods.length);
            for (final Method m : methods) {
                final String name = m.getName();
                if (name.startsWith(BeanCons.SET)//以set开头
                        && name.length() > BeanCons.SET_LEN//set后面还有字
                        && Character.isUpperCase(name.charAt(BeanCons.SET_LEN))//set后面的字符是大写字符
                        && m.getParameterTypes().length == 1//有一个参数
                        ) {
                    collection.add(m);
                }
            }
            ALL_SETTERS_CACHE.put(clazz, collection);
        }
        return new ArrayList<>(collection);
    }

    /**
     * 获取一个类的所有setter.如果存在两个名字相同(且都是1个参数)的setter,保留参数的类型(Class)较"小"者.
     * 对返回结果进行修改是安全的.
     *
     * @param clazz
     * @return
     * @throws RuntimeException 如果存在两个名字相同(且都是1个参数)的setter,但它们的参数不相容.
     */
    public static Collection<Method> getAllSettersForField(final Class<?> clazz) throws RuntimeException {
        final Collection<Method> setters0 = getAllSetters(clazz);
        if (Kits.COLL.isEmpty(setters0)) {
            return setters0;
        }
        final Map<String, Method> setterNameMap = new LinkedHashMap<>(setters0.size());
        for (final Method setter : setters0) {
            if (!isSetter(setter, false)) {
                continue;
            }
            final Class<?> fieldClass = setter.getParameterTypes()[0];
            final String fieldName = setterNameToFieldName(setter.getName());
            final Method setterFromMap = setterNameMap.get(fieldName);
            if (setterFromMap == null) {
                setterNameMap.put(fieldName, setter);
            } else {
                final Class<?> setterArgFromMap = setterFromMap.getParameterTypes()[0];
                if (fieldClass.isAssignableFrom(setterArgFromMap)) {
                } else if (setterArgFromMap.isAssignableFrom(fieldClass)) {
                    setterNameMap.put(fieldName, setter);
                } else {
                    throw new RuntimeException(
                            '类' + clazz.getSimpleName() + '的' + fieldName + "字段有两个setter方法,但是参数却又不相容");
                }
            }
        }
        return new ArrayList<>(setterNameMap.values());
    }

    public static Method getGetter(final String fieldName, final Class<?> c) {
        try {
            return c.getMethod(fieldNameToGetterName(fieldName));
        } catch (final SecurityException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
