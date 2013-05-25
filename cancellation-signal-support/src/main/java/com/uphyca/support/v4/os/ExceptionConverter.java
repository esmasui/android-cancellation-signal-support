/*
 * Copyright (C) 2013 uPhyca Inc. 
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

package com.uphyca.support.v4.os;

import android.os.Build;
import android.os.OperationCanceledException;

public abstract class ExceptionConverter {

    public static RuntimeException convertException(RuntimeException e) {
        return sThrower.convertException(e);
    }

    private static final Thrower sThrower = newThrower();

    private static final Thrower newThrower() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return ModernThrowerFactory.newInstance();
        } else {
            return LegacyThrowerFactory.newInstance();
        }
    }

    static final class LegacyThrowerFactory {
        static final Thrower newInstance() {
            return new LegacyThrower();
        }
    }

    static final class ModernThrowerFactory {
        static final Thrower newInstance() {
            return new ModernThrower();
        }
    }

    static interface Thrower {
        RuntimeException convertException(RuntimeException e);
    }

    static final class LegacyThrower implements Thrower {
        @Override
        public RuntimeException convertException(RuntimeException e) {
            return e;
        }
    }

    static final class ModernThrower implements Thrower {
        @Override
        public RuntimeException convertException(RuntimeException e) {
            if (e instanceof OperationCanceledException) {
                OperationCanceledExceptionCompat compat = new OperationCanceledExceptionCompat(e);
                compat.fillInStackTrace();
                return compat;
            }
            return e;
        }
    }
}
