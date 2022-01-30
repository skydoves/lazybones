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

/** creates a [LifecycleAwareProperty] by [LifecycleAwareProperty.Builder] using dsl. */
@JvmSynthetic
@LazybonesWithNoInlines
public inline fun <T : Any> LifecycleAwareProperty<T>.observe(
  block: LifecycleAwareProperty.Builder<T>.() -> Unit
): LifecycleAwareProperty<T> =
  LifecycleAwareProperty.Builder(this.lifecycleOwner, this.value).apply(block).build()

/** LifecycleAwareProperty is an observer for notifying lifecycle is changed. */
public class LifecycleAwareProperty<T : Any> constructor(
  public val lifecycleOwner: LifecycleOwner,
  public var value: T
) {

  /** observes on the [On] lifecycle state. */
  @JvmSynthetic
  @LazybonesWithNoInlines
  public fun observeOn(on: On, receiver: T.() -> Unit): LifecycleAwareProperty<T> = apply {
    addLifecycleObserver(on, receiver)
  }

  /** observes on the [On.CREATE] lifecycle state. */
  @JvmSynthetic
  @LazybonesWithNoInlines
  public fun observeOnCreate(receiver: T.() -> Unit): LifecycleAwareProperty<T> = apply {
    addLifecycleObserver(On.CREATE, receiver)
  }

  /** observes on the [On.START] lifecycle state. */
  @JvmSynthetic
  @LazybonesWithNoInlines
  public fun observeOnStart(receiver: T.() -> Unit): LifecycleAwareProperty<T> = apply {
    addLifecycleObserver(On.START, receiver)
  }

  /** observes on the [On.RESUME] lifecycle state. */
  @JvmSynthetic
  @LazybonesWithNoInlines
  public fun observeOnResume(receiver: T.() -> Unit): LifecycleAwareProperty<T> = apply {
    addLifecycleObserver(On.RESUME, receiver)
  }

  /** observes on the [On.PAUSE] lifecycle state. */
  @JvmSynthetic
  @LazybonesWithNoInlines
  public fun observeOnPause(receiver: T.() -> Unit): LifecycleAwareProperty<T> = apply {
    addLifecycleObserver(On.PAUSE, receiver)
  }

  /** observes on the [On.STOP] lifecycle state. */
  @JvmSynthetic
  @LazybonesWithNoInlines
  public fun observeOnStop(receiver: T.() -> Unit): LifecycleAwareProperty<T> = apply {
    addLifecycleObserver(On.STOP, receiver)
  }

  /** observes on the [On.DESTROY] lifecycle state. */
  @JvmSynthetic
  @LazybonesWithNoInlines
  public fun observeOnDestroy(receiver: T.() -> Unit): LifecycleAwareProperty<T> = apply {
    addLifecycleObserver(On.DESTROY, receiver)
  }

  /** adds a lifecycle observer based on [On] lifecycle state internally. */
  @JvmSynthetic
  private fun addLifecycleObserver(on: On, receiver: T.() -> Unit) {
    val observer = on.getOnLifecyclePropertyObserver(this.value)
    observer.registerLifecyclePropertyObserver { value -> receiver(value) }
    this.lifecycleOwner.lifecycle.addObserver(observer)
  }

  /** Builder class for creating [LifecycleAwareProperty]. */
  public class Builder<T : Any>(lifecycleOwner: LifecycleOwner, value: T) {
    private val lifecycleAwareProperty = LifecycleAwareProperty(lifecycleOwner, value)

    /** observes on the [On] lifecycle state. */
    @JvmSynthetic
    @LazybonesWithNoInlines
    public fun on(on: On, receiver: T.() -> Unit): Builder<T> = apply {
      this.lifecycleAwareProperty.addLifecycleObserver(on, receiver)
    }

    /** observes on the [On.CREATE] lifecycle state. */
    @JvmSynthetic
    @LazybonesWithNoInlines
    public fun onCreate(receiver: T.() -> Unit): Builder<T> = apply {
      this.lifecycleAwareProperty.addLifecycleObserver(On.CREATE, receiver)
    }

    /** observes on the [On.START] lifecycle state. */
    @JvmSynthetic
    @LazybonesWithNoInlines
    public fun onStart(receiver: T.() -> Unit): Builder<T> = apply {
      this.lifecycleAwareProperty.addLifecycleObserver(On.START, receiver)
    }

    /** observes on the [On.RESUME] lifecycle state. */
    @JvmSynthetic
    @LazybonesWithNoInlines
    public fun onResume(receiver: T.() -> Unit): Builder<T> = apply {
      this.lifecycleAwareProperty.addLifecycleObserver(On.RESUME, receiver)
    }

    /** observes on the [On.PAUSE] lifecycle state. */
    @LazybonesWithNoInlines
    @JvmSynthetic
    public fun onPause(receiver: T.() -> Unit): Builder<T> = apply {
      this.lifecycleAwareProperty.addLifecycleObserver(On.PAUSE, receiver)
    }

    /** observes on the [On.STOP] lifecycle state. */
    @LazybonesWithNoInlines
    @JvmSynthetic
    public fun onStop(receiver: T.() -> Unit): Builder<T> = apply {
      this.lifecycleAwareProperty.addLifecycleObserver(On.STOP, receiver)
    }

    /** observes on the [On.DESTROY] lifecycle state. */
    @LazybonesWithNoInlines
    @JvmSynthetic
    public fun onDestroy(receiver: T.() -> Unit): Builder<T> = apply {
      this.lifecycleAwareProperty.addLifecycleObserver(On.DESTROY, receiver)
    }

    /** returns a new instance of [LifecycleAwareProperty]. */
    public fun build(): LifecycleAwareProperty<T> = this.lifecycleAwareProperty
  }
}
