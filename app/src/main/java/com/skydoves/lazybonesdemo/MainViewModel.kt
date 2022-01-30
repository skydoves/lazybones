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

package com.skydoves.lazybonesdemo

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.skydoves.lazybones.On
import com.skydoves.lazybones.lifecycleAware

class MainViewModel(lifecycleOwner: LifecycleOwner) : ViewModel() {

  private val lifecycleAwareProperty = lifecycleOwner.lifecycleAware(Rabbit())

  init {
    this.lifecycleAwareProperty
      .observeOnCreate { Log.d(TAG, "OnCreate: $this") }
      .observeOnStart { Log.d(TAG, "OnStart: $this") }
      .observeOnResume { Log.d(TAG, "OnResume: $this") }
      .observeOnPause { Log.d(TAG, "OnPause: $this") }
      .observeOnStop { Log.d(TAG, "OnStop: $this") }
      .observeOnDestroy { Log.d(TAG, "OnDestroy: $this") }
      .observeOn(On.CREATE) { }
  }

  companion object {
    private val TAG = MainViewModel::class.java.simpleName
  }
}
