package org.sliner.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Component:
 * Description:
 * Date: 14-6-17
 *
 * @author Andy Ai
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Criteria {
    boolean multiple() default false;
}
