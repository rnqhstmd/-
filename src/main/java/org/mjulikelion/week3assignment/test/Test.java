package org.mjulikelion.week3assignment.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 이 어노테이션이 어떤 곳에서 사용될 것인지
@Retention(RetentionPolicy.RUNTIME) // 이 어노테이션이 언제까지 유지될 수 있는지
public @interface Test {
}
