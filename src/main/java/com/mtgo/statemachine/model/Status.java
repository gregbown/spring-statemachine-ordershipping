package com.mtgo.statemachine.model;

public class Status {
  private final long id;
  private final String content;

  public Status(long id, String content) {
    this.id = id;
    this.content = content;
  }

  public long getId() {
    return id;
  }

  public String getContent() {
    return content;
  }
}
