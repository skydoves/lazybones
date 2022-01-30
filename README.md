# Lazybones

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=15"><img alt="API" src="https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://github.com/skydoves/Lazybones/actions/workflows/android.yml"><img alt="Build Status" src="https://github.com/skydoves/Lazybones/actions/workflows/android.yml/badge.svg"/></a>
  <a href="https://github.com/skydoves"><img alt="Profile" src="https://skydoves.github.io/badges/skydoves.svg"/></a>
  <a href="https://androidweekly.net/issues/issue-463"><img alt="Android Weekly" src="https://skydoves.github.io/badges/android-weekly.svg"/></a>
  <a href="https://skydoves.github.io/libraries/lazybones/html/lazybones/com.skydoves.lazybones/index.html"><img alt="Javadoc" src="https://skydoves.github.io/badges/javadoc-lazybones.svg"/></a>
</p>

<p align="center">
😴 Lazy and fluent syntactic sugar of Kotlin for initializing Android lifecycle-aware property.
</p>

<p align="center">
<img src="https://user-images.githubusercontent.com/24237865/72173497-0cb26280-341b-11ea-8d0a-5a000773600f.png" width="734" height="251"/>
</p>

> <p align="center">Ah... I'm a super lazy person. <br>I just want to declare initialization and disposition together. </p>

## Including in your project

[![Maven Central](https://img.shields.io/maven-central/v/com.github.skydoves/lazybones.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.skydoves%22%20AND%20a:%22lazybones%22)

### Gradle 
Add the codes below to your **root** `build.gradle` file (not your module build.gradle file).
```gradle
allprojects {
    repositories {
        mavenCentral()
    }
}
```

And add the dependency below to your **module**'s `build.gradle` file.

```gradle
dependencies {
    implementation "com.github.skydoves:lazybones:1.0.2"
}
```
## SNAPSHOT 
[![Lazybones](https://img.shields.io/static/v1?label=snapshot&message=lazybones&logo=apache%20maven&color=C71A36)](https://oss.sonatype.org/content/repositories/snapshots/com/github/skydoves/lazybones/) <br>
Snapshots of the current development version of Lazybones are available, which track [the latest versions](https://oss.sonatype.org/content/repositories/snapshots/com/github/skydoves/lazybones/).
```Gradle
repositories {
   maven { url 'https://oss.sonatype.org/content/repositories/snapshots/' }
}
```

## Usage
### lifecycleAware
You can initialize a lifecycle-aware object lazily with the `lifecycleAware` extension. The `lifecycleAware` can be used to register & unregister listeners, initailize & clear, show & dismiss, and dispose objects following the lifecycle changes of the `lifecycleOwner`. With the `by` keyword and `lazy()` methhod, you can initialize an object lazily as the following example:

```kotlin
val myDialog: Dialog by lifecycleAware { getDarkThemeDialog(baseContext) }
    .onCreate { this.show() } // show the dialog when the lifecycle's state is onCreate.
    .onDestroy { this.dismiss() } // dismiss the dialog when the lifecycle's state is onDestroy.
    .lazy() // initlize the dialog lazily.
```

In the `onCreate` and `onDestroy` lambda functions, you can omit the `this` keyword. In the following example, the `MediaPlayer` will be initialized and the `start()` will be executed on the `onCreate` state of the lifecycle. The `pause()`, `stop()`, and `release()` methods will be executed following the next lifecycle state.

```kotlin
private val mediaPlayer: MediaPlayer by lifecycleAware {
    MediaPlayer.create(this, R.raw.bgm3)
}.onCreate {
    isLooping = true
    start()
}.onStop {
    pause()
}.onResume {
    start()
}.onDestroy {
    stop()
    release()
}.lazy() // initialize the instance lazily.
```

The code above works the same as the following codes:

```kotlin
private val mediaPlayer: MediaPlayer by lazy { MediaPlayer.create(this, R.raw.bgm3) }

override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  mediaPlayer.isLooping = true
  mediaPlayer.start()
}

override fun onPause() {
  super.onPause()
  mediaPlayer.pause()
}

override fun onStop() {
  super.onStop()
  mediaPlayer.pause()
}

override fun onResume() {
  super.onResume()
  mediaPlayer.start()
}

override fun onDestroy() {
  super.onDestroy()
  mediaPlayer.stop()
  mediaPlayer.release()
}
```

#### CompositeDisposable in RxJava2

You can utilize Lazybones with RxJava as the following example:

```kotlin
val compositeDisposable by lifecycleAware { CompositeDisposable() }
    .onDestroy { dispose() } // call the dispose() method when onDestroy this activity.
    .lazy() // initialize a CompositeDisposable lazily.
```

As the above example, you can initialize and dispose of `CompositeDisposable` on the same method chain of the `lifecycleAware` extension.

#### Lifecycle Relevant Extensions

We can execute lambda functions following lifecycle changes and here are lifecycle relevant methods for the `lifecycleAware` extension.

```kotlin
.onCreate { } // the lambda will be invoked when onCreate.
.onStart { } // the lambda will be invoked when onStart.
.onResume { } // the lambda will be invoked when onResume.
.onPause { } // the lambda will be invoked when onPause.
.onStop { } // the lambda will be invoked when onStop.
.onDestroy { }  // the lambda will be invoked when onDestroy.
.on(On.Create) { } // we can set the lifecycle state manually as an attribute.
```

#### Usages in the non-lifecycle owner class

The `lifecycleAware` is an extension of the `lifecycleOwner`, so you can use it on the `LifecycleOwner` instance as the following:

```kotlin
class SomeClass(lifecycleOwner: LifecycleOwner) {

  private val compositeDisposable by lifecycleOwner.lifecycleAware { CompositeDisposable() }
    .onDestroy { it.dispose() }
    .lazy()

   ...
```

### Initialize Immediately with LifecycleAware

We can also initialize the lifecycle-aware property with the following example:

```kotlin
private val lifecycleAwareProperty = lifecycleAware(CompositeDisposable())
    // observe lifecycle's state and call the dispose() method when onDestroy  
    .observeOnDestroy { dispose() }
```

The `CompositeDisposable` will be initialized immediately, and it will be disposed of on the `observeOnDestroy` scope. Also, you can access the value of the property via the `value` field.

```kotlin
lifecycleAwareProperty.value.add(disposable)
lifecycleAwareProperty.value.dispose()
```

You can observe the lifecycle changes with `observe_` methods.<br>

```kotlin
class MainActivity : AppCompatActivity() {

  private val lifecycleAwareProperty = lifecycleAware(DialogUtil.getDarkTheme())
    .observeOnCreate { show() }
    .observeOnDestroy { dismiss() }
    .observeOnAny { .. }
    .observeOn(On.CREATE) { .. }

    ...
```

You can initialize them with the Kotlin DSL way as the following:

```kotlin
private val lifecycleAwareProperty = lifecycleAware(getDarkThemeDialog())
    .observe {
      onCreate { show() }
      onResume { restart() }
      onDestroy { dismiss() }
    }

```

#### Usages in the non-lifecycle owner class

The `lifecycleAware` is an extension of the `lifecycleOwner`, so you can use it on the `LifecycleOwner` instance as the following:

```kotlin
class SomeClass(lifecycleOwner: LifecycleOwner) {

  private val TAG = MainViewModel::class.java.simpleName
  private val lifecycleAwareProperty = lifecycleOwner.lifecycleAware(Rabbit())

 init {
    this.lifecycleAwareProperty
      .observeOnCreate { Log.d(TAG, "OnCreate: $this") }
      .observeOnStart { Log.d(TAG, "OnStart: $this") }
      .observeOnResume { Log.d(TAG, "OnResume: $this") }
      .observeOnPause { Log.d(TAG, "OnPause: $this") }
      .observeOnStop { Log.d(TAG, "OnStop: $this") }
      .observeOnDestroy { Log.d(TAG, "OnDestroy: $this") }
      .observeOnAny { }
      .observeOn(On.CREATE) { }
  }
  ...
```

## Find this library useful? :heart:
Support it by joining __[stargazers](https://github.com/skydoves/Lazybones/stargazers)__ for this repository. :star:<br>
And __[follow](https://github.com/skydoves)__ me for my next creations! 🤩

# License
```xml
Copyright 2020 skydoves (Jaewoong Eum)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the L
