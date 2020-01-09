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
internal class OnCreatePropertyObserver<T>
constructor(private val value: T) : OnLifecyclePropertyObserver<T>(value) {
  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  fun onCreate() = super.propertyObserver.onChanged(this.value)
}

/** A lifecycle observer for performing receiver when the lifecycle state is [On.START]. */
internal class OnStartPropertyObserver<T>
constructor(private val value: T) : OnLifecyclePropertyObserver<T>(value) {
  @OnLifecycleEvent(Lifecycle.Event.ON_START)
  fun onStart() = super.propertyObserver.onChanged(this.value)
}

/** A lifecycle observer for performing receiver when the lifecycle state is [On.RESUME]. */
internal class OnResumePropertyObserver<T>
constructor(private val value: T) : OnLifecyclePropertyObserver<T>(value) {
  @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
  fun onResume() = super.propertyObserver.onChanged(this.value)
}

/** A lifecycle observer for performing receiver when the lifecycle state is [On.PAUSE]. */
internal class OnPausePropertyObserver<T>
constructor(private val value: T) : OnLifecyclePropertyObserver<T>(value) {
  @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
  fun onPause() = super.propertyObserver.onChanged(this.value)
}

/** A lifecycle observer for performing receiver when the lifecycle state is [On.STOP]. */
internal class OnStopPropertyObserver<T>
constructor(private val value: T) : OnLifecyclePropertyObserver<T>(value) {
  @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
  fun onStop() = super.propertyObserver.onChanged(this.value)
}

/** A lifecycle observer for performing receiver when the lifecycle state is [On.DESTROY]. */
internal class OnDestroyPropertyObserver<T>
constructor(private val value: T) : OnLifecyclePropertyObserver<T>(value) {
  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  fun onDestroy() = super.propertyObserver.onChanged(this.value)
}

/** A lifecycle observer for performing receiver when the lifecycle state is [On.ANY]. */
internal class OnAnyPropertyObserver<T>
constructor(private val value: T) : OnLifecyclePropertyObserver<T>(value) {
  @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
  fun onAny() = super.propertyObserver.onChanged(this.value)
}

/** An abstract observer for delegating lazy and receiver. */
internal abstract class OnLifecyclePropertyObserver<T>
constructor(private val value: T) : LifecycleObserver {

  lateinit var propertyObserver: LifecycleAwarePropertyObserver<T>

  fun registerLifecyclePropertyObserver(propertyObserver: LifecycleAwarePropertyObserver<T>) {
    this.propertyObserver = propertyObserver
  }
}
