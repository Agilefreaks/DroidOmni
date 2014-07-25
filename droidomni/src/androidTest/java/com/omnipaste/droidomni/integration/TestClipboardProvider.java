package com.omnipaste.droidomni.integration;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.widget.ListView;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.activities.MainActivity_;
import com.omnipaste.droidomni.adapters.ClippingAdapter;
import com.omnipaste.droidomni.adapters.ClippingsPagerAdapter;
import com.omnipaste.omniapi.resources.v1.Clippings;
import com.omnipaste.omnicommon.dto.ClippingDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestClipboardProvider extends TestIntegration<MainActivity_> {
  public TestClipboardProvider() {
    super(MainActivity_.class);
  }

  @SuppressWarnings("ConstantConditions")
  public void testClipboardProviderShouldNotDuplicatedItems() {
    assertTrue("Should show a list view with clippings", solo.waitForView(R.id.clippingsPager));

    setTextInClipboard("some");
    solo.waitForText("some");

    setTextInClipboard("test");
    solo.waitForText("test");

    ClippingAdapter clippingAdapter = (ClippingAdapter) solo.getCurrentViews(ListView.class).get(0).getAdapter();

    assertThat(clippingAdapter.getItem(0).getContent(), is("test"));
    assertThat(clippingAdapter.getItem(1).getContent(), is("some"));
  }

  private void setTextInClipboard(String text) {
    ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
    clipboard.setPrimaryClip(ClipData.newPlainText("", text));
  }
}
