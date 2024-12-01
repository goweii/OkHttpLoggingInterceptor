/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package per.goweii.okhttp3.logging

import okio.Buffer
import org.junit.Test

import org.junit.Assert.*
import per.goweii.okhttp3.logging.internal.readProbablyUtf8String

class ProbablyUtf8Test {
    @Test
    fun readProbablyUtf8String() {
        assertEquals(
            StringBuilder().also {
                Buffer().readProbablyUtf8String(it, Charsets.UTF_8)
            }.toString(),
            ""
        )
        assertEquals(
            StringBuilder().also {
                Buffer().writeUtf8("abc")
                    .readProbablyUtf8String(it, Charsets.UTF_8)
            }.toString(),
            "abc"
        )
        assertEquals(
            StringBuilder().also {
                Buffer().writeUtf8("new\r\nlines")
                    .readProbablyUtf8String(it, Charsets.UTF_8)
            }.toString(),
            "new\r\nlines"
        )
        assertEquals(
            StringBuilder().also {
                Buffer().writeUtf8("white\t space")
                    .readProbablyUtf8String(it, Charsets.UTF_8)
            }.toString(),
            "white\t space"
        )
        assertEquals(
            StringBuilder().also {
                Buffer()
                    .writeUtf8("a")
                    .writeByte(0x80)
                    .writeUtf8("b")
                    .readProbablyUtf8String(it, Charsets.UTF_8)
            }.toString(),
            StringBuilder()
                .append("a")
                .append(String(byteArrayOf(0x80.toByte()), Charsets.UTF_8))
                .append("b")
                .toString()
        )
        assertEquals(
            StringBuilder().also {
                Buffer()
                    .writeUtf8("a")
                    .writeByte(0xc0)
                    .writeUtf8("b")
                    .readProbablyUtf8String(it, Charsets.UTF_8)
            }.toString(),
            StringBuilder()
                .append("a")
                .append(String(byteArrayOf(0xc0.toByte()), Charsets.UTF_8))
                .append("b")
                .toString()
        )
        assertEquals(
            StringBuilder().also {
                Buffer()
                    .writeUtf8("a")
                    .writeByte(0x00)
                    .writeUtf8("b")
                    .readProbablyUtf8String(it, Charsets.UTF_8)
            }.toString(),
            StringBuilder()
                .append("a")
                .toString()
        )
    }
}
