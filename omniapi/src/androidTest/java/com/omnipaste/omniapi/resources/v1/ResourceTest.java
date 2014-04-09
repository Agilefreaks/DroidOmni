package com.omnipaste.omniapi.resources.v1;

import com.omnipaste.omnicommon.dto.AccessTokenDto;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ResourceTest extends TestCase {
  public class ResourceImpl extends Resource {
    protected ResourceImpl(String baseUrl) {
      super(baseUrl);
    }
  }

  private ResourceImpl subject;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    subject = new ResourceImpl("http://some_url.com");
  }

  public void testBearerTokenWillPrefixWithBearer() throws Exception {
    assertThat(subject.bearerToken(new AccessTokenDto("access", "refresh")), is("bearer access"));
  }
}
