package me.magicall.lang.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

public class MethodUtil {

    /**
     * 判断一个方法是否Object类定义的。
     *
     * @param method
     * @return
     */
    public static boolean isObjectClass(final Method method) {
        return method.getDeclaringClass() == Object.class;
    }

    /**
     * 判断一个方法是否Object类定义的getClass()方法
     *
     * @param method
     * @return
     */
    public static boolean isGetClass(final Method method) {
        return "getClass".equals(method.getName()) && isObjectClass(method);
    }

    /**
     * 执行方法。不会抛出受检异常。如果执行时抛出异常，则返回null。对执行结果关心度不高的场景很有效。
     *
     * @param obj
     * @param method
     * @param args
     * @return
     */
    public static Object invokeMethod(final Object obj, final Method method, final Object... args) {
        try {
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            return method.invoke(obj, args);
        } catch (final IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param obj
     * @param methods
     * @param methodName
     * @param args
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Object invokeMethodIgnoreCaseAndArgsTypesAssigned(final Object obj, final Method[] methods,
                                                                    final String methodName, final Object... args)
            throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        return invokeMethodIgnoreCaseAndArgsTypesAssigned(obj, Arrays.asList(methods), methodName, args);
    }

    public static Object invokeMethodIgnoreCaseAndArgsTypesAssigned(final Object obj, final Collection<Method> methods,
                                                                    final String methodName, final Object... args)
            throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        final Method method = findMethodIgnoreCaseAndArgsTypesAssigned(methods, methodName,
                ClassUtil.objsToClasses(args));
        if (method == null) {
            throw new NoSuchMethodException();
        }
        return method.invoke(obj, args);
    }

    /**
     * 从一些方法中按名字和参数获取某方法，忽略名字大小写，并且是参数包容的。该字段的名字为Class的简单名，首字母小写。
     * （如：某类A有setString(Object o)方法，用A.class和String.class调用本方法，可以获得此Method对象)。
     *
     * @param methods
     * @param methodName
     * @param argsClasses
     * @return
     */
    public static Method findMethodIgnoreCaseAndArgsTypesAssigned(final Method[] methods, final String methodName,
                                                                  final Class<?>... argsClasses) {
        return findMethodIgnoreCaseAndArgsTypesAssigned(Arrays.asList(methods), methodName, argsClasses);
    }

    /**
     * 从一些方法中按名字和参数获取某方法，忽略名字大小写，并且是参数包容的。该字段的名字为Class的简单名，首字母小写。
     * （如：某类A有setString(Object o)方法，用A.class和String.class调用本方法，可以获得此Method对象)。
     *
     * @param methods
     * @param methodName
     * @param argsClasses
     * @return
     */
    public static Method findMethodIgnoreCaseAndArgsTypesAssigned(final Collection<Method> methods,
                                                                  final String methodName,
                                                                  final Class<?>... argsClasses) {
        Method method = null;
        for (final Method m : methods) {
            final String name = m.getName();
            final Class<?>[] argTypes = m.getParameterTypes();
            if (argsClasses.length != argTypes.length) {
                continue;
            }
            if (name.equalsIgnoreCase(methodName)) {
                if (Arrays.equals(argTypes, argsClasses)) {
                    return m;
                }
                int i = 0;
                for (final Class<?> argType : argTypes) {
                    if (argType.isAssignableFrom(argsClasses[i])) {
                        ++i;
                    } else {
                        break;
                    }
                }
                if (i == argsClasses.length) {
                    method = m;
                }
            }
        }
        return method;
    }

}
