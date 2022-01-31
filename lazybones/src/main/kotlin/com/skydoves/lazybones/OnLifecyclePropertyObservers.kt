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
@file:JvmName("OnLifecyclePropertyObservers")
@file:JvmMultifileClass

package com.skydoves.lazybones

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/** A lifecycle observer for performing receiver when the lifecycle state is [On.CREATE]. */
internal class OnCreatePropertyObserver<T : Any>
constructor(private val value: T) : OnLifecyclePropertyObserver<T>(value) {
  override fun onCreate(owner: LifecycleOwner) {
    super.propertyObserver.onChanged(this.value)
  }
}

/** A lifecycle observer for performing receiver when the lifecycle state is [On.START]. */
internal class OnStartPropertyObserver<T : Any>
constructor(private val value: T) : OnLifecyclePropertyObserver<T>(value) {
  override fun onStart(owner: LifecycleOwner) {
    super.propertyObserver.onChanged(this.value)
  }
}

/** A lifecycle observer for performing receiver when the lifecycle state is [On.RESUME]. */
internal class OnResumePropertyObserver<T : Any>
constructor(private val value: T) : OnLifecyclePropertyObserver<T>(value) {
  override fun onResume(owner: LifecycleOwner) {
    super.propertyObserver.onChanged(this.value)
  }
}

/** A lifecycle observer for performing receiver when the lifecycle state is [On.PAUSE]. */
internal class OnPausePropertyObserver<T : Any>
constructor(private val value: T) : OnLifecyclePropertyObserver<T>(value) {
  override fun onPause(owner: LifecycleOwner) {
    super.propertyObserver.onChanged(this.value)
  }
}

/** A lifecycle observer for performing receiver when the lifecycle state is [On.STOP]. */
internal class OnStopPropertyObserver<T : Any>
constructor(private val value: T) : OnLifecyclePropertyObserver<T>(value) {
  override fun onStop(owner: LifecycleOwner) {
    super.propertyObserver.onChanged(this.value)
  }
}

/** A lifecycle observer for performing receiver when the lifecycle state is [On.DESTROY]. */
internal class OnDestroyPropertyObserver<T : Any>
constructor(private val value: T) : OnLifecyclePropertyObserver<T>(value) {
  override fun onDestroy(owner: LifecycleOwner) {
    super.propertyObserver.onChanged(this.value)
  }
}

/** An abstract observer for delegating lazy and receiver. */
internal abstract class OnLifecyclePropertyObserver<T : Any> constructor(
  private val value: T
) : DefaultLifecycleObserver {

  lateinit var propertyObserver: LifecycleAwarePropertyObserver<T>

  fun registerLifecyclePropertyObserver(propertyObserver: LifecycleAwarePropertyObserver<T>) {
    this.propertyObserver = propertyObserver
  }
}
