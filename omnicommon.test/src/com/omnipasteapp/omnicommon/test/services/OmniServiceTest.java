package com.omnipasteapp.omnicommon.test.services;

import com.omnipasteapp.omnicommon.interfaces.ILocalClipboard;
import com.omnipasteapp.omnicommon.interfaces.IOmniClipboard;
import com.omnipasteapp.omnicommon.services.OmniService;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class OmniServiceTest {
  private OmniService subject;
  private ILocalClipboard localClipboard;
  private IOmniClipboard omniClipboard;

  @Before
  public void Setup() {
    localClipboard = mock(ILocalClipboard.class);
    omniClipboard = mock(IOmniClipboard.class);
    subject = new OmniService(localClipboard, omniClipboard);
  }

  @Test
  public void someTest() {
    assertThat(subject.getLocalClipboard(), is(ILocalClipboard.class));
  }
}
