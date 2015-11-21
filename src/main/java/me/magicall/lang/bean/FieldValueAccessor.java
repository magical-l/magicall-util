package me.magicall.lang.bean;

import me.magicall.lang.reflect.MethodUtil;
import me.magicall.util.touple.Tuple;
import me.magicall.util.touple.TwoTuple;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 从某一类的实例对象中取出或设置值的工具.
 *
 * @param <E>
 * @author MaGiCalL
 */
public interface FieldValueAccessor<E> {

    /**
     * 从一个对象中获取某属性的值。
     *
     * @param obj
     * @param fieldName
     * @return
     */
    <T> T getValue(E obj, String fieldName);

    /**
     * 将指定值设置给某对象。
     *
     * @param obj
     * @param fieldName
     * @param value
     */
    void setValue(E obj, String fieldName, Object value);

    FieldValueAccessor<Map<String, Object>> MAP_VALUE_ACCESSOR = new FieldValueAccessor<Map<String, Object>>() {

        @Override
        @SuppressWarnings("unchecked")
        public <T> T getValue(final Map<String, Object> obj, final String fieldName) {
            return (T) obj.get(fieldName);
        }

        @Override
        public void setValue(final Map<String, Object> obj, final String fieldName, final Object value) {
            obj.put(fieldName, value);
        }
    };

    FieldValueAccessor<Object> BEAN_VALUE_ACCESSOR = new FieldValueAccessor<Object>() {

        private final Map<TwoTuple<Class<?>, String>, Method> getters = new WeakHashMap<>();
        private final Map<TwoTuple<Class<?>, String>, Method> setters = new WeakHashMap<>();

        @Override
        public <T> T getValue(final Object obj, final String fieldName) {
            final Class<?> c = obj.getClass();
            @SuppressWarnings("unchecked")
            final TwoTuple<Class<?>, String> key = (TwoTuple) Tuple.of(c, fieldName);
            Method method = getters.get(key);
            if (method == null) {
                method = BeanUtil.getGetter(fieldName, c);
                getters.put(key, method);
            }
            @SuppressWarnings("unchecked")
            final T t = (T) MethodUtil.invokeMethod(obj, method);
            return t;
        }

        @Override
        public void setValue(final Object obj, final String fieldName, final Object value) {
            if (value == null) {
                return;
            }
            final Class<?> c = obj.getClass();
            @SuppressWarnings("unchecked")
            final TwoTuple<Class<?>, String> key = (TwoTuple) Tuple.of(c, fieldName);
            Method method = setters.get(key);
            if (method == null) {
                method = BeanUtil.getSetterIgnoreNameCaseAndTypeAssigned(c, value.getClass());
                setters.put(key, method);
            }
            MethodUtil.invokeMethod(obj, method, value);
        }
    };
}
