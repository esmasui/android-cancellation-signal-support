/*
 * Copyright (C) 2013 uPhyca Inc.
 * 
 * Based on Copyright (C) 2012 The Android Open Source Project
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

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.CancellationSignal;

/**
 * Provides the ability to cancel an operation in progress.
 */
public final class CancellationSignalCompat {
    private boolean mIsCanceled;
    private OnCancelListener mOnCancelListener;
    private boolean mCancelInProgress;
    private final Canceller mCanceller;

    /**
     * Creates a cancellation signal, initially not canceled.
     */
    public CancellationSignalCompat() {
        this(null);
    }

    public CancellationSignalCompat(Object cancellationSignal) {
        mCanceller = newCanceller(cancellationSignal);
    }

    /**
     * Returns true if the operation has been canceled.
     * 
     * @return True if the operation has been canceled.
     */
    public boolean isCanceled() {
        synchronized (this) {
            return mIsCanceled;
        }
    }

    /**
     * Throws {@link OperationCanceledExceptionCompat} if the operation has been canceled.
     * 
     * @throws OperationCanceledExceptionCompat if the operation has been canceled.
     */
    public void throwIfCanceled() {
        if (isCanceled()) {
            throw new OperationCanceledExceptionCompat();
        }
    }

    /**
     * Cancels the operation and signals the cancellation listener.
     * If the operation has not yet started, then it will be canceled as soon as it does.
     */
    public void cancel() {
        final OnCancelListener listener;
        synchronized (this) {
            if (mIsCanceled) {
                return;
            }
            mIsCanceled = true;
            mCancelInProgress = true;
            listener = mOnCancelListener;
        }

        try {
            if (listener != null) {
                listener.onCancel();
            }

            mCanceller.cancel();

        } finally {
            synchronized (this) {
                mCancelInProgress = false;
                notifyAll();
            }
        }
    }

    /**
     * Sets the cancellation listener to be called when canceled.
     * This method is intended to be used by the recipient of a cancellation signal
     * such as a database or a content provider to handle cancellation requests
     * while performing a long-running operation. This method is not intended to be
     * used by applications themselves.
     * If {@link CancellationSignalCompat#cancel} has already been called, then the provided
     * listener is invoked immediately.
     * This method is guaranteed that the listener will not be called after it
     * has been removed.
     * 
     * @param listener The cancellation listener, or null to remove the current listener.
     */
    public void setOnCancelListener(OnCancelListener listener) {
        synchronized (this) {
            waitForCancelFinishedLocked();

            if (mOnCancelListener == listener) {
                return;
            }
            mOnCancelListener = listener;
            if (!mIsCanceled || listener == null) {
                return;
            }
        }
        listener.onCancel();
    }

    private void waitForCancelFinishedLocked() {
        while (mCancelInProgress) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
    }

    /**
     * Listens for cancellation.
     */
    public interface OnCancelListener {
        /**
         * Called when {@link CancellationSignalCompat#cancel} is invoked.
         */
        void onCancel();
    }

    @SuppressLint("NewApi")
    public static final CancellationSignalCompat valueOf(final CancellationSignal cancellationSignal) {
        CancellationSignalCompat returnThis = new CancellationSignalCompat(cancellationSignal);
        return returnThis;
    }

    @SuppressLint("NewApi")
    public final CancellationSignal toCancellationSignal() {
        return (CancellationSignal) mCanceller.getCancellationSignal();
    }

    final OnCancelListener getOnCancelListener() {
        return mOnCancelListener;
    }

    private static final Canceller newCanceller(Object cancellationSignal) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return ModernCancellerFactory.newInstance(cancellationSignal);
        } else {
            return LegacyCancellerFactory.newInstance();
        }
    }

    private static final class LegacyCancellerFactory {
        static Canceller newInstance() {
            return new LegacyCanceller();
        }
    }

    private static final class ModernCancellerFactory {
        static Canceller newInstance(Object cancellationSignal) {
            return new ModernCanceller((CancellationSignal) cancellationSignal);
        }
    }

    private static interface Canceller {
        void cancel();

        Object getCancellationSignal();
    }

    private static final class LegacyCanceller implements Canceller {

        @Override
        public void cancel() {
        }

        @Override
        public Object getCancellationSignal() {
            return null;
        }
    }

    @SuppressLint("NewApi")
    private static final class ModernCanceller implements Canceller, CancellationSignal.OnCancelListener {

        private final CancellationSignal mCancellationSignal;

        ModernCanceller(CancellationSignal cancellationSignal) {
            if (cancellationSignal == null) {
                mCancellationSignal = new CancellationSignal();
                mCancellationSignal.setOnCancelListener(this);
            } else {
                mCancellationSignal = cancellationSignal;
            }
        }

        @Override
        public void cancel() {
            mCancellationSignal.cancel();
        }

        @Override
        public Object getCancellationSignal() {
            return mCancellationSignal;
        }

        @Override
        public void onCancel() {
            //noop
        }
    }
}
