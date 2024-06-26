/*
 * Copyright (c) 2024 General Motors GTO LLC
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
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
 * SPDX-FileType: SOURCE
 * SPDX-FileCopyrightText: 2024 General Motors GTO LLC
 * SPDX-License-Identifier: Apache-2.0
 */

package org.eclipse.uprotocol.core.udiscovery;

import static org.eclipse.uprotocol.common.util.UStatusUtils.toStatus;
import static org.eclipse.uprotocol.common.util.log.Formatter.status;
import static org.eclipse.uprotocol.common.util.log.Formatter.tag;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import org.eclipse.uprotocol.common.util.log.Key;
import org.eclipse.uprotocol.core.udiscovery.v3.Node;
import org.eclipse.uprotocol.v1.UAuthority;
import org.eclipse.uprotocol.v1.UEntity;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("SameParameterValue")
public class TestBase {
    public static final String TAG = tag("core", TestBase.class.getSimpleName());
    public static final String SERVICE_NAME = "core.udiscovery";
    public static final String TEST_VIN = "1gk12d1t2n10339dc";
    public static final String TEST_DOMAIN = TEST_VIN + ".test.org";
    public static final String TEST_DEVICE = "device";
    public static final String TEST_NAME = String.join(".", TEST_DEVICE, TEST_DOMAIN);
    public static final UAuthority TEST_AUTHORITY = UAuthority.newBuilder().setName(TEST_NAME).build();
    public static final String TEST_ALTERNATE_DEVICE = "cgm";
    public static final String TEST_ENTITY_NAME = "body.access";
    public static final String TEST_ALTERNATE_ENTITY = "cabin.climate";
    public static final String TEST_OBSERVER1 = "observer1";
    public static final String TEST_OBSERVER2 = "observer2";
    public static final String TEST_RESOURCE = "door";
    public static final String TEST_INSTANCE_1 = "front_left";
    public static final String TEST_INSTANCE_2 = "front_right";
    public static final String TEST_INSTANCE_3 = "rear_left";
    public static final String TEST_INSTANCE_4 = "rear_right";
    public static final String TEST_PROPERTY1 = "ent_prop1";
    public static final String TEST_PROPERTY2 = "ent_prop2";
    public static final UEntity SERVICE = UEntity.newBuilder().setName(SERVICE_NAME).setVersionMajor(3).build();
    public static final UEntity TEST_ENTITY = UEntity.newBuilder().setName(TEST_ENTITY_NAME).setVersionMajor(1).build();
    public static final UAuthority TEST_AUTHORITY2 = UAuthority.newBuilder().setName(TEST_ALTERNATE_ENTITY).build();
    public static final int DEFAULT_DEPTH = -1;
    public static final int TTL = 10000;
    public static final int DELAY_MS = 100;

    protected static @NonNull Node jsonToNode(String json) {
        Node.Builder bld = Node.newBuilder();
        try {
            JsonFormat.parser().merge(json, bld);
        } catch (InvalidProtocolBufferException e) {
            Log.e(TAG, status(Key.FAILURE, toStatus(e)));
        }
        return bld.build();
    }

    protected void sleep(long timeout) {
        try {
            new CompletableFuture<>().get(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception ignored) {}
    }
}
