package com.goodmortician.notes.auth;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authenticate {
    @AliasFor("allowedRoles")
    String[] value() default {};
    @AliasFor("value")
    String[] allowedRoles() default {};
}
