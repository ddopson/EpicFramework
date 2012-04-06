package com.epic.framework.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
public @interface EpicFieldInflation {
	boolean ignore() default false;
}
