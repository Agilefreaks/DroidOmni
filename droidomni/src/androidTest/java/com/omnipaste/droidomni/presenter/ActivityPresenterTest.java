package com.omnipaste.droidomni.presenter;

import android.test.InstrumentationTestCase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ActivityPresenterTest extends InstrumentationTestCase {
  private ActivityPresenter subject;
  private ClippingPresenter mockClippingPresenter;

  public void setUp() throws Exception {
    super.setUp();

    mockClippingPresenter = mock(ClippingPresenter.class);
    subject = new ActivityPresenter(mockClippingPresenter);
    subject.attachView(mock(ActivityPresenter.View.class));
  }

  public void testRefreshWillCallRefresOnClippings() throws Exception {
    subject.refresh();

    verify(mockClippingPresenter).refresh();
  }
}