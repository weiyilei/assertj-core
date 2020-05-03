/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2020 the original author or authors.
 */
package org.assertj.core.api.recursive.comparison;

import static java.lang.Integer.toHexString;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.RecursiveComparisonAssert_isEqualTo_BaseTest;
import org.junit.jupiter.api.Test;

public class RecursiveComparisonAssert_isEqualTo_debugTest extends RecursiveComparisonAssert_isEqualTo_BaseTest {

  @Test
  public void should_be_able_to_compare_objects_with_cycles_in_ordered_and_unordered_collection() {
    // GIVEN
    FriendlyPerson actual = new FriendlyPerson();

    FriendlyPerson expected = new FriendlyPerson();

    // friends cycle with intermediate collection
    FriendlyPerson sherlock = new FriendlyPerson();

    // unordered collections
    // this could cause an infinite recursion if we don't track correctly the visited objects
    actual.otherFriends.add(actual);
    actual.otherFriends.add(expected);
    actual.otherFriends.add(sherlock);
    expected.otherFriends.add(sherlock);
    expected.otherFriends.add(expected);
    expected.otherFriends.add(actual);

    // THEN
    assertThat(actual).usingRecursiveComparison()
                      .isEqualTo(expected);
  }

  static class FriendlyPerson extends Person {
    public Set<FriendlyPerson> otherFriends = new HashSet<>();

    public FriendlyPerson() {
      super();
    }

    @Override
    public String toString() {
      return "FriendlyPerson@" + id(this);
    }
  }

  static class Person {
    public Person() {}

    @Override
    public String toString() {
      return "Person@" + id(this);
    }

  }

  private static String id(Object obj) {
    return toHexString(System.identityHashCode(obj));
  }
}
