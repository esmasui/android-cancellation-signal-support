/* 
 * Copyright (C) 2013 uPhyca Inc.
 * 
 * Copyright (C) 2010 The Android Open Source Project
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

package com.uphyca.support.v4.content;

import android.content.Context;
import android.support.v4.content.Loader;

/**
 * An abstract class that performs asynchronous loading of data. While Loaders are active
 * they should monitor the source of their data and deliver new results when the contents
 * change. See {@link android.app.LoaderManager} for more detail.
 * <p>
 * <b>Note on threading:</b> Clients of loaders should as a rule perform any calls on to a Loader from the main thread of their process (that is, the thread the Activity callbacks and other things occur on). Subclasses of Loader (such as {@link AsyncTaskLoaderCompat}) will often perform their work in a separate thread, but when delivering their results this too should be done on the main thread.
 * </p>
 * <p>
 * Subclasses generally must implement at least {@link #onStartLoading()}, {@link #onStopLoading()}, {@link #onForceLoad()}, and {@link #onReset()}.
 * </p>
 * <p>
 * Most implementations should not derive directly from this class, but instead inherit from {@link AsyncTaskLoaderCompat}.
 * </p>
 * <div class="special reference"> <h3>Developer Guides</h3>
 * <p>
 * For more information about using loaders, read the <a href="{@docRoot}guide/topics/fundamentals/loaders.html">Loaders</a> developer guide.
 * </p>
 * </div>
 * 
 * @param <D> The result returned when the load is complete
 */
public class LoaderCompat<D> extends Loader<D> {

    OnLoadCanceledListener<D> mOnLoadCanceledListener;

    /**
     * Interface that is implemented to discover when a Loader has been canceled
     * before it finished loading its data. You do not normally need to implement
     * this yourself; it is used in the implementation of {@link android.app.LoaderManager} to find out when a Loader it is managing has been canceled so that it
     * can schedule the next Loader. This interface should only be used if a
     * Loader is not being used in conjunction with LoaderManager.
     */
    public interface OnLoadCanceledListener<D> {
        /**
         * Called on the thread that created the Loader when the load is canceled.
         * 
         * @param loader the loader that canceled the load
         */
        public void onLoadCanceled(Loader<D> loader);
    }

    /**
     * Stores away the application context associated with context.
     * Since Loaders can be used across multiple activities it's dangerous to
     * store the context directly; always use {@link #getContext()} to retrieve
     * the Loader's Context, don't use the constructor argument directly.
     * The Context returned by {@link #getContext} is safe to use across
     * Activity instances.
     * 
     * @param context used to retrieve the application context.
     */
    public LoaderCompat(Context context) {
        super(context);
    }

    /**
     * Informs the registered {@link OnLoadCanceledListener} that the load has been canceled.
     * Should only be called by subclasses.
     * Must be called from the process's main thread.
     */
    public void deliverCancellation() {
        if (mOnLoadCanceledListener != null) {
            mOnLoadCanceledListener.onLoadCanceled(this);
        }
    }

    /**
     * Registers a listener that will receive callbacks when a load is canceled.
     * The callback will be called on the process's main thread so it's safe to
     * pass the results to widgets.
     * Must be called from the process's main thread.
     * 
     * @param listener The listener to register.
     */
    public void registerOnLoadCanceledListener(OnLoadCanceledListener<D> listener) {
        if (mOnLoadCanceledListener != null) {
            throw new IllegalStateException("There is already a listener registered");
        }
        mOnLoadCanceledListener = listener;
    }

    /**
     * Unregisters a listener that was previously added with {@link #registerOnLoadCanceledListener}.
     * Must be called from the process's main thread.
     * 
     * @param listener The listener to unregister.
     */
    public void unregisterOnLoadCanceledListener(OnLoadCanceledListener<D> listener) {
        if (mOnLoadCanceledListener == null) {
            throw new IllegalStateException("No listener register");
        }
        if (mOnLoadCanceledListener != listener) {
            throw new IllegalArgumentException("Attempting to unregister the wrong listener");
        }
        mOnLoadCanceledListener = null;
    }

    /**
     * Attempt to cancel the current load task.
     * Must be called on the main thread of the process.
     * <p>
     * Cancellation is not an immediate operation, since the load is performed in a background thread. If there is currently a load in progress, this method requests that the load be canceled, and notes this is the case; once the background thread has completed its work its remaining state will be cleared. If another load request comes in during this time, it will be held until the canceled load is complete.
     * 
     * @return Returns <tt>false</tt> if the task could not be canceled,
     *         typically because it has already completed normally, or
     *         because {@link #startLoading()} hasn't been called; returns <tt>true</tt> otherwise. When <tt>true</tt> is returned, the task
     *         is still running and the {@link OnLoadCanceledListener} will be called
     *         when the task completes.
     */
    public boolean cancelLoad() {
        return onCancelLoad();
    }

    /**
     * Subclasses must implement this to take care of requests to {@link #cancelLoad()}.
     * This will always be called from the process's main thread.
     * 
     * @return Returns <tt>false</tt> if the task could not be canceled,
     *         typically because it has already completed normally, or
     *         because {@link #startLoading()} hasn't been called; returns <tt>true</tt> otherwise. When <tt>true</tt> is returned, the task
     *         is still running and the {@link OnLoadCanceledListener} will be called
     *         when the task completes.
     */
    protected boolean onCancelLoad() {
        return false;
    }
}
