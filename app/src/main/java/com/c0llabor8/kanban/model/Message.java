package com.c0llabor8.kanban.model;

import android.text.format.DateUtils;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@ParseClassName("Message")
public class Message extends ParseObject {

  public static final String KEY_USER = "user";
  public static final String KEY_TASK = "task";
  public static final String KEY_BODY = "body";
  public static final String KEY_CREATED_AT = "createdAt";

  public ParseUser getUser() {
    return getParseUser(KEY_USER);
  }

  public void setUser(ParseUser user) {
    put(KEY_USER, user);
  }

  public ParseObject getTask() {
    return getParseObject(KEY_TASK);
  }

  public void setTask(ParseObject task) {
    put(KEY_TASK, task);
  }

  public String getBody() {
    return getString(KEY_BODY);
  }

  public void setBody(String body) {
    put(KEY_BODY, body);
  }


  public String getRelativeTime() {
    return getCreatedAt().toString();
  }


}
