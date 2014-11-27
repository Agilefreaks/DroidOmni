package com.omnipaste.droidomni.domain;

public abstract class Command<T> {
  private T item;
  private Action action;

  public enum Action {
    ADD,
    REMOVE
  }

  protected Command(T item, Action action) {
    this.item = item;
    this.action = action;
  }

  public T getItem() {
    return item;
  }

  public Action getAction() {
    return action;
  }
}
