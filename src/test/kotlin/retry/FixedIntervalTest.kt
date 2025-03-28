/*
 * Copyright 2025-2025 marks.yag@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package retry

import org.assertj.core.api.Assertions.assertThat
import retry.BackoffPolicies.FixedInterval
import java.time.Duration
import java.time.Instant
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds

class FixedIntervalTest {

    @Test
    fun testAlign() {
        val backoff = FixedInterval(1.seconds)
        assertThat(backoff.backoff(Context(Instant.MIN, Instant.MIN.plusMillis(2500), 3, RuntimeException())))
            .isEqualTo(Duration.ofMillis(500))
    }

    @Test
    fun testTimeout() {
        val backoff = FixedInterval(1.seconds)
        assertThat(backoff.backoff(Context(Instant.MIN, Instant.MIN.plusMillis(2500), 2, RuntimeException())))
            .isEqualTo(Duration.ofMillis(0))
    }
}