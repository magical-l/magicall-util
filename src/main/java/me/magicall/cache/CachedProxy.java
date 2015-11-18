package me.magicall.cache;

import me.magicall.lang.reflect.MethodSelector.SomeMethodSelectors;
import me.magicall.lang.reflect.proxy.BaseInvocationHandler;
import me.magicall.util.touple.Tuple;
import me.magicall.util.touple.TwoTuple;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对某种接口的方法的运行结果进行缓存的动态代理对象.通常这样使用:
 * I是一个接口,Im是其一个实现类
 * Im commonImpl = ...;
 * I cachedImpl = ProxyUtil.newProxyInstance(new CachedProxy(commonImpl), I.class);
 * 
 * @author MaGiCalL
 */
public class CachedProxy extends BaseInvocationHandler {

	protected final Object internalImplementation;

	protected Map<TwoTuple<Method, List<Object>>, Object> map;

	public CachedProxy(final Object internalImplementation) {
		super();
		this.internalImplementation = internalImplementation;
		map = new HashMap<>();
		setMethodInvokator(proxiedCachedMethodsSelector(), (invocationHandler, proxy, method, args) -> {
            if (method.getReturnType() == Void.class) {
                return null;
            }
			final List<Object> argsList = Arrays.asList(args);
            final TwoTuple<Method, List<Object>> key = Tuple.of(method, argsList);
            Object o = map.get(key);
            if (o == null) {
                o = invokeInternal(invocationHandler, proxy, method, args);
                if (o != null) {
                    map.put(key, o);
                }
            }
            return o;
        });
	}

	/**
	 * @return
	 */
	protected SomeMethodSelectors proxiedCachedMethodsSelector() {
		return SomeMethodSelectors.ALL_NOT_OBJECT_METHOD;
	}

	private Object invokeInternal(final InvocationHandler invocationHandler, final Object proxy, final Method method,
								  final Object... args) {
		try {
			method.setAccessible(true);
			return method.invoke(internalImplementation, args);
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		} catch (final InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
