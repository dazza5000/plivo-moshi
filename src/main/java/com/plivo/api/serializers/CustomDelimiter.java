package com.plivo.api.serializers;

import com.squareup.moshi.JsonQualifier;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@JsonQualifier
public @interface CustomDelimiter {
}
