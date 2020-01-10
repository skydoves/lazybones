/*
 * Designed and developed by 2020 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused")

package com.skydoves.lazybones

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/** A lifecycle observer for performing receiver when the lifecycle state is [On.CREATE]. */
internal class OnCreateObserver<T> : OnLifecycleObserver<T>() {
  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  fun onCreate() = super.initialize()
}

/** A lifecycle observer for performing receiver when the lifecycle state is [On.START]. */
internal class OnStartObserver<T> : OnLifecycleObserver<T>() {
  @OnLifecycleEvent(Lifecycle.Event.ON_START)
  fun onStart() = super.initialize()
}

/** A lifecycle observer for performing receiver when the lifecycle state is [On.RESUME]. */
internal class OnResumeObserver<T> : OnLifecycleObserver<T>() {
  @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
  fun onResume() = super.initialize()
}

/** A lifecycle observer for performing receiver when the lifecycle state is [On.PAUSE]. */
internal class OnPauseObserver<T> : OnLifecycleObserver<T>() {
  @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
  fun onPause() = super.initialize()
}

/** A lifecycle observer for performing receiver when the lifecycle state is [On.STOP]. */
internal class OnStopObserver<T> : OnLifecycleObserver<T>() {
  @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
  fun onStop() = super.initialize()
}

/** A lifecycle observer for performing receiver when the lifecycle state is [On.DESTROY]. */
internal class OnDestroyObserver<T> : OnLifecycleObserver<T>() {
  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  fun onDestroy() = super.initialize()
}

/** A lifecycle observer for performing receiver when the lifecycle state is [On.ANY]. */
internal class OnAnyObserver<T> : OnLifecycleObserver<T>() {
  @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
  fun onAny() = super.initialize()
}

/** An abstract observer for delegating lazy and receiver. */
internal abstract class OnLifecycleObserver<T> : LifecycleObserver {

  private lateinit var lazy: Lazy<T>
  private lateinit var receiver: (T.() -> Unit)

  fun registerLazyProperty(lazy: Lazy<T>, receiver: T.() -> Unit) {
    this.lazy = lazy
    this.receiver = receiver
  }

  fun initialize() = this.receiver(this.lazy.value)
}
