package me.magicall.lang.reflect;

import me.magicall.lang.bean.BeanCons;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Predicate;

@FunctionalInterface
public interface MethodSelector extends Predicate<Method>{

	boolean accept(Method method);

	@Override
	default boolean test(final Method method) {
		return accept(method);
	}

	enum SomeMethodSelectors implements MethodSelector {
		TO_STRING("toString"), //
		HASH_CODE("hashCode"), //
		EQUALS("equals", Object.class), //
		GET_CLASS("getClass"), //
		CLONE("clone"), //
		WAIT("wait"), //
		WAIT_LONG("wait", long.class), //
		WAIT_LONG_INT("wait", long.class, int.class), //
		NOTIFY("notify"), //
		NOTIFY_ALL("notifyAll"), //
		FINALIZE("finalize"), //
		/**
		 * 包含{@link Object}类定义的所有方法，包括toString,hashCode,equals,clone,finalize，以及final的方法：notify、notifyAll、wait*3、getClass
		 */
		ALL_OBJECT_METHOD(null) {
			@Override
			public boolean accept(final Method method) {
				return method.getDeclaringClass() == Object.class;
			}
		}, //
		ALL_NOT_OBJECT_METHOD(null) {
			@Override
			public boolean accept(final Method method) {
				return !ALL_OBJECT_METHOD.accept(method);
			}
		}, //
		/**
		 * 包含所有getter方法：以get开头，并且随后跟着至少一个字符，并且以大写字母（Character.toUpperCase(charAfterGet) == charAfterGet）开始
		 * （包括getClass）
		 */
		GETTER_INCLUDING_GET_CLASS(BeanCons.GET) {
			@Override
			boolean nameAccept(final String methodName) {
				if (methodName.length() <= this.methodName.length()) {
					//这里包括纯粹叫get方法
					return false;
				}
				if (methodName.startsWith(this.methodName)) {
					final char charAfterGet = methodName.charAt(this.methodName.length() + 1);
					if (Character.toUpperCase(charAfterGet) == charAfterGet) {
						return true;
					}
				}
				return false;
			}
		}, //
		/**
		 * 包含除了getClass之外的所有getter：以get开头，并且随后跟着至少一个字符，并且以大写字母（Character.toUpperCase(charAfterGet) == charAfterGet）开始
		 */
		GETTER(BeanCons.GET) {
			@Override
			boolean nameAccept(final String methodName) {
				return GETTER_INCLUDING_GET_CLASS.nameAccept(methodName) && !"getClass".equals(methodName);
			}
		}, //
		/**
		 * 包含所有setter方法：以set开头，并且随后跟着至少一个字符，并且以大写字母（Character.toUpperCase(charAfterSet) == charAfterSet）开始
		 */
		SETTER(BeanCons.SET) {
			@Override
			boolean nameAccept(final String methodName) {
				if (methodName.length() <= this.methodName.length()) {
					//这里包括纯粹叫set方法
					return false;
				}
				if (methodName.startsWith(this.methodName)) {
					final char charAfterSet = methodName.charAt(this.methodName.length() + 1);
					if (Character.toUpperCase(charAfterSet) == charAfterSet) {
						return true;
					}
				}
				return false;
			}

			@Override
			boolean parameterClassesCompare(final Method method) {
				final Class<?>[] methodParameterTypes = method.getParameterTypes();
				return methodParameterTypes.length == 1;
			}
		}, //
		ALL(null) {
			@Override
			public boolean accept(final Method method) {
				return true;
			}
		}, //
		;
		final String methodName;
		final Class<?>[] argsClasses;

		SomeMethodSelectors(final String methodName) {
			this(methodName, (Class<?>[]) null);
		}

		SomeMethodSelectors(final String methodName, final Class<?>... argsClasses) {
			this.methodName = methodName;
			this.argsClasses = argsClasses;
		}

		@Override
		public boolean accept(final Method method) {
			if (nameAccept(method.getName())) {
				return parameterClassesCompare(method);
			}
			return false;
		}

		boolean parameterClassesCompare(final Method method) {
			final Class<?>[] methodParameterTypes = method.getParameterTypes();
			return argsClasses == null && methodParameterTypes.length == 0//
					|| Arrays.equals(argsClasses, methodParameterTypes);
		}

		boolean nameAccept(final String methodName) {
			return this.methodName.equals(methodName);
		}
	}
}
