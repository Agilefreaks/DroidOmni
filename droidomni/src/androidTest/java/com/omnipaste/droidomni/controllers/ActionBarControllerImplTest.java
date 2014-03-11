package com.omnipaste.droidomni.controllers;

import android.support.v7.app.ActionBar;

import com.omnipaste.droidomni.activities.OmniActivity;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

public class ActionBarControllerImplTest extends TestCase {
  ActionBarControllerImpl controller;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    controller = new ActionBarControllerImpl();
  }

  public void testGetActionBarWillMemoize() throws Exception {
    assertThat(controller.getActionBar(), is(controller.getActionBar()));
  }

  public void testGetActionBarWillNotMemoizeAfterRun() throws Exception {
    ActionBar actionBar = controller.getActionBar();

    controller.run(new OmniActivity());

    assertThat(controller.getActionBar(), not(actionBar));
  }
}
