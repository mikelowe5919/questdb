/*******************************************************************************
 *     ___                  _   ____  ____
 *    / _ \ _   _  ___  ___| |_|  _ \| __ )
 *   | | | | | | |/ _ \/ __| __| | | |  _ \
 *   | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *    \__\_\\__,_|\___||___/\__|____/|____/
 *
 *  Copyright (c) 2014-2019 Appsicle
 *  Copyright (c) 2019-2022 QuestDB
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 ******************************************************************************/

package io.questdb.griffin.engine.functions.columns;

import io.questdb.cairo.ColumnType;
import org.junit.Assert;
import org.junit.Test;

public class GeoIntColumnTest {

    @Test
    public void testNewInstanceReturnsCorrectTypeForCachedColumnIndexes() {
        for (int col = 0; col < 40; col++) {
            assertBits(ColumnType.GEOINT_MIN_BITS, col);
            assertBits(ColumnType.GEOINT_MAX_BITS, col);
        }
    }

    private static void assertBits(int bits, int column) {
        int type = ColumnType.getGeoHashTypeWithBits(bits);
        GeoIntColumn col = GeoIntColumn.newInstance(column, type);
        String desc = "col=" + column + ",bits=" + bits;

        Assert.assertEquals(desc, type, col.getType());
        Assert.assertEquals(desc, column, col.getColumnIndex());

        boolean isCached = GeoIntColumn.newInstance(column, type) == GeoIntColumn.newInstance(column, type);

        if (column < ColumnUtils.STATIC_COLUMN_COUNT) {
            Assert.assertTrue(isCached);
        } else {
            Assert.assertFalse(isCached);
        }
    }
}
