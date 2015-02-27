package com.omnipaste.omnicommon.dto;

import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class NumberDtoTest {
  @Test
  public void equalsWithTheSameNumberAndTypeWillReturnTrue() {
    NumberDto numberDto1 = new NumberDto("123", "work");
    NumberDto numberDto2 = new NumberDto("123", "work");

    assertThat(numberDto1, equalTo(numberDto2));
  }

  @Test
  public void equalsWithDifferentNumberWillReturnFalse() {
    NumberDto numberDto1 = new NumberDto("321", "work");
    NumberDto numberDto2 = new NumberDto("123", "work");

    assertThat(numberDto1, not(equalTo(numberDto2)));
  }

  @Test
  public void equalsWithDifferentTypeWillReturnFalse() {
    NumberDto numberDto1 = new NumberDto("123", "home");
    NumberDto numberDto2 = new NumberDto("123", "work");

    assertThat(numberDto1, not(equalTo(numberDto2)));
  }
}