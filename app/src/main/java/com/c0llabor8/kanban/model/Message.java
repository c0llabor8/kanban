package com.c0llabor8.kanban.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.Date;

@ParseClassName("Message")
public class Message extends ParseObject {

  public static final String KEY_USER = "user";
  public static final String KEY_PROJECT = "project";
  public static final String KEY_BODY = "body";

  public ParseUser getUser() {
    return getParseUser(KEY_USER);
  }

  public void setUser(ParseUser user) {
    put(KEY_USER, user);
  }

  public ParseObject getProject() {
    return getParseObject(KEY_PROJECT);
  }

  public void setProject(ParseObject project) {
    put(KEY_PROJECT, project);
  }

  public String getBody() {
    return getString(KEY_BODY);
  }

  public void setBody(String body) {
    put(KEY_BODY, body);
  }

  public Date getCreatedAt() {
    return getCreatedAt();
  }


}
