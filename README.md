# Lazybones

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=15"><img alt="API" src="https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://travis-ci.com/skydoves/Lazybones"><img alt="Build Status" src="https://travis-ci.com/skydoves/Lazybones.svg?branch=master"/></a>
   <a href="https://skydoves.github.io/libraries/lazybones/javadoc/lazybones/com.skydoves.lazybones/index.html"><img alt="Javadoc" src="https://img.shields.io/badge/Javadoc-Lazybones-yellow"/></a>
</p>

<p align="center">
😴 A super lazy and fluent Kotlin expression for initializing lifecycle-aware property.
</p>

<p align="center">
<img src="https://user-images.githubusercontent.com/24237865/72173497-0cb26280-341b-11ea-8d0a-5a000773600f.png" width="734" height="251"/>
</p>

> <p align="center">Ah... I'm a super lazy person. <br>I just want to initialize and declare of disposing it at the same time. </p>

## Including in your project
[![Download](https://api.bintray.com/packages/devmagician/maven/lazybones/images/download.svg) ](https://bintray.com/devmagician/maven/lazybones/_latestVersion)
[![Jitpack](https://jitpack.io/v/skydoves/Lazybones.svg)](https://jitpack.io/#skydoves/Lazybones)
### Gradle 
Add below codes to your **root** `build.gradle` file (not your module build.gradle file).
```gradle
allprojects {
    repositories {
        jcenter()
    }
}
```
And add a dependency code to your **module**'s `build.gradle` file.
```gradle
dependencies {
    implementation "com.github.skydoves:lazybones:1.0.1"
}
```

## Usage
### lifecycleAware
We can initialize a lifecycle-aware object lazily using the `lifecycleAware` keyword. <br>
The `lifecycleAware` functionality can be used to register & unregister listeners, clear something,<br>show & dismiss and dispose of disposable objects as lifecycle changes by lifecycle owner(Activity, Fragment).<br>
If we want to initialize an object lazily, we should use it with `by` keyword and  `lazy()` method.
```kotlin
val myDialog: Dialog by lifecycleAware { getDarkThemeDialog(baseContext) }
    .onCreate { this.show() } // show the dialog when the lifecycle's state is onCreate.
    .onDestroy { this.dismiss() } // dismiss the dialog when the lifecycle's state is onDestroy.
    .lazy() // initlize the dialog lazily.
```
In the `onCreate` and `onDestroy` lambda function, we can omit the `this` keyword.<br>
So we can use like below.
```kotlin
val myDialog: Dialog by lifecycleAware { getDarkThemeDialog(baseContext) }
    .onCreate { show() } // show the dialog when the lifecycle's state is onCreate.
    .onDestroy { dismiss() } // dismiss the dialog when the lifecycle's state is onDestroy.
    .lazy() // initlize the dialog lazily.
```

#### CompositeDisposable in RxJava2
Here is an example of `CompositeDisposable` in RxJava2. <br>
At the same time as initializing lazily the CompositeDisposable, the `dispose()` method will be <br>invoked automatically when onDestroy.

```kotlin
val compositeDisposable by lifecycleAware { CompositeDisposable() }
    .onDestroy { dispose() } // call the dispose() method when onDestroy this activity.
    .lazy() // initialize a CompositeDisposable lazily.
```
#### Lifecycle related methods
We can invoke lambda functions as lifecycle changes and here are eight lifecycle-related methods of `lifecycleAware`.
```kotlin
.onCreate { } // the lambda will be invoked when onCreate.
.onStart { } // the lambda will be invoked when onStart.
.onResume { } // the lambda will be invoked when onResume.
.onPause { } // the lambda will be invoked when onPause.
.onStop { } // the lambda will be invoked when onStop.
.onDestroy { }  // the lambda will be invoked when onDestroy.
.onAny { } // the lambda will be invoked whenever the lifecycle state is changed.
.on(On.Create) { } // we can set the lifecycle state manually as an attribute.
```

#### Usages in the non-lifecycle owner class
The `lifecycleAware` is an extension of `lifecycleOwner` so it can be used on non- lifecycle-owner classes.
```kotlin
class MainViewModel(lifecycleOwner: LifecycleOwner) : ViewModel() {

  private val compositeDisposable by lifecycleOwner.lifecycleAware { CompositeDisposable() }
    .onDestroy { it.dispose() }
    .lazy()

   ...
```

### LifecycleAwareProperty
If we don't need to initialize lazily, here is a more simple way.<br>
We can declare a `LifecycleAwareProperty` using the `lifecycleAware` keyword.<br> The attribute value will not be initialized lazily. so we don't need to use it with `by` keyword and `lazy()` method.<br>
```kotlin
private val lifecycleAwareProperty = lifecycleAware(CompositeDisposable())
    // observe lifecycle's state and call the dispose() method when onDestroy  
    .observeOnDestroy { dispose() }
```
And we can access the original property via the `value` field.
```kotlin
lifecycleAwareProperty.value.add(disposable)
lifecycleAwareProperty.value.dispose()
```

We can observe the lifecycle changes using `observe_` method.<br>
```kotlin
class MainActivity : AppCompatActivity() {

  private val lifecycleAwareProperty = lifecycleAware(DialogUtil.getDarkTheme())
    .observeOnCreate { show() }
    .observeOnDestroy { dismiss() }
    .observeOnAny { .. }
    .observeOn(On.CREATE) { .. }

    ...
```
Here is the kotlin dsl way.
```kotlin
private val lifecycleAwareProperty = lifecycleAware(getDarkThemeDialog())
    .observe {
      onCreate { show() }
      onResume { restart() }
      onDestroy { dismiss() }
    }
```

#### Using in the non-lifecycle owner class
The `lifecycleAware` is an extension of `lifecycleOwner` so it can be used on non- lifecycle-owner classes.
```kotlin
class MainViewModel(lifecycleOwner: LifecycleOwner) : ViewModel() {

  private val TAG = MainViewModel::class.java.simpleName
  private val lifecycleAwareProperty = lifecycleOwner.lifecycleAware(Rabbit())

  init {
    this.lifecycleAwareProperty
      .observeOnCreate { Log.d(TAG, "OnCreate: $it") }
      .observeOnStart { Log.d(TAG, "OnStart: $it") }
      .observeOnResume { Log.d(TAG, "OnResume: $it") }
      .observeOnPause { Log.d(TAG, "OnPause: $it") }
      .observeOnStop { Log.d(TAG, "OnStop: $it") }
      .observeOnDestroy { Log.d(TAG, "OnDestroy: $it") }
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
