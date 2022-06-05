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

import org.mockito.Mockito
import java.io.IOException
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.test.Test
import kotlin.test.assertEquals

class RetrySubmitTest {

    @Test
    fun testRetrySuccess() {
        val retry = Retry().apply {
            retryCondition = MaxRetries(10)
            backOff = BackOff.duration(1, TimeUnit.SECONDS)
        }
        val mock = Mockito.mock(Callable::class.java)
        Mockito.doThrow(*Array(10) {
            IOException()
        }).doReturn("done").`when`(mock).call()

        val executor = Executors.newScheduledThreadPool(1)
        assertEquals("done", retry.submit(executor) {
            mock.call()
        }.get())
        Mockito.verify(mock, Mockito.times(11)).call()

        executor.shutdownNow()
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS)
    }

}
