package com.example.gesang.emotion.injection.scope


import java.lang.annotation.Documented
import java.lang.annotation.Retention
import javax.inject.Scope
import java.lang.annotation.RetentionPolicy.RUNTIME

@Scope
@Documented
@Retention(RUNTIME)
annotation class PerComponentScope()