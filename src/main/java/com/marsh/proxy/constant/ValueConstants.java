package com.marsh.proxy.constant;

/**
 * Common value constants shared between bind annotations.
 */
public interface ValueConstants {

    /**
     * Constant defining a value for no default - as a replacement for
     * 	 * {@code null} which we cannot use in annotation attributes.
     * 	 * <p>This is an artificial arrangement of 16 unicode characters,
     * 	 * with its sole purpose being to never match user-declared values.
     */
    String DEFAULT_NONE = "\n\t\t\n\t\t\n\ue000\ue001\ue002\n\t\t\t\t\n";
}
