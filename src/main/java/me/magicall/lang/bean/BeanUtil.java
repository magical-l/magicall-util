package me.magicall.lang.bean;

import com.google.common.collect.Lists;
import me.magicall.lang.reflect.ClassUtil;
import me.magicall.lang.reflect.MethodUtil;
import me.magicall.lang.reflect.proxy.ProxyUtil;
import me.magicall.lang.reflect.proxy.bean.BeanProxy;

import java.lang.reflect.Method;
import java.util.Collection;

public class BeanUtil implements BeanCons {

    /**
     * 通过属性名获取getter的名字。
     * xxx -> getXxx
     *
     * @param fieldName
     * @return
     */
    public static String fieldNameToGetterName(final String fieldName) {
        return GET + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }

    /**
     * 通过属性名获得setter的名字。
     * xxx -> setXxx
     *
     * @param fieldName
     * @return
     */
    public static String fieldNameToSetterName(final String fieldName) {
        return SET + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }

    /**
     * 通过getter的名字获得属性名。
     * getXxx -> xxx
     *
     * @param methodName
     * @return
     */
    public static String getterNameToFieldName(final String methodName) {
        return Character.toLowerCase(methodName.charAt(GET_LEN)) + methodName.substring(GET_LEN);
    }

    /**
     * 通过setter的名字获得属性名。
     * setXxx -> xxx
     *
     * @param methodName
     * @return
     */
    public static String setterNameToFieldName(final String methodName) {
        return Character.toLowerCase(methodName.charAt(SET_LEN)) + methodName.substring(SET_LEN);
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
        final Collection<Method> rt = Lists.newArrayList();
        final Method[] methods = clazz.getMethods();
        for (final Method m : methods) {
            if (BeanCons.isGetter(m)) {
                rt.add(m);
            }
        }
        return rt;
    }

    /**
     * 获取一个类的所有setter.
     * 对返回结果进行修改是安全的.
     *
     * @param clazz
     * @return
     */
    public static Collection<Method> getAllSetters(final Class<?> clazz) {
        final Method[] methods = clazz.getMethods();
        final Collection<Method> rt = Lists.newArrayListWithExpectedSize(methods.length);
        for (final Method m : methods) {
            if (BeanCons.isSetter(m)) {
                rt.add(m);
            }
        }
        return rt;
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
