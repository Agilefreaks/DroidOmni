package com.omnipasteapp.omnicommon.test.settings;

import com.omnipasteapp.omnicommon.settings.CommunicationSettings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class CommunicationSettingsTest {
  private CommunicationSettings subject;

  @Before
  public void Setup() {
    subject = new CommunicationSettings("42");
  }

  @Test
  public void getChannelShouldReturnTheChannelName() {
    assertThat(subject.getChannel(), is("42"));
  }

  @Test
  public void hasChannelWhenChannelIsNullReturnsFalse(){
    subject.setChannel(null);

    assertFalse(subject.hasChannel());
  }

  @Test
  public void hasChannelWhenChannelIsEmptyReturnsFalse(){
    subject.setChannel("");

    assertFalse(subject.hasChannel());
  }

  @Test
  public void hasChannelWhenChannelIsNotEmptyReturnsTrue(){
    subject.setChannel("42");

    assertTrue(subject.hasChannel());
  }
}
