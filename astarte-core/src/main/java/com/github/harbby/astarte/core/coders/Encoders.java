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
package com.github.harbby.astarte.core.coders;

import com.github.harbby.astarte.core.coders.array.AnyArrayEncoder;
import com.github.harbby.astarte.core.coders.array.BooleanArrayEncoder;
import com.github.harbby.astarte.core.coders.array.ByteArrayEncoder;
import com.github.harbby.astarte.core.coders.array.CharArrayEncoder;
import com.github.harbby.astarte.core.coders.array.DoubleArrayEncoder;
import com.github.harbby.astarte.core.coders.array.FloatArrayEncoder;
import com.github.harbby.astarte.core.coders.array.IntArrayEncoder;
import com.github.harbby.astarte.core.coders.array.LongArrayEncoder;
import com.github.harbby.astarte.core.coders.array.ShortArrayEncoder;
import com.github.harbby.gadtry.base.Lazys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Date;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * Encoder factory
 */
public final class Encoders
{
    protected static final Logger logger = LoggerFactory.getLogger(Encoders.class);

    private Encoders() {}

    private static final Encoder<Long> longEncoder = new LongEncoder();
    private static final Encoder<Boolean> booleanEncoder = new BooleanEncoder();
    private static final Encoder<Byte> byteEncoder = new ByteEncoder();
    private static final Encoder<Character> charEncoder = new CharEncoder();
    private static final Encoder<Date> dateEncoder = new DateEncoder();
    private static final Encoder<Short> shortEncoder = new ShortEncoder();
    private static final Encoder<Float> floatEncoder = new FloatEncoder();
    private static final Encoder<java.sql.Date> sqlDateEncoder = new SqlDateEncoder();
    private static final Encoder<Timestamp> timestampEncoder = new SqlTimestampEncoder();
    private static final Encoder<long[]> longArrEncoder = new LongArrayEncoder();

    private static final Encoder<Integer> intEncoder = new IntEncoder();
    private static final Encoder<int[]> intArrEncoder = new IntArrayEncoder();
    private static final Encoder<boolean[]> booleanArrayEncoder = new BooleanArrayEncoder();
    private static final Encoder<byte[]> byteArrayEncoder = new ByteArrayEncoder();
    private static final Encoder<char[]> charArrayEncoder = new CharArrayEncoder();
    private static final Encoder<double[]> doubleArrayEncoder = new DoubleArrayEncoder();
    private static final Encoder<float[]> floatArrayEncoder = new FloatArrayEncoder();
    private static final Encoder<short[]> shortArrayEncoder = new ShortArrayEncoder();

    private static final Encoder<Double> doubleEncoder = new DoubleEncoder();
    private static final Encoder<String> charStringEncoder = new CharStringEncoder();
    private static final Encoder<String> ASCII_STRING_ENCODER = new AsciiStringEncoder();
    private static final Encoder<String> UTF8_STRING_ENCODER = new UTF8StringEncoder();

    private static final Supplier<Encoder<?>> javaEncoder = Lazys.goLazy(() -> {
        logger.warn("Don't use java serialization encoder");
        return new JavaEncoder<>();
    });

    @SuppressWarnings("unchecked")
    public static <E> Encoder<E> javaEncoder()
    {
        return (Encoder<E>) javaEncoder.get();
    }

    /**
     * encode map
     *
     * @param kEncoder
     * @param vEncoder
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> MapEncoder<K, V> mapEncoder(Encoder<K> kEncoder, Encoder<V> vEncoder)
    {
        requireNonNull(kEncoder, "key Encoder is null");
        requireNonNull(vEncoder, "value Encoder is null");
        return new MapEncoder<>(kEncoder, vEncoder);
    }

    public static <V> AnyArrayEncoder<V> arrayEncoder(Encoder<V> vEncoder, Class<V> aClass)
    {
        requireNonNull(vEncoder, "value Encoder is null");
        return new AnyArrayEncoder<>(vEncoder, aClass);
    }

    public static <K, V> Tuple2Encoder<K, V> tuple2(Encoder<K> kEncoder, Encoder<V> vEncoder)
    {
        requireNonNull(kEncoder, "key Encoder is null");
        requireNonNull(vEncoder, "value Encoder is null");
        return new Tuple2Encoder<>(kEncoder, vEncoder);
    }

    public static Encoder<String> jCharString()
    {
        return charStringEncoder;
    }

    public static Encoder<String> asciiString()
    {
        return ASCII_STRING_ENCODER;
    }

    public static Encoder<String> UTF8String()
    {
        return UTF8_STRING_ENCODER;
    }

    public static Encoder<Boolean> jBoolean()
    {
        return booleanEncoder;
    }

    public static Encoder<Byte> jByte()
    {
        return byteEncoder;
    }

    public static Encoder<Float> jFloat()
    {
        return floatEncoder;
    }

    public static Encoder<Date> jDate()
    {
        return dateEncoder;
    }

    public static Encoder<Short> jShort()
    {
        return shortEncoder;
    }

    public static Encoder<java.sql.Date> sqlDate()
    {
        return sqlDateEncoder;
    }

    public static Encoder<Timestamp> sqlTimestamp()
    {
        return timestampEncoder;
    }

    public static Encoder<Character> jChar()
    {
        return charEncoder;
    }

    public static Encoder<Long> jLong()
    {
        return longEncoder;
    }

    public static Encoder<long[]> jLongArray()
    {
        return longArrEncoder;
    }

    public static Encoder<boolean[]> jBooleanArray()
    {
        return booleanArrayEncoder;
    }

    public static Encoder<byte[]> jByteArray()
    {
        return byteArrayEncoder;
    }

    public static Encoder<char[]> jCharArray()
    {
        return charArrayEncoder;
    }

    public static Encoder<double[]> jDoubleArray()
    {
        return doubleArrayEncoder;
    }

    public static Encoder<float[]> jFloatArray()
    {
        return floatArrayEncoder;
    }

    public static Encoder<short[]> jShortArray()
    {
        return shortArrayEncoder;
    }

    public static Encoder<String[]> jStringArray()
    {
        return new AnyArrayEncoder<>(UTF8_STRING_ENCODER, String.class);
    }

    public static Encoder<Integer> jInt()
    {
        return intEncoder;
    }

    public static Encoder<int[]> jIntArray()
    {
        return intArrEncoder;
    }

    public static Encoder<Double> jDouble()
    {
        return doubleEncoder;
    }
}
