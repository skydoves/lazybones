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
import java.io.Serializable

/** returns a [Lazybones] delegate for initializing lifecycle aware property. */
@LazybonesWithNoInlines
inline fun <reified T : Any> LifecycleOwner.lifecycleAware(
  noinline initializer: () -> T
): Lazybones<T> {
  return Lazybones(this, lazy(LazyThreadSafetyMode.NONE) { initializer() })
}

inline fun <reified T : Any> LifecycleOwner.lifecycleAware(
  value: T
): LifecycleAwareProperty<T> {
  return LifecycleAwareProperty(this, value)
}

/** [Lazybones] is a wrapper class having [Lazy] property for lifecycle aware. */
class Lazybones<out T : Any>(
  private val lifecycleOwner: LifecycleOwner,
  private val lazy: Lazy<T>
) : Serializable {

  /** returns a [Lazybones] with an [On] lifecycle state and a [receiver]. */
  @LazybonesWithNoInlines
  internal fun on(on: On, receiver: T.() -> Unit) = apply {
    addLifecycleObserver(on, receiver)
    return this
  }

  /** returns a [Lazybones] and the receiver will be aware of [On.CREATE] lifecycle state. */
  @LazybonesWithNoInlines
  fun onCreate(receiver: T.() -> Unit) = apply {
    addLifecycleObserver(On.CREATE, receiver)
    return this
  }

  /** returns a [Lazybones] and the receiver will be aware of [On.START] lifecycle state. */
  @LazybonesWithNoInlines
  fun onStart(receiver: T.() -> Unit) = apply {
    addLifecycleObserver(On.START, receiver)
    return this
  }

  /** returns a [Lazybones] and the receiver will be aware of [On.RESUME] lifecycle state. */
  @LazybonesWithNoInlines
  fun onResume(receiver: T.() -> Unit) = apply {
    addLifecycleObserver(On.RESUME, receiver)
    return this
  }

  /** returns a [Lazybones] and the receiver will be aware of [On.PAUSE] lifecycle state. */
  @LazybonesWithNoInlines
  fun onPause(receiver: T.() -> Unit) = apply {
    addLifecycleObserver(On.PAUSE, receiver)
    return this
  }

  /** returns a [Lazybones] and the receiver will be aware of [On.STOP] lifecycle state. */
  @LazybonesWithNoInlines
  fun onStop(receiver: T.() -> Unit) = apply {
    addLifecycleObserver(On.STOP, receiver)
    return this
  }

  /** returns a [Lazybones] and the receiver will be aware of [On.DESTROY] lifecycle state. */
  @LazybonesWithNoInlines
  fun onDestroy(receiver: T.() -> Unit) = apply {
    addLifecycleObserver(On.DESTROY, receiver)
    return this
  }

  /** returns a [Lazybones] and the receiver will be aware of [On.ANY] lifecycle state. */
  @LazybonesWithNoInlines
  fun onAny(receiver: T.() -> Unit) = apply {
    addLifecycleObserver(On.ANY, receiver)
    return this
  }

  /** adds a lifecycle observer based on [On] lifecycle state internally. */
  private fun addLifecycleObserver(on: On, receiver: T.() -> Unit) {
    val observer = on.getOnLifecycleObserver<T>()
    observer.registerLazyProperty(this.lazy, receiver)
    this.lifecycleOwner.lifecycle.addObserver(observer)
  }

  /** gets a lazy class. */
  @LazybonesWithNoInlines
  fun lazy() = this.lazy

  /** gets a value from the lazy class. */
  fun value() = this.lazy.value

  override fun toString() = "Lazybones(" +
    "lifecycleOwner=$lifecycleOwner," +
    "lazy=$lazy" +
    ")"
}
