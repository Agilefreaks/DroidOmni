package com.omnipaste.droidomni.integration;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.ListView;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.activities.MainActivity_;
import com.omnipaste.omnicommon.dto.ClippingDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestClipboardProvider extends TestIntegration<MainActivity_> {
  public TestClipboardProvider() {
    super(MainActivity_.class);
  }

  @SuppressWarnings("ConstantConditions")
  public void testClipboardProviderShouldNotDuplicatedItems() {
    TestHelper.signIn(solo);
    assertTrue("Should show a list view with clippings", solo.waitForView(R.id.clippings));

    setTextInClipboard("some");
    setTextInClipboard("test");

    solo.waitForText("test");
    solo.waitForText("some");

    ListView view = (ListView) solo.getView(R.id.clippings);
    assertThat(((ClippingDto) view.getItemAtPosition(0)).getContent(), is("test"));
    assertThat(((ClippingDto) view.getItemAtPosition(1)).getContent(), is("some"));
  }

  private void setTextInClipboard(String text) {
    ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
    clipboard.setPrimaryClip(ClipData.newPlainText("", text));
  }
}
