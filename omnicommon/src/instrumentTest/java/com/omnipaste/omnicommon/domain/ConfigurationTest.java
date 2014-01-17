package com.omnipaste.omnicommon.domain;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ConfigurationTest extends TestCase {
  private Configuration subject;

  public void testHasChannelReturnsTrueWhenChannelIsNotEmpty() throws Exception {
    subject = new Configuration();
    subject.channel = "test@test.com";

    assertThat(subject.hasChannel(), is(true));
  }

  public void testHasChannelReturnsFalseWhenChannelIsEmpty() throws Exception {
    subject = new Configuration();

    assertThat(subject.hasChannel(), is(false));
  }
}
