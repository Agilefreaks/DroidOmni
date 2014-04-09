package com.omnipaste.omnicommon.domain;

import com.omnipaste.omnicommon.dto.AccessTokenDto;

import junit.framework.TestCase;

public class ConfigurationTest extends TestCase {
  private Configuration subject;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    subject = new Configuration();
  }

  public void testHasAccessTokenWhenAccessTokeNullReturnsFalse() throws Exception {
    subject.setAccessToken(null);

    assertFalse(subject.hasAccessToken());
  }

  public void testHasAccessTokenWhenAccessTokeNotNullReturnsTrue() throws Exception {
    subject.setAccessToken(new AccessTokenDto());

    assertTrue(subject.hasAccessToken());
  }
}
