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

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/** A lifecycle observer for performing receiver after the lifecycle state is [OnViewModel.INITIALIZE]. */
internal class OnInitializeObserver<T : Any> : OnViewModelLifecycleObserver<T>() {
  override fun onCreate(owner: LifecycleOwner) {
    super.initialize()
  }
}

/** A lifecycle observer for performing receiver after the lifecycle state is [OnViewModel.CLEAR]. */
internal class OnClearObserver<T : Any> : OnViewModelLifecycleObserver<T>() {
  override fun onDestroy(owner: LifecycleOwner) {
    super.initialize()
  }
}

/** An abstract observer for delegating lazy and receiver. */
internal abstract class OnViewModelLifecycleObserver<T : Any> : DefaultLifecycleObserver {

  private lateinit var lazy: Lazy<T>
  private lateinit var receiver: (T.() -> Unit)

  fun registerLazyProperty(lazy: Lazy<T>, receiver: T.() -> Unit) {
    this.lazy = lazy
    this.receiver = receiver
  }

  fun initialize() = this.receiver(this.lazy.value)
}
