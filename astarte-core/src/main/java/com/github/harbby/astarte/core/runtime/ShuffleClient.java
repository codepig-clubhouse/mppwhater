/*
 * Copyright (C) 2018 The Astarte Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.harbby.astarte.core.runtime;

import com.github.harbby.astarte.core.coders.Encoder;
import com.github.harbby.gadtry.collection.tuple.Tuple2;

import java.io.Closeable;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Iterator;

public interface ShuffleClient
        extends Closeable
{
    public <K, V> Iterator<Tuple2<K, V>> readShuffleData(Encoder<Tuple2<K, V>> encoder, int shuffleId, int reduceId)
            throws IOException;

    public static ShuffleClient getLocalShuffleClient(ShuffleManagerService shuffleManagerService)
    {
        return shuffleManagerService::getShuffleDataIterator;
    }

    public static ShuffleClient getClusterShuffleClient(Collection<SocketAddress> shuffleServices)
            throws InterruptedException
    {
        return ClusterShuffleClient.start(shuffleServices);
    }

    @Override
    default void close()
            throws IOException
    {}
}
