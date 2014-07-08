package com.omnipaste.droidomni.fragments;

import com.omnipaste.droidomni.services.DeviceService;
import com.omnipaste.droidomni.services.DeviceServiceImpl;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;


public class DeviceInitFragmentTest extends TestCase {
  private DeviceInitFragment subject;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    subject = new DeviceInitFragment();
  }

  public void testafterViewsWillTODO() throws Exception {
  }

  public void testGetDeviceServiceWhenNoDeviceServiceWasSetWillReturnANewDeviceServiceImpl() throws Exception {
    assertThat(subject.getDeviceService(), instanceOf(DeviceServiceImpl.class));
  }

  public void testGetDeviceServiceWhenSetWillReturnTheSetValue() throws Exception {
    DeviceService deviceService = new DeviceServiceImpl();
    subject.setDeviceService(deviceService);

    assertThat(subject.getDeviceService(), is(deviceService));
  }
}