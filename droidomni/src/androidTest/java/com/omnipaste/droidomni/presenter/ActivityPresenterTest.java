package com.omnipaste.droidomni.presenter;

import android.test.InstrumentationTestCase;

import com.omnipaste.droidomni.interaction.Refresh;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ActivityPresenterTest extends InstrumentationTestCase {
  private ActivityPresenter subject;
  private Refresh mockRefresh;

  public void setUp() throws Exception {
    super.setUp();

    mockRefresh = mock(Refresh.class);
    subject = new ActivityPresenter(
      mock(ClippingsPresenter.class),
      mock(SmsMessagesPresenter.class),
      mock(PhoneCallsPresenter.class),
      mock(ContactsPresenter.class),
      mockRefresh);
    subject.attachView(mock(ActivityPresenter.View.class));
  }

  public void testRefreshWillCallRefreshOnClippings() throws Exception {
    subject.refresh();

    verify(mockRefresh).all();
  }
}