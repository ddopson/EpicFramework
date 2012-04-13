package com.epic.framework.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface EpicInflatableClass {
	boolean inflatable() default true;
	boolean ignoreSuperclass() default false;
	Class<?> arguments() default EpicInflatableClass.class;
}
