/*
 * Copyright 2018-2020 marks.yag@gmail.com
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

import java.time.Duration
import kotlin.random.Random
import kotlin.random.nextLong

data class Exponential @JvmOverloads constructor(
    val minInitIntervalMs: Long = 1000,
    val maxInitIntervalMs: Long = minInitIntervalMs,
    val maxIntervalMs: Long = 60000
) : BackOff {

    private val initIntervalMs = random.nextLong(LongRange(minInitIntervalMs,  maxInitIntervalMs))

    override fun backOff(context: Context): Duration {
        var value = initIntervalMs
        for (i in 0 until context.retryCount) {
            if (value < Long.MAX_VALUE / 2) {
                value = value shl 1
            } else {
                value = Long.MAX_VALUE
                break
            }
            if (value > maxIntervalMs)
                break
        }
        value = minOf(value, maxIntervalMs)
        return Duration.ofMillis(value)
    }

    companion object {
        @JvmStatic
        private val random = Random(System.currentTimeMillis())
    }
}
