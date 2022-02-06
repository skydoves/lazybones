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

package com.skydoves.lazybones.viewmodel

import com.skydoves.viewmodel.lifecycle.ViewModelLifecycleOwner

/** creates a [ViewModelLifecycleAwareProperty] by [ViewModelLifecycleAwareProperty.Builder] using dsl. */
@JvmSynthetic
@LazybonesViewModelWithNoInlines
public inline fun <T : Any> ViewModelLifecycleAwareProperty<T>.observe(
  block: ViewModelLifecycleAwareProperty.Builder<T>.() -> Unit
): ViewModelLifecycleAwareProperty<T> =
  ViewModelLifecycleAwareProperty.Builder(this.viewModelLifecycleOwner, this.value).apply(block)
    .build()

/** LifecycleAwareProperty is an observer for notifying lifecycle is changed. */
public class ViewModelLifecycleAwareProperty<T : Any> constructor(
  public val viewModelLifecycleOwner: ViewModelLifecycleOwner,
  public var value: T
) {

  /** observes on the [OnViewModel] lifecycle state. */
  @JvmSynthetic
  @LazybonesViewModelWithNoInlines
  public fun observeOn(
    onViewModel: OnViewModel,
    receiver: T.() -> Unit
  ): ViewModelLifecycleAwareProperty<T> = apply {
    addLifecycleObserver(onViewModel, receiver)
  }

  /** observes on the [OnViewModel.INITIALIZE] lifecycle state. */
  @JvmSynthetic
  @LazybonesViewModelWithNoInlines
  public fun observeOnInitialize(receiver: T.() -> Unit): ViewModelLifecycleAwareProperty<T> =
    apply {
      addLifecycleObserver(OnViewModel.INITIALIZE, receiver)
    }

  /** observes on the [OnViewModel.CLEAR] lifecycle state. */
  @JvmSynthetic
  @LazybonesViewModelWithNoInlines
  public fun observeOnClear(receiver: T.() -> Unit): ViewModelLifecycleAwareProperty<T> =
    apply {
      addLifecycleObserver(OnViewModel.CLEAR, receiver)
    }

  /** adds a lifecycle observer based on [OnViewModel] lifecycle state internally. */
  @JvmSynthetic
  private fun addLifecycleObserver(onViewModel: OnViewModel, receiver: T.() -> Unit) {
    val observer = onViewModel.getOnLifecyclePropertyObserver(this.value)
    observer.registerLifecyclePropertyObserver { value -> receiver(value) }
    this.viewModelLifecycleOwner.lifecycle.addObserver(observer)
  }

  /** Builder class for creating [ViewModelLifecycleAwareProperty]. */
  public class Builder<T : Any>(viewModelLifecycleOwner: ViewModelLifecycleOwner, value: T) {
    private val lifecycleAwareProperty =
      ViewModelLifecycleAwareProperty(viewModelLifecycleOwner, value)

    /** observes on the [OnViewModel] lifecycle state. */
    @JvmSynthetic
    @LazybonesViewModelWithNoInlines
    public fun on(
      onViewModel: OnViewModel,
      receiver: T.() -> Unit
    ): Builder<T> =
      apply {
        this.lifecycleAwareProperty.addLifecycleObserver(onViewModel, receiver)
      }

    /** observes on the [OnViewModel.INITIALIZE] lifecycle state. */
    @JvmSynthetic
    @LazybonesViewModelWithNoInlines
    public fun onInitialize(receiver: T.() -> Unit): Builder<T> =
      apply {
        this.lifecycleAwareProperty.addLifecycleObserver(OnViewModel.INITIALIZE, receiver)
      }

    /** observes on the [OnViewModel.CLEAR] lifecycle state. */
    @JvmSynthetic
    @LazybonesViewModelWithNoInlines
    public fun onClear(receiver: T.() -> Unit): Builder<T> =
      apply {
        this.lifecycleAwareProperty.addLifecycleObserver(OnViewModel.CLEAR, receiver)
      }

    /** returns a new instance of [ViewModelLifecycleAwareProperty]. */
    public fun build(): ViewModelLifecycleAwareProperty<T> = this.lifecycleAwareProperty
  }
}
