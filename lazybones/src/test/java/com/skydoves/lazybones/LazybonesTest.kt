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

package com.skydoves.lazybones

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class LazybonesTest {

  @Test
  fun lifecycleAwarePropertyTest() {
    val controller = getController()
    val activity = controller.get()

    controller.resume()
    controller.pause()
    controller.stop()
    controller.destroy()

    activity.lifecycleAware { TestModel() }
      .onCreate {
        assertThat(activity.testModel.tag, `is`("skydoves"))
        assertThat(activity.testModel.number, `is`(20))
        it.apply {
          tag = "onCreate"
          number = 15
        }
      }
      .onResume {
        assertThat(activity.testModel.tag, `is`("onCreate"))
        assertThat(activity.testModel.number, `is`(25))
        it.apply {
          tag = "onResume"
          number = 20
        }
      }
      .onStop {
        assertThat(activity.testModel.tag, `is`("onResume"))
        assertThat(activity.testModel.number, `is`(30))
        it.apply {
          tag = "onStop"
          number = 25
        }
      }
      .onDestroy {
        assertThat(activity.testModel.tag, `is`("onStop"))
        assertThat(activity.testModel.number, `is`(40))
      }
  }

  @Test
  fun lazybonesOnLifecycleChangedTest() {
    val controller = getController()
    val activity = controller.get()

    assertThat(activity.testModel.tag, `is`("skydoves"))
    assertThat(activity.testModel.number, `is`(20))

    controller.resume()
    assertThat(activity.testModel.number, `is`(25))

    controller.stop()
    assertThat(activity.testModel.number, `is`(30))

    controller.pause()
    controller.destroy()
    assertThat(activity.testModel.tag, `is`("skydoves"))
    assertThat(activity.testModel.number, `is`(40))
  }

  fun getController(): ActivityController<UnitTestActivity> =
    Robolectric.buildActivity(UnitTestActivity::class.java).create().start()
}
