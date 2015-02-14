package com.omnipaste.droidomni.presenter;

import org.junit.Before;
import org.junit.Test;

public class ContactsPresenterTest {
  private ContactsPresenter contactsPresenter;

  @Before
  public void context() {
    contactsPresenter = new ContactsPresenter();
  }

  @Test
  public void initializeWhenContactsSyncedWillNotContactsStartSync() {
    contactsPresenter.initialize();
  }
}