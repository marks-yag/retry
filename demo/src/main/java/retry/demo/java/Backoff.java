/*
 * Copyright 2025-2025 marks.yag@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package retry.demo.java;

import retry.RetryPolicy;

import static retry.BackoffPolicies.exponentialDelayInSeconds;
import static retry.BackoffPolicies.randomDelayInSeconds;
import static retry.Rules.maxAttempts;

public class Backoff {

    public static void main(String[] args) throws Exception {
        RetryPolicy policy = new RetryPolicy.Builder(
            maxAttempts(3),
            exponentialDelayInSeconds(1, 10).plus(randomDelayInSeconds(1, 2))
        ).build();
        policy.call(
            () -> {
                System.out.println("Hello world!");
                return null;
            }
        );
    }
}
