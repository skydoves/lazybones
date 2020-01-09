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

import android.content.Context
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation

object BalloonUtils {

  fun getProfileBalloon(baseContext: Context): Balloon {
    return Balloon.Builder(baseContext)
      .setText("You can edit your profile now!")
      .setArrowSize(10)
      .setWidthRatio(0.75f)
      .setHeight(63)
      .setTextSize(15f)
      .setCornerRadius(8f)
      .setTextColorResource(R.color.white_87)
      .setIconDrawableResource(R.drawable.ic_edit)
      .setBackgroundColorResource(R.color.skyBlue)
      .setBalloonAnimation(BalloonAnimation.ELASTIC)
      .build()
  }
}
