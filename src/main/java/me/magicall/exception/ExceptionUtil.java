package me.magicall.exception;

import java.util.Objects;

/**
 * @author Liang Wenjian
 */
public class ExceptionUtil {

    public static Throwable findRoot(final Throwable throwable) {
        for (Throwable t = throwable; t != null; ) {
            final Throwable cause = t.getCause();
            if (cause == null) {
                return t;
            }
            t = cause;
        }
        return null;
    }

    /**
     * 攀爬异常藤，在cause中寻找指定类型的异常。
     *
     * @param throwable
     * @param exceptionClass
     * @param includingSubclasses 若为true，表示寻找指定异常类型的异常或其子孙类型的异常；若为false，表示寻找指定异常类型的异常。
     * @param <T>
     * @return
     */
    public static <T extends Throwable> T findCause(final Throwable throwable, final Class<T> exceptionClass,
                                                    final boolean includingSubclasses) {
        for (Throwable t = throwable; t != null; ) {
            if (includingSubclasses) {
                if (exceptionClass.isAssignableFrom(t.getClass())) {
                    return exceptionClass.cast(t);
                }
            } else {
                if (t.getClass().equals(exceptionClass)) {
                    return exceptionClass.cast(t);
                }
            }
            final Throwable cause = t.getCause();
            if (Objects.equals(cause, t)) {
                break;
            }
            t = cause;
        }
        return null;
    }

    /**
     * 如果throwable的cause stack中有exceptionClass及其子类异常，则返回改异常，否则返回null。
     *
     * @param throwable
     * @param exceptionClass
     * @param <T>
     * @return
     */
    public static <T extends Throwable> T findCause(final Throwable throwable, final Class<T> exceptionClass) {
        return findCause(throwable, exceptionClass, true);
    }

    private ExceptionUtil() {
        super();
    }
}
