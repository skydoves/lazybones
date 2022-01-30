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

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModel
import java.io.Closeable

private const val JOB_KEY = "androidx.lifecycle.ViewModelLifecycle.JOB_KEY"

/**
 * [ViewModelLifecycleOwner] tied to this [ViewModel]. The lifecycle owner will be destroyed
 * when ViewModel will be cleared, i.e [ViewModel.onCleared] is called.
 */
public val ViewModel.viewModelLifecycleOwner: ViewModelLifecycleOwner
  get() {
    val getTagMethod = this.javaClass.superclass.getDeclaredMethod("getTag", String::class.java)
    getTagMethod.isAccessible = true
    val lifecycleOwner: ViewModelLifecycleOwner? =
      getTagMethod.invoke(this, JOB_KEY) as? ViewModelLifecycleOwner
    if (lifecycleOwner != null) {
      return lifecycleOwner
    }
    val setTagIfAbsentMethod = this.javaClass.superclass.getDeclaredMethod(
      "setTagIfAbsent",
      String::class.java,
      Any::class.java
    )
    setTagIfAbsentMethod.isAccessible = true
    return setTagIfAbsentMethod.invoke(
      this,
      JOB_KEY,
      CloseableViewModelLifecycleOwner()
    ) as ViewModelLifecycleOwner
  }

/**
 * CloseableViewModelLifecycleOwner represents the lifecycle of a [ViewModel], and implements
 * [Closeable] and [ViewModelLifecycleOwner].
 */
internal class CloseableViewModelLifecycleOwner : Closeable, ViewModelLifecycleOwner {

  private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

  init {
    lifecycleRegistry.currentState = Lifecycle.State.CREATED
    lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
  }

  override fun close() {
    lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  }

  override fun getLifecycle(): Lifecycle {
    return lifecycleRegistry
  }
}
