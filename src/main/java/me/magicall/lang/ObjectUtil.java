package me.magicall.lang;

import me.magicall.coll.CollFactory.L;
import me.magicall.mark.Copyable;
import me.magicall.util.kit.Kits;
import me.magicall.util.kit.NumKit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ObjectUtil {

    /**
     * 若对象实现了Copyable接口，则复制之，否则返回null
     *
     * @param object
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T tryCopy(final T object) {
        if (object instanceof Copyable<?>) {
            final T t = (T) ((Copyable<?>) object).clone();
            return t == null ? null : t;
        } else {
            return null;
        }
    }

    /**
     * 将一个对象转化成列表：
     * 如果它为null，转化为不可修改的空列表
     * 如果它为Collection或数组或Enumeration，复制为一个List
     * 否则转化为一个单元素列表。
     *
     * @param o
     * @return
     */
    public static List<Object> castToList(final Object o) {
        if (o == null) {
            return Kits.LIST.emptyValue();
        }
        if (o instanceof Collection<?>) {
            return new ArrayList<>((Collection<?>) o);
        }
        if (o.getClass().isArray()) {
            return ArrayUtil.asList(o);
        }
        if (o instanceof Enumeration<?>) {
            final Enumeration<?> e = (Enumeration<?>) o;
            final List<Object> list = new ArrayList<>();
            while (e.hasMoreElements()) {
                list.add(e.nextElement());
            }
            return list;
        }
        return L.asList(o);
    }

    /**
     * 判断一个对象是否“包含多个对象”：是可迭代的（Iterable）、数组或Enumeration。
     *
     * @param o
     * @return
     */
    public static boolean isMultiValue(final Object o) {
        return o instanceof Iterable<?>//
                || o.getClass().isArray()//
                || o instanceof Enumeration<?>;
    }

    /**
     * <pre>
     * 深度比较两个对象是否相等.
     * tips:
     * 【java.utils.Arrays.deepEquals没有解决两个数组互相引用的问题,该问题会引发一个死循环(java.lang.StackOverflowError)
     * 此方法会对这种情况返回false
     * 示例:
     * 	 	Object[] o1 = { null };	Object[] o2 = { null };
     * 		o1[0] = o2;		o2[0] = o1;
     * 		System.out.print(ObjUtil.OBJ.deepEquals(o1,o2);
     * 		System.out.print(Arrays.deepEquals(o1, o2));】
     * </pre>
     *
     * @param o1
     * @param o2
     * @return
     * @author lwj
     */
    public static boolean deepEquals(final Object o1, final Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null) {
            return false;
        }
        if (o1.equals(o2)) {
            return true;
        }

        return deepEquals(o1, o2, null);
    }

    static boolean deepEquals(final Object o1, final Object o2, Set<Object> escape) {
        if (escape != null && (escape.contains(o1) || escape.contains(o2))) {
            return false;
        }

        final Class<?> c1 = o1.getClass();
        final Class<?> c2 = o2.getClass();
        //其中一个不是数组而另一个是数组的情况
        if (!c1.isArray()) {
            if (c2.isArray()) {
                return false;
            } else {//都不是数组
                if (o1.equals(o2)) {
                    return true;
                }
                if (o1 instanceof Number && o2 instanceof Number) {//数字比较
                    return NumKit.deepEquals((Number) o1, (Number) o2);
                }
                return false;
            }
        } else if (!c2.isArray()) {
            return false;
        }
        //都是数组
        final Class<?> cc1 = c1.getComponentType();
        final Class<?> cc2 = c2.getComponentType();
        if (cc1.isPrimitive()) {
            if (cc2.isPrimitive()) {
                return ArrayUtil.primitivesArrEquals(o1, o2);
            } else {//这里暂时没算外包类
                return false;
            }
        } else if (cc2.isPrimitive()) {
            return false;
        }
        //都不是原始类型数组,是普通数组
        Set<Object> escapeToUse = escape == null ? new HashSet<>() : escape;
        escapeToUse.add(o1);
        escapeToUse.add(o2);
        return ArrayUtil.deepEquals((Object[]) o1, (Object[]) o2, escapeToUse);
    }
}
