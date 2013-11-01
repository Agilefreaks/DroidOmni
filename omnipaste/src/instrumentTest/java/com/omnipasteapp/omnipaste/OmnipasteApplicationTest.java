package com.omnipasteapp.omnipaste;

import android.test.ApplicationTestCase;

import java.lang.reflect.InvocationTargetException;

public class OmnipasteApplicationTest extends ApplicationTestCase<OmnipasteApplication> {
  public OmnipasteApplicationTest() {
    super(OmnipasteApplication.class);
  }

  public void testOnCreateWillLoadAsyncTask() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    createApplication();

    java.lang.reflect.Method m = ClassLoader.class.getDeclaredMethod("findLoadedClass", new Class[] { String.class });
    m.setAccessible(true);

    ClassLoader cl = getApplication().getClassLoader();
    Object result = m.invoke(cl, "android.os.AsyncTask");

    assertNotNull(result);
  }
}
