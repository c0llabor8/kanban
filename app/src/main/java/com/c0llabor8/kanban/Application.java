package com.c0llabor8.kanban;

import com.parse.Parse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class Application extends android.app.Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Force parse to write to logcat at the debug level
    Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

    // Use OkHttp to monitor Parse traffic with a header level http
    // interceptor
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    HttpLoggingInterceptor httpLoggingInterceptor =
        new HttpLoggingInterceptor();
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    builder.networkInterceptors().add(httpLoggingInterceptor);

    // Initialize the Parse SDK at the app entry point
    Parse.initialize(
        new Parse.Configuration.Builder(this)
            .applicationId("c0llabor8")
            .clientBuilder(builder)
            .server("https://c0llabor8.herokuapp.com/parse")
            .build()
    );
  }
}
