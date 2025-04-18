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

import retry.BackoffPolicies;
import retry.MaxAttempts;
import retry.RetryPolicy;

import java.io.IOException;
import java.util.Random;

public class Proxy {

    public static void main(String[] args) throws IOException {
        RetryPolicy policy = new RetryPolicy.Builder(new MaxAttempts(99), BackoffPolicies.NONE).build();
        Api api = policy.proxy(Api.class, new Impl());
        System.out.println(api.execute());
    }

    public interface Api {
        String execute() throws IOException;
    }

    public static class Impl implements Api {
        Random random = new Random(System.currentTimeMillis());

        @Override
        public String execute() throws IOException {
            int r = random.nextInt(10);
            if (r < 6) throw new IOException("exe failed");
            return "exe-" + r;
        }
    }
}
