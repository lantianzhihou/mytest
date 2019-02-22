package com.wangbo.test.annotation;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

import org.springframework.core.annotation.Order;

/**
 * Comparator for classes annotated with {@link Order}.
 *
 * @since 2.1
 */
public class OrderComparator implements Comparator<Class<?>>, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Comparator<Class<?>> INSTANCE = new OrderComparator();

	/**
	 * Returns a singleton instance of this class.
	 *
	 * @return the singleton for this class.
	 */
	public static Comparator<Class<?>> getInstance() {
		return INSTANCE;
	}

	@Override
	public int compare(final Class<?> lhs, final Class<?> rhs) {
		final Order lhsOrder = Objects.requireNonNull(lhs, "lhs").getAnnotation(Order.class);
		final Order rhsOrder = Objects.requireNonNull(rhs, "rhs").getAnnotation(Order.class);
		if (lhsOrder == null && rhsOrder == null) {
			// both unannotated means equal priority
			return 0;
		}
		// if only one class is @Order-annotated, then prefer that one
		if (rhsOrder == null) {
			return -1;
		}
		if (lhsOrder == null) {
			return 1;
		}
		// larger value means lower priority
		return Integer.signum(rhsOrder.value() - lhsOrder.value());
	}
}
