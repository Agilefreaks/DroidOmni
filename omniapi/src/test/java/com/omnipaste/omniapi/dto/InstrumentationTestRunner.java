package com.omnipaste.omniapi.dto;

import android.os.Bundle;

@SuppressWarnings({"UnusedDeclaration", "Used in gradle.properties as the testInstrumentationRunner"})
public class InstrumentationTestRunner extends android.test.InstrumentationTestRunner {
  @Override
  public void onCreate(Bundle arguments) {
    super.onCreate(arguments);
    System.setProperty("dexmaker.dexcache", getTargetContext().getCacheDir().toString());
  }
}
