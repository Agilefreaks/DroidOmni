package com.omnipasteapp.omnipaste.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
    @Test
    public void shouldPass() {
        assertThat(true, is(true));
    }
}