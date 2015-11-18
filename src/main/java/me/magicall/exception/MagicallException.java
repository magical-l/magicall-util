package me.magicall.exception;

import com.google.common.collect.Sets;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Collections;

/**
 * 异常框架的根异常。所有业务异常都应该继承自本类。
 *
 * @author Liang Wenjian
 */
public abstract class MagicallException extends RuntimeException {
	/**
	 * 下一步可以重试
	 */
	public static final Collection<String> NEXT_STEP_IDS_OF_RETRY = Collections.singleton("self");
	/**
	 * 下一步可以报告问题
	 */
	public static final Collection<String> NEXT_STEP_IDS_OF_REPORT_PROBLEM = Collections.singleton("reportProblem");
	/**
	 * 下一步可以重试或报告问题
	 */
	public static final Collection<String> NEXT_STEP_IDS_OF_RETRY_OR_REPORT_BUG = Sets
			.newHashSet("self", "reportProblem");
	/**
	 * 下一步可以去平台授权
	 */
	public static final Collection<String> NEXT_STEP_IDS_OF_PLAT_AUTHORIZE = Collections.singleton("platAuthorize");

	private static final long serialVersionUID = 4904242843495439482L;

	/**
	 * 攀爬异常藤，寻找是否有MagicallException异常节点。
	 *
	 * @param throwable
	 * @return
	 */
	public static MagicallException findMagicallException(final Throwable throwable) {
		return ExceptionUtil.findCause(throwable, MagicallException.class, true);
	}

	//----------------------------------------------------

	private final String id;
	private final Collection<String> nextStepIds;

	//----------------------------------------------------

	public MagicallException(final String message) {
		super(message);
		id = buildId();
		nextStepIds = NEXT_STEP_IDS_OF_REPORT_PROBLEM;
	}

	public MagicallException(final String message, final Collection<String> nextStepIds) {
		super(message);
		id = buildId();
		this.nextStepIds = Collections.unmodifiableCollection(nextStepIds);
	}

	public MagicallException(final String message, final Throwable cause) {
		super(message, cause);
		if (cause instanceof MagicallException) {
			final MagicallException magicallException = (MagicallException) cause;
			id = magicallException.getId();
		} else {
			id = buildId();
		}
		nextStepIds = Collections.emptyList();
	}

	//----------------------------------------------------

	private static String buildId() {
		final Thread thread = Thread.currentThread();
		try {
			final InetAddress inetAddress = InetAddress.getLocalHost();
			return thread.getId() + "_" + System.currentTimeMillis() + '_' + inetAddress.getHostAddress();
		} catch (final UnknownHostException ignored) {
//			logger.warn("不应该发生", e);
			return thread.getId() + "_" + System.currentTimeMillis();
		}
	}

	public String getId() {
		return id;
	}

	public MagicallException(final String message, final Throwable cause, final Collection<String> nextStepIds) {
		super(message, cause);
		if (cause instanceof MagicallException) {
			final MagicallException magicallException = (MagicallException) cause;
			id = magicallException.getId();
		} else {
			id = buildId();
		}
		this.nextStepIds = Collections.unmodifiableCollection(nextStepIds);
	}

	public Collection<String> getNextStepIds() {
		return nextStepIds;
	}
}
