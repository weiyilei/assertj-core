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
 * Copyright 2012-2021 the original author or authors.
 */
package org.assertj.core.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchReflectiveOperationException;
import static org.assertj.core.api.Assertions_catchThrowable_Test.codeThrowing;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.util.AssertionsUtil.expectAssertionError;
import static org.mockito.Mockito.mock;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;

class Assertions_catchReflectiveOperationException_Test {

  @Test
  void catchReflectiveOperationException_should_fail_with_good_message_if_wrong_type() {
    // GIVEN
    ThrowingCallable code = () -> catchReflectiveOperationException(raisingException("boom!!"));
    // WHEN
    AssertionError assertionError = expectAssertionError(code);
    // THEN
    assertThat(assertionError).hasMessageContainingAll(ReflectiveOperationException.class.getName(), Exception.class.getName());
  }

  @Test
  void catchReflectiveOperationException_should_succeed_and_return_actual_instance_with_correct_class() {
    // GIVEN
    final ReflectiveOperationException expected = new ReflectiveOperationException("boom!!");
    // WHEN
    ReflectiveOperationException actual = catchReflectiveOperationException(codeThrowing(expected));
    // THEN
    then(actual).isSameAs(expected);
  }

  @Test
  void catchReflectiveOperationException_should_succeed_and_return_null_if_no_exception_thrown() {
    // WHEN
    ReflectiveOperationException actual = catchReflectiveOperationException(() -> {});
    // THEN
    then(actual).isNull();
  }

  @Test
  void catchReflectiveOperationException_should_catch_mocked_throwable() {
    // GIVEN
    ReflectiveOperationException exception = mock(ReflectiveOperationException.class);
    // WHEN
    Throwable actual = catchReflectiveOperationException(codeThrowing(exception));
    // THEN
    then(actual).isSameAs(exception);
  }

  static ThrowingCallable raisingException(final String reason) {
    return codeThrowing(new Exception(reason));
  }

}
