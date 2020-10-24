package com.carnetwork.hansen.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * author：Sun on 2017/8/25/0025.
 * email：1564063766@qq.com
 * remark:
 */
@Scope
@Retention(RUNTIME)
public @interface ActivityScope {
}
