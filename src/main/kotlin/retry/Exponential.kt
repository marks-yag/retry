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
import kotlin.time.toJavaDuration

data class Exponential(
    val initInterval: Duration,
    val maxInterval: Duration
) : BackOff {
    
    constructor(initInterval: kotlin.time.Duration, maxInterval: kotlin.time.Duration) : this(initInterval.toJavaDuration(), maxInterval.toJavaDuration())

    override fun backOff(context: Context): Duration {
        var value = initInterval.toMillis()
        for (i in 0 until context.retryCount) {
            if (value < Long.MAX_VALUE / 2) {
                value = value shl 1
            } else {
                value = Long.MAX_VALUE
                break
            }
            if (value > maxInterval.toMillis()) {
                break
            }
        }
        value = minOf(value, maxInterval.toMillis())
        return Duration.ofMillis(value)
    }

}
