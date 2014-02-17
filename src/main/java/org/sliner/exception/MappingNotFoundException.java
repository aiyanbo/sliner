package org.sliner.exception;

/**
 * Component:
 * Description:
 * Date: 14-2-17
 *
 * @author Andy Ai
 */
public class MappingNotFoundException extends RuntimeException {
    public MappingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
