package com.c0llabor8.kanban.model.query;

import static com.c0llabor8.kanban.model.Membership.KEY_PROJECT;
import static com.c0llabor8.kanban.model.Membership.KEY_USER;

import com.c0llabor8.kanban.model.Membership;
import com.c0llabor8.kanban.model.Project;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class MembershipQuery extends ParseQuery<Membership> {

  public MembershipQuery() {
    super(Membership.class);
    include(KEY_USER);
    include(KEY_PROJECT);
  }

  public MembershipQuery whereUserEquals(ParseUser user) {
    whereEqualTo(KEY_USER, user);
    return this;
  }

  public MembershipQuery whereProjectEquals(Project project) {
    whereEqualTo(KEY_PROJECT, project);
    return this;
  }
}
