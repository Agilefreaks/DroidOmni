package com.omnipaste.droidomni.factories;

import com.omnipaste.droidomni.fragments.LoginFragment_;
import com.omnipaste.droidomni.fragments.MainFragment_;
import com.omnipaste.omnicommon.domain.Configuration;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

public class FragmentFactoryTest extends TestCase {
  @Override
  public void setUp() throws Exception {
    super.setUp();
  }

  public void testCreateWhenConfigurationHasChannelWillReturnInstanceOfMainFragment() throws Exception {
    Configuration configuration = new Configuration();
    configuration.setChannel("calin@test.com");
    assertThat(FragmentFactory.create_from(configuration), instanceOf(MainFragment_.class));
  }

  public void testCreateWhenConfigurationHasNoChannelWillReturnInstanceOfLoginFragment() throws Exception {
    Configuration configuration = new Configuration();
    assertThat(FragmentFactory.create_from(configuration), instanceOf(LoginFragment_.class));
  }
}
