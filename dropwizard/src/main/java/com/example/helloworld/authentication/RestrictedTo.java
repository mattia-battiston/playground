package com.example.helloworld.authentication;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface RestrictedTo {

    UserRole[] value() default UserRole.ADMIN;

}
