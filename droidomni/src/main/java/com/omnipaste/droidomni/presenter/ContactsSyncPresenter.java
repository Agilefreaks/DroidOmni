package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.domain.ContactSyncNotification;

public class ContactsSyncPresenter extends Presenter<ContactsSyncPresenter.View> {
  private final ContactSyncNotification contactSyncNotification;

  public interface View {
    public void setTitle(int resId);

    public void setStatus(int resId);

    public void setStatus(String content);
  }

  public ContactsSyncPresenter(ContactSyncNotification contactSyncNotification) {
    this.contactSyncNotification = contactSyncNotification;
  }

  @Override
  public void initialize() {
    int title;

    if (contactSyncNotification.getStatus() == ContactSyncNotification.Status.Started) {
      title = R.string.contact_sync_started;
      getView().setStatus(R.string.contact_sync_started_status);
    } else if (contactSyncNotification.getStatus() == ContactSyncNotification.Status.Completed) {
      title = R.string.contact_sync_completed;
      getView().setStatus(R.string.contact_sync_completed_status);
    } else {
      title = R.string.contact_sync_failed;
      getView().setStatus(contactSyncNotification.getReason().getMessage());
    }

    getView().setTitle(title);
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }

  @Override
  public void destroy() {
  }
}
