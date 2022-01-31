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
import androidx.lifecycle.ViewModel
import com.skydoves.lazybones.viewmodel.lifecycleAware
import io.reactivex.rxjava3.disposables.CompositeDisposable

class SecondViewModel : ViewModel() {

  private val viewModelLifecycleAware = lifecycleAware { CompositeDisposable() }
    .onInitialize {
      Log.d(TAG, "viewModel is initialized")
    }.onClear {
      Log.d(TAG, "viewModel is cleared")
      dispose() // dispose CompositeDisposable when viewModel is getting cleared
    }

  companion object {
    private val TAG = SecondViewModel::class.java.simpleName
  }
}
