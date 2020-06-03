package com.plivo.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.plivo.api.models.message.MessageCreator;
import com.plivo.api.serializers.CustomDelimiter;
import com.plivo.api.serializers.DelimitedListSerializer;
import com.plivo.api.util.Utils;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.util.List;

public class PlivoClient {

  private static SimpleModule simpleModule = new SimpleModule();
  protected static String BASE_URL = "https://api.plivo.com/v1/";
  protected static String CALLINSIGHTS_BASE_URL = "https://stats.plivo.com/v1/";
  private static String version = "Unknown Version";
  private boolean testing = false;
  private static ObjectMapper objectMapper = new ObjectMapper();

  public void setTesting(boolean testing) {
    this.testing = testing;
  }

  public boolean isTesting() {
    return testing;
  }


  {
    try {
      InputStream inputStream = PlivoClient.class
        .getResource("version.txt")
        .openStream();
      version = new BufferedReader(new InputStreamReader(inputStream)).readLine();
    } catch (IOException ignored) {
      ignored.printStackTrace();
    }

  }

  private final Interceptor interceptor = new HttpLoggingInterceptor()
    .setLevel(Level.BODY);
  private final String authId;
  private final String authToken;
  private OkHttpClient httpClient;
  private Retrofit retrofit;
  private Retrofit callInsightsRetrofit;
  private PlivoAPIService apiService = null;
  private CallInsightsAPIService callInsightsAPIService = null;

  /**
   * Constructs a new PlivoClient instance. To set a proxy, timeout etc, you can pass in an OkHttpClient.Builder, on which you can set
   * the timeout and proxy using:
   *
   * <pre><code>
   *   new OkHttpClient.Builder()
   *   .proxy(proxy)
   *   .connectTimeout(1, TimeUnit.MINUTES);
   * </code></pre>
   *  @param authId
   * @param authToken
   * @param httpClientBuilder
   * @param baseUrl
   */
  public PlivoClient(String authId, String authToken, OkHttpClient.Builder httpClientBuilder, final String baseUrl) {
    if (!(Utils.isAccountIdValid(authId) || Utils.isSubaccountIdValid(authId))) {
      throw new IllegalArgumentException("invalid account ID");
    }

    this.authId = authId;
    this.authToken = authToken;

    httpClient = httpClientBuilder
      .addNetworkInterceptor(interceptor)
      .addInterceptor(chain -> chain.proceed(
        chain.request()
          .newBuilder()
          .addHeader("Authorization", Credentials.basic(getAuthId(), getAuthToken()))
          .addHeader("User-Agent", String.format("%s/%s (Implementation: %s %s %s, Specification: %s %s %s)", "plivo-java", version,
            Runtime.class.getPackage().getImplementationVendor(),
            Runtime.class.getPackage().getImplementationTitle(),
            Runtime.class.getPackage().getImplementationVersion(),
            Runtime.class.getPackage().getSpecificationVendor(),
            Runtime.class.getPackage().getSpecificationTitle(),
            Runtime.class.getPackage().getSpecificationVersion()
          ))
          .build()
      ))
      .addNetworkInterceptor(chain -> {
        Response response;
        try {
          response = chain.proceed(chain.request());
        } catch (ProtocolException protocolException) {
          // We return bodies for HTTP 204!
          response = new Response.Builder()
            .request(chain.request())
            .code(204)
            .protocol(Protocol.HTTP_1_1)
            .body(ResponseBody.create(null, new byte[]{}))
            .build();
        }
        return response;
      }).build();


    Moshi moshi = new Moshi.Builder()
      .build();

    JsonAdapter<MessageCreator> messageCreatorJsonAdapter = moshi.adapter(MessageCreator.class);

    retrofit = new Retrofit.Builder()
      .client(httpClient)
      .baseUrl(baseUrl)
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .build();

    this.apiService = retrofit.create(PlivoAPIService.class);

    callInsightsRetrofit = new Retrofit.Builder()
      .client(httpClient)
      .baseUrl((CALLINSIGHTS_BASE_URL))
      .addConverterFactory(MoshiConverterFactory.create(new Moshi.Builder().build()))
      .build();

    this.callInsightsAPIService = callInsightsRetrofit.create(CallInsightsAPIService.class);
  }

  /**
   * Constructs a new PlivoClient instance. To set a proxy, timeout etc, you can pass in an OkHttpClient.Builder, on which you can set
   * the timeout and proxy using:
   *
   * <pre><code>
   *   new OkHttpClient.Builder()
   *   .proxy(proxy)
   *   .connectTimeout(1, TimeUnit.MINUTES);
   * </code></pre>
   *
   * @param authId
   * @param authToken
   */
  public PlivoClient(String authId, String authToken) {
    this(authId, authToken, new OkHttpClient.Builder(), BASE_URL);
  }

  /**
   * Constructs a new PlivoClient instance. To set a proxy, timeout etc, you can pass in an OkHttpClient.Builder, on which you can set
   * the timeout and proxy using:
   *
   * <pre><code>
   *   new OkHttpClient.Builder()
   *   .proxy(proxy)
   *   .connectTimeout(1, TimeUnit.MINUTES);
   * </code></pre>
   *
   * @param authId
   * @param authToken
   * @param httpClientBuilder
   */
  public PlivoClient(String authId, String authToken, OkHttpClient.Builder httpClientBuilder) {
    this(authId, authToken, httpClientBuilder, BASE_URL);
  }

  public static ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  Retrofit getRetrofit() {
    return retrofit;
  }

  public PlivoAPIService getApiService() {
    return apiService;
  }

  public CallInsightsAPIService getCallInsightsAPIService() {
    return callInsightsAPIService;
  }

  void setApiService(PlivoAPIService apiService) {
    this.apiService = apiService;
  }

  public String getAuthId() {
    return authId;
  }

  public String getAuthToken() {
    return authToken;
  }
}
