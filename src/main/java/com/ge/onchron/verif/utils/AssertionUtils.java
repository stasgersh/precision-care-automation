package com.ge.onchron.verif.utils;

import lombok.experimental.UtilityClass;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@UtilityClass
public final class AssertionUtils {

    /**
     * Asserts that two lists contain exactly the same elements in the same order.
     * <p>
     * Fails the test if {@code expectedOrder} and {@code actualOrder} are not equal
     * according to {@link java.util.List#equals(Object)}.
     *
     * @param message       the assertion message to show if the lists differ
     * @param expectedOrder the expected sequence of elements
     * @param actualOrder   the actual sequence of elements to verify
     * @param <T>           the element type of the lists
     */

    public static <T> void assertExactOrder(final String message,
                                            final List<T> expectedOrder,
                                            final List<T> actualOrder) {
        assertThat(message, expectedOrder.equals(actualOrder));
    }
}

