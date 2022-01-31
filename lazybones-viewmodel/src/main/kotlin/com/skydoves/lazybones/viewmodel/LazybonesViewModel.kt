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

import androidx.lifecycle.ViewModel
import java.io.Serializable

/**
 * @author skydoves (Jaewoong Eum)
 *
 * Returns a [LazybonesViewModel] delegate for initializing lifecycle aware property lazily.
 *
 * @param lazyThreadSafetyMode Specifies how a [Lazy] instance synchronizes initialization among multiple threads.
 * @param initializer A instance initializer for initializing the [T] lazily.
 *
 * @return [LazybonesViewModel] A lazybones wrapper for creating a lifecycle-aware property.
 */
@JvmSynthetic
@LazybonesViewModelWithNoInlines
public inline fun <reified T : Any> ViewModel.lifecycleAware(
  lazyThreadSafetyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
  noinline initializer: () -> T,
): LazybonesViewModel<T> {
  return LazybonesViewModel(viewModelLifecycleOwner, lazy(lazyThreadSafetyMode) { initializer() })
}

/**
 * @author skydoves (Jaewoong Eum)
 *
 * Returns a [ViewModelLifecycleAwareProperty] delegate for initializing lifecycle observable property instantly.
 *
 * @param value The initialization value.
 *
 * @return [ViewModelLifecycleAwareProperty] A wrapper for creating lifecycle observable property.
 */
@JvmSynthetic
public inline fun <reified T : Any> ViewModel.lifecycleAware(
  value: T
): ViewModelLifecycleAwareProperty<T> {
  return ViewModelLifecycleAwareProperty(viewModelLifecycleOwner, value)
}

/**
 * @author skydoves (Jaewoong Eum)
 *
 * [LazybonesViewModel] is a wrapper class that initializes [Lazy] property based on the lifecycle.
 *
 * @property viewModelLifecycleOwner A class that has a ViewModel lifecycle for observing the lifecycle events.
 * @property lazy A lazy initialization for initializing the value [T] lazily.
 */
public class LazybonesViewModel<out T : Any> constructor(
  private val viewModelLifecycleOwner: ViewModelLifecycleOwner,
  private val lazy: Lazy<T>
) : Serializable {

  /**
   * Returns a [LazybonesViewModel] and the receiver will be aware of [OnViewModel.INITIALIZE] lifecycle state.
   *
   * @param receiver A receiver that will be executed based on the lifecycle lazily.
   */
  @JvmSynthetic
  @LazybonesViewModelWithNoInlines
  public fun onInitialize(receiver: T.() -> Unit): LazybonesViewModel<T> =
    addLifecycleObserver(OnViewModel.INITIALIZE, receiver)

  /**
   * Returns a [LazybonesViewModel] and the receiver will be aware of [OnViewModel.CLEAR] lifecycle state.
   *
   * @param receiver A receiver that will be executed based on the lifecycle lazily.
   */
  @JvmSynthetic
  @LazybonesViewModelWithNoInlines
  public fun onClear(receiver: T.() -> Unit): LazybonesViewModel<T> =
    addLifecycleObserver(OnViewModel.CLEAR, receiver)

  /**
   * Adds a lifecycle observer based on [OnViewModel] lifecycle state internally.
   *
   * @param onViewModel ViewModel lifecycle for creating lifecycle-aware properties.
   * @param receiver A receiver that will be executed based on the every lifecycle lazily.
   */
  @JvmSynthetic
  private fun addLifecycleObserver(onViewModel: OnViewModel, receiver: T.() -> Unit) = apply {
    val observer = onViewModel.getOnLifecycleObserver<T>()
    observer.registerLazyProperty(this.lazy, receiver)
    this.viewModelLifecycleOwner.lifecycle.addObserver(observer)
  }

  /** initializes the lifecycle-aware property lazily. */
  @LazybonesViewModelWithNoInlines
  public fun lazy(): Lazy<T> = this.lazy

  /** gets property value from the lazy class. */
  public fun value(): T = this.lazy.value

  override fun toString(): String = "LazybonesViewModel(" +
    "viewModelLifecycleOwner=$viewModelLifecycleOwner," +
    "lazy=$lazy" +
    ")"
}
