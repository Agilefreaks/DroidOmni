package com.omnipasteapp.clipboardprovider.omniclipboard;

import com.omnipasteapp.omniapi.IOmniApi;
import com.omnipasteapp.omniapi.resources.IClippings;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OmniClipboardTest extends TestCase {
  @Mock
  private IOmniApi mockOmniApi;

  private OmniClipboard subject;

  protected void setUp() {
    MockitoAnnotations.initMocks(this);

    subject = new OmniClipboard(mockOmniApi);
}

  public void testInitializeReturnsNewThread() {
    Thread result = subject.initialize();

    assertNotNull(result);
  }

  public void testFetchClippingWillCallApi() {
    IClippings mockClippings = mock(IClippings.class);
    when(mockOmniApi.clippings()).thenReturn(mockClippings);

    subject.fetchClipping();

    verify(mockClippings).getLastAsync(subject);
  }
}
