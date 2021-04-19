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

@file:JvmName("LazybonesCoroutines")
@file:JvmMultifileClass

package com.skydoves.lazybones

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.addRepeatingJob
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

/** returns a [Job] lazily via [Lazybones] delegate for invoking the [block] on the onStarted [lifecycleScope]. */
@JvmSynthetic
@LazybonesWithNoInlines
inline fun LifecycleOwner.launchOnStarted(
  lazyThreadSafetyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
  crossinline block: suspend CoroutineScope.() -> Unit
): Lazybones<Job> {
  val job = lifecycleScope.launchWhenStarted { block() }
  return Lazybones(this, lazy(lazyThreadSafetyMode) { job })
}

/**
 * Returns a [Job] lazily via [Lazybones] delegate for invoking the collecting [block] from the [flow]
 * on the onStarted [lifecycleScope].
 */
@JvmSynthetic
@LazybonesWithNoInlines
inline fun <T> LifecycleOwner.launchOnStarted(
  flow: Flow<T>,
  lazyThreadSafetyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
  crossinline block: (T) -> Unit
): Lazybones<Job> {
  val job = lifecycleScope.launchWhenStarted { flow.collect { block(it) } }
  return Lazybones(this, lazy(lazyThreadSafetyMode) { job })
}

/** returns a [Job] lazily via [Lazybones] delegate for invoking the [block] on the onCreated [lifecycleScope]. */
@JvmSynthetic
@LazybonesWithNoInlines
inline fun LifecycleOwner.launchOnCreated(
  lazyThreadSafetyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
  crossinline block: suspend CoroutineScope.() -> Unit
): Lazybones<Job> {
  val job = lifecycleScope.launchWhenCreated { block() }
  return Lazybones(this, lazy(lazyThreadSafetyMode) { job })
}

/**
 * Returns a [Job] lazily via [Lazybones] delegate for invoking the collecting [block] from the [flow]
 * on the onCreated [lifecycleScope].
 */
@JvmSynthetic
@LazybonesWithNoInlines
inline fun <T> LifecycleOwner.launchOnCreated(
  flow: Flow<T>,
  lazyThreadSafetyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
  crossinline block: (T) -> Unit
): Lazybones<Job> {
  val job = lifecycleScope.launchWhenCreated { flow.collect { block(it) } }
  return Lazybones(this, lazy(lazyThreadSafetyMode) { job })
}

/** returns a [Job] lazily via [Lazybones] delegate for invoking the [block] on the onResume [lifecycleScope]. */
@JvmSynthetic
@LazybonesWithNoInlines
inline fun LifecycleOwner.launchOnResume(
  lazyThreadSafetyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
  crossinline block: suspend CoroutineScope.() -> Unit
): Lazybones<Job> {
  val job = lifecycleScope.launchWhenResumed { block() }
  return Lazybones(this, lazy(lazyThreadSafetyMode) { job })
}

/**
 * Returns a [Job] lazily via [Lazybones] delegate for invoking the collecting [block] from the [flow]
 * on the onResume [lifecycleScope].
 */
@JvmSynthetic
@LazybonesWithNoInlines
inline fun <T> LifecycleOwner.launchOnResume(
  flow: Flow<T>,
  lazyThreadSafetyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
  crossinline block: (T) -> Unit
): Lazybones<Job> {
  val job = lifecycleScope.launchWhenResumed { flow.collect { block(it) } }
  return Lazybones(this, lazy(lazyThreadSafetyMode) { job })
}

/**
 * Collects from the flow when the lifecycle is at least [Lifecycle.State.STARTED] and
 *  STOPS the collection when it's STOPPED.
 * It automatically restarts collecting when the lifecycle is [Lifecycle.State.STARTED] again.
 */
@JvmSynthetic
@LazybonesWithNoInlines
inline fun LifecycleOwner.addOnRepeatingJob(
  state: Lifecycle.State,
  lazyThreadSafetyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
  crossinline block: suspend CoroutineScope.() -> Unit
): Lazybones<Job> {
  val job = addRepeatingJob(state = state) { block() }
  return Lazybones(this, lazy(lazyThreadSafetyMode) { job })
}

/**
 * Collects from the flow when the lifecycle is at least [Lifecycle.State.STARTED] and
 *  STOPS the collection when it's STOPPED.
 * It automatically restarts collecting when the lifecycle is [Lifecycle.State.STARTED] again.
 */
@JvmSynthetic
@LazybonesWithNoInlines
inline fun <T> LifecycleOwner.addOnRepeatingJob(
  state: Lifecycle.State,
  flow: Flow<T>,
  lazyThreadSafetyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
  crossinline block: (T) -> Unit
): Lazybones<Job> {
  val job = addRepeatingJob(state = state) { flow.collect { block(it) } }
  return Lazybones(this, lazy(lazyThreadSafetyMode) { job })
}
