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
import kotlin.time.toJavaDuration

/**
 * Fixed delay back off.
 * 
 * @param delay The delay.
 */
data class FixedDelay(val delay: Duration) : BackOff {
    
    /**
     * Constructs a fixed delay back off.
     *
     * @param delay The interval.
     */
    constructor(delay: kotlin.time.Duration) : this(delay.toJavaDuration())

    override fun backOff(context: Context): Duration {
        return delay
    }
}
