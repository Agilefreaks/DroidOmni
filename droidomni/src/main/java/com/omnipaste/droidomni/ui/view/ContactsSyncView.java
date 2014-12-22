package com.omnipaste.droidomni.ui.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.domain.ContactSyncNotification;
import com.omnipaste.droidomni.presenter.ContactsSyncPresenter;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.view_contacts_sync)
public class ContactsSyncView extends LinearLayout implements HasSetup<ContactSyncNotification>, ContactsSyncPresenter.View {

  @ViewById
  public TextView contactsSyncTitle;

  @ViewById
  public TextView contactsSyncStatus;

  public ContactsSyncView(Context context) {
    super(context);
  }

  @Override
  public void setUp(ContactSyncNotification item) {
    ContactsSyncPresenter contactsSyncPresenter = new ContactsSyncPresenter(item);
    contactsSyncPresenter.attachView(this);
    contactsSyncPresenter.initialize();
  }

  @Override
  public void setTitle(int resId) {
    contactsSyncTitle.setText(resId);
  }

  @Override
  public void setStatus(int resId) {
    contactsSyncStatus.setText(resId);
  }

  @Override
  public void setStatus(String content) {
    contactsSyncStatus.setText(content);
  }
}
