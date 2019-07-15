Kanban
===

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
An intuitive application for managing team workflows and maximizing productivity.

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Productivity/Tools
- **Mobile:** The application will provide a convenient way to observe and manage individual tasks from anywhere
- **Story:** Regardless of what you're working on, a good project management application will ease the pains of coordinating tasks and communication within a team, allowing everyone to be more productive
- **Market:** Our market will include anyone who's work includes a lot of collaboration amongst a group. Due to it's focus on efficiency with team work this application has a broad market
- **Habit:** By integrating this app into team workflow, it would be very easy to have a recurring userbase. Ultimately we want people to rely on this app in order to functionally do work
- **Scope:** Getting the MVP (Minimal Viable Product) completed will not be too difficult, but getting the application to be a full fledged platform for collaboration will be a difficult challenge

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User authentication: As a user, I want to sign in or sign up with email/username and password because I want an account to collaborate with my team.
* Task creation: As a user, I want to be able to create tasks in order to track my work.
* Task overview: As a user, task overview allows me to get an idea of the tasks I am assigned.
* Project creation: As a user, project creation is important so that my team can keep track of progress and tasks.
* Project list: As a user, I want to see my list of projects I am involved in because I want to keep track of the various projects I am working on.
* Project invitation: As a user, I would like to invite team members so we can collaborate and keep track of progress.
* Project overview/task list view: As a user, I want to be able to see the list of tasks in a specific project because it's important to know the progress of my team.
* Member list view: As a user, I want to see the members in my project to know who I am working with.
* Member ranking: As a user, I have the option to view a ranked list of members within my project based on task completion because it motivates team members.
* Project/Individual timeline: As a user, I want to visualize my upcoming deadlines because it will keep me on track.
* Individual/project status/health screen: As a user, I can see a graphical representation of the overall state of the projects I am involved in because this will motivate the team to get tasks done.
* Task tags/flags: As a user, visual representation of importance of task priority helps me prioritize my tasks.
* Task conversations: As a user, communication within context will help reduce confusion and misunderstandings.

**Optional Nice-to-have Stories**

* Push notifications: As a user, I want instant notification of activities within my projects to get reminders for my tasks.
* Confetti animation upon completion: As a user, I will see confetti when I complete a task because positive reinforcement is good.
* Application onboarding: As a user, I want to get an introduction of the app because it is difficult to figure out all the features by yourself.

### 2. Screen Archetypes

* Authentication Screen
   * Username & Password
   * Registration pop-up
* Home Screen
   * Individual/Project task list (Fragment)
   * Individual/Project task timeline (Fragment)
   * Individual/Project task health (Fragment)
* Member Screen
   * View current users
   * Invite users
   * Remove users
   * Member ranking
* Profile Screen
   * Display user info
   * Profile picture
   * Completed task (graveyard)
   * Logout





### 3. Navigation

**Drawer Navigation**
   * Individual tasks
   * Individual timeline
   **Project List**

**Tab Navigation** (Tab to Screen)
* Task list
* Timeline
* Progress

**Flow Navigation** (Screen to Screen)

* Login Screen
   * Login button to MainActivity
   * Signup bottom anchored button
* Main Activity
   * BottomAppBar bottom sheet to switch project scope
   * Tab navigation

## Wireframes
<img src="https://raw.githubusercontent.com/c0llabor8/kanban/master/wireframes/screen_01.png" width=600>
<img src="https://raw.githubusercontent.com/c0llabor8/kanban/master/wireframes/screen_02.png" width=600>
<img src="https://raw.githubusercontent.com/c0llabor8/kanban/master/wireframes/screen_03.png" width=600>
<img src="https://raw.githubusercontent.com/c0llabor8/kanban/master/wireframes/screen_04.png" width=600>
<img src="https://raw.githubusercontent.com/c0llabor8/kanban/master/wireframes/screen_05.png" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema
<img src="https://raw.githubusercontent.com/c0llabor8/kanban/master/schema.png" width=600>

### Models
# Use joint  relationship table

User
------
email string
full_name string
username string
password string
profile_image string

Project
------
name string
tasks int
members int
deadline date

Membership
-----
user fk - user
project fk - project

Task
------
title string
description string
project fk - project
created_at date
estimate date
priority int

Assignment
------
user fk - user
task fk - task

Message
------
user fk - user
project fk - project
body string
created_at date
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]

## Notes
Horizontal member list at top of project dashboard
