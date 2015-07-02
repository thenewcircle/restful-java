package com.example.chirp.app.providers;

import java.lang.annotation.*;

import javax.ws.rs.NameBinding;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(value = RetentionPolicy.RUNTIME)
@NameBinding
public @interface DeprecatedRequest {}
