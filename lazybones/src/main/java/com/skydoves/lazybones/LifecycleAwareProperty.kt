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

import androidx.lifecycle.LifecycleOwner

/** LifecycleAwareProperty is an observer for notifying lifecycle is changed. */
class LifecycleAwareProperty<T>
constructor(private val lifecycleOwner: LifecycleOwner, var value: T) {

  /** observes on the [On] lifecycle state. */
  @LazybonesWithNoInlines
  fun observeOn(on: On, receiver: (T) -> Unit) = apply {
    addLifecycleObserver(on, receiver)
  }

  /** observes on the [On.CREATE] lifecycle state. */
  @LazybonesWithNoInlines
  fun observeOnCreate(receiver: (T) -> Unit) = apply {
    addLifecycleObserver(On.CREATE, receiver)
  }

  /** observes on the [On.START] lifecycle state. */
  @LazybonesWithNoInlines
  fun observeOnStart(receiver: (T) -> Unit) = apply {
    addLifecycleObserver(On.START, receiver)
  }

  /** observes on the [On.RESUME] lifecycle state. */
  @LazybonesWithNoInlines
  fun observeOnResume(receiver: (T) -> Unit) = apply {
    addLifecycleObserver(On.RESUME, receiver)
  }

  /** observes on the [On.PAUSE] lifecycle state. */
  @LazybonesWithNoInlines
  fun observeOnPause(receiver: (T) -> Unit) = apply {
    addLifecycleObserver(On.PAUSE, receiver)
  }

  /** observes on the [On.STOP] lifecycle state. */
  @LazybonesWithNoInlines
  fun observeOnStop(receiver: (T) -> Unit) = apply {
    addLifecycleObserver(On.STOP, receiver)
  }

  /** observes on the [On.DESTROY] lifecycle state. */
  @LazybonesWithNoInlines
  fun observeOnDestroy(receiver: (T) -> Unit) = apply {
    addLifecycleObserver(On.DESTROY, receiver)
  }

  /** observes on the [On.ANY] lifecycle state. */
  @LazybonesWithNoInlines
  fun observeOnAny(receiver: (T) -> Unit) = apply {
    addLifecycleObserver(On.ANY, receiver)
  }

  /** adds a lifecycle observer based on [On] lifecycle state internally. */
  private fun addLifecycleObserver(on: On, receiver: (T) -> Unit) {
    val observer = on.getOnLifecyclePropertyObserver(this.value)
    observer.registerLifecyclePropertyObserver(object : LifecycleAwarePropertyObserver<T> {
      override fun onChanged(value: T) {
        receiver(value)
      }
    })
    this.lifecycleOwner.lifecycle.addObserver(observer)
  }
}
