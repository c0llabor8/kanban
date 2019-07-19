package com.c0llabor8.kanban;

import com.c0llabor8.kanban.model.Assignment;
import com.c0llabor8.kanban.model.Membership;
import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.model.Task;
import com.parse.Parse;
import com.parse.ParseObject;
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
    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    builder.networkInterceptors().add(httpLoggingInterceptor);

    // Enable parse's local storage of
    Parse.enableLocalDatastore(this);

    // Register our data models with Parse
    ParseObject.registerSubclass(Project.class);
    ParseObject.registerSubclass(Membership.class);
    ParseObject.registerSubclass(Assignment.class);
    ParseObject.registerSubclass(Task.class);

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
