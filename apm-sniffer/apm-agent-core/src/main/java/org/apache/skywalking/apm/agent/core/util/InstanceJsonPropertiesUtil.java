/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.skywalking.apm.agent.core.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.skywalking.apm.agent.core.conf.Config;
import org.apache.skywalking.apm.network.common.v3.KeyStringValuePair;
import org.apache.skywalking.apm.util.StringUtil;

public class InstanceJsonPropertiesUtil {
    private static final Gson GSON = new Gson();

    public static List<KeyStringValuePair> parseProperties() {
        List<KeyStringValuePair> properties = new ArrayList<>();

        if (StringUtil.isNotEmpty(Config.Agent.INSTANCE_PROPERTIES_JSON)) {
            Map<String, String> json = GSON.fromJson(
                Config.Agent.INSTANCE_PROPERTIES_JSON,
                new TypeToken<Map<String, String>>() {
                }.getType()
            );
            json.forEach(
                (key, val) -> properties.add(KeyStringValuePair.newBuilder().setKey(key).setValue(val).build()));
        }

        properties.add(KeyStringValuePair.newBuilder().setKey("namespace").setValue(Config.Agent.NAMESPACE).build());
        properties.add(KeyStringValuePair.newBuilder().setKey("cluster").setValue(Config.Agent.NAMESPACE).build());

        return properties;
    }
}
