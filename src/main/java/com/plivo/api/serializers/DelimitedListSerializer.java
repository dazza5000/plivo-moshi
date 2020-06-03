package com.plivo.api.serializers;


import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonQualifier;
import com.squareup.moshi.ToJson;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.util.List;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class DelimitedListSerializer {

  private final String delimiter;

  public DelimitedListSerializer() {
    this("<");
  }

  DelimitedListSerializer(String delimiter) {
    this.delimiter = delimiter;
  }

  @FromJson
  @CustomDelimiter
  public String toJson(String incomingList) {
    return null;
  }

  @ToJson
  public String toJson(@CustomDelimiter List<String> value) {
    return String.join(delimiter, value);
  }
}

