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

/**
 * @author skydoves (Jaewoong Eum)
 *
 * Returns a [Lazybones] delegate for initializing lifecycle aware property lazily.
 *
 * @param lazyThreadSafetyMode Specifies how a [Lazy] instance synchronizes initialization among multiple threads.
 * @param initializer A instance initializer for initializing the [T] lazily.
 *
 * @return [Lazybones] A lazybones wrapper for creating a lifecycle-aware property.
 */
@JvmSynthetic
@LazybonesWithNoInlines
inline fun <reified T : Any> LifecycleOwner.lifecycleAware(
  lazyThreadSafetyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
  noinline initializer: () -> T,
): Lazybones<T> {
  return Lazybones(this, lazy(lazyThreadSafetyMode) { initializer() })
}

/**
 * @author skydoves (Jaewoong Eum)
 *
 * Returns a [LifecycleAwareProperty] delegate for initializing lifecycle observable property instantly.
 *
 * @param value The initialization value.
 *
 * @return [LifecycleAwareProperty] A wrapper for creating lifecycle observable property.
 */
@JvmSynthetic
inline fun <reified T : Any> LifecycleOwner.lifecycleAware(
  value: T
): LifecycleAwareProperty<T> {
  return LifecycleAwareProperty(this, value)
}

/**
 * @author skydoves (Jaewoong Eum)
 *
 * [Lazybones] is a wrapper class that initializes [Lazy] property based on the lifecycle.
 *
 * @property lifecycleOwner A class that has an Android lifecycle for obesrving the lifecycle events.
 * @property lazy A lazy initialization for initializing the value [T] lazily.
 */
class Lazybones<out T : Any> constructor(
  private val lifecycleOwner: LifecycleOwner,
  private val lazy: Lazy<T>
) : Serializable {

  /**
   * Returns a [Lazybones] with an [On] lifecycle state and a [receiver].
   *
   * @param on Android lifecycle for creating lifecycle-aware properties.
   * @param receiver A receiver that will be executed based on the lifecycle lazily.
   */
  @JvmSynthetic
  @LazybonesWithNoInlines
  internal fun on(on: On, receiver: T.() -> Unit) = addLifecycleObserver(on, receiver)

  /**
   * Returns a [Lazybones] and the receiver will be aware of [On.CREATE] lifecycle state.
   *
   * @param receiver A receiver that will be executed based on the lifecycle lazily.
   */
  @JvmSynthetic
  @LazybonesWithNoInlines
  fun onCreate(receiver: T.() -> Unit) = addLifecycleObserver(On.CREATE, receiver)

  /**
   * Returns a [Lazybones] and the receiver will be aware of [On.START] lifecycle state.
   *
   * @param receiver A receiver that will be executed based on the lifecycle lazily.
   */
  @JvmSynthetic
  @LazybonesWithNoInlines
  fun onStart(receiver: T.() -> Unit) = addLifecycleObserver(On.START, receiver)

  /**
   * Returns a [Lazybones] and the receiver will be aware of [On.RESUME] lifecycle state.
   *
   * @param receiver A receiver that will be executed based on the lifecycle lazily.
   */
  @JvmSynthetic
  @LazybonesWithNoInlines
  fun onResume(receiver: T.() -> Unit) = addLifecycleObserver(On.RESUME, receiver)

  /**
   * Returns a [Lazybones] and the receiver will be aware of [On.PAUSE] lifecycle state.
   *
   * @param receiver A receiver that will be executed based on the lifecycle lazily.
   */
  @JvmSynthetic
  @LazybonesWithNoInlines
  fun onPause(receiver: T.() -> Unit) = addLifecycleObserver(On.PAUSE, receiver)

  /**
   * Returns a [Lazybones] and the receiver will be aware of [On.STOP] lifecycle state.
   *
   * @param receiver A receiver that will be executed based on the lifecycle lazily.
   */
  @JvmSynthetic
  @LazybonesWithNoInlines
  fun onStop(receiver: T.() -> Unit) = addLifecycleObserver(On.STOP, receiver)

  /**
   * Returns a [Lazybones] and the receiver will be aware of [On.DESTROY] lifecycle state.
   *
   * @param receiver A receiver that will be executed based on the lifecycle lazily.
   */
  @JvmSynthetic
  @LazybonesWithNoInlines
  fun onDestroy(receiver: T.() -> Unit) = addLifecycleObserver(On.DESTROY, receiver)

  /**
   * Adds a lifecycle observer based on [On] lifecycle state internally.
   *
   * @param on Android lifecycle for creating lifecycle-aware properties.
   * @param receiver A receiver that will be executed based on the every lifecycle lazily.
   */
  @JvmSynthetic
  private fun addLifecycleObserver(on: On, receiver: T.() -> Unit) = apply {
    val observer = on.getOnLifecycleObserver<T>()
    observer.registerLazyProperty(this.lazy, receiver)
    this.lifecycleOwner.lifecycle.addObserver(observer)
  }

  /** initializes the lifecycle-aware property lazily. */
  @LazybonesWithNoInlines
  fun lazy() = this.lazy

  /** gets property value from the lazy class. */
  fun value() = this.lazy.value

  override fun toString() = "Lazybones(" +
    "lifecycleOwner=$lifecycleOwner," +
    "lazy=$lazy" +
    ")"
}
