# movix
Represents Popular TV Shows 

![](https://media.giphy.com/media/ZBhputFKV5Pe81EIlK/giphy.gif)

# Table of Contents

1. [Gradle Dependency]
   1. [Repository]
   2. [Dependency]
2. [Basic Usage]
   1. [General Purpose]   
   # Gradle Dependency


#### Repository

```gradle
allprojects {
    repositories {
        google()
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```

#### Dependency

Add this to your module's `build.gradle` file:

```gradle
dependencies {
	...
	    implementation 'androidx.appcompat:appcompat:1.1.0'
	    // Support Lib
	    implementation 'com.google.android.material:material:1.1.0-alpha10'
	    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
	    implementation 'com.squareup.picasso:picasso:2.5.2'
	    // Navigation
	    implementation 'androidx.navigation:navigation-fragment:2.1.0-beta01'
	    implementation 'androidx.navigation:navigation-ui:2.1.0-beta01'
	    // HTTP Lib
	    implementation 'com.squareup.retrofit2:retrofit:2.5.0'
	    implementation 'com.squareup.retrofit2:converter-gson:2.5.0'
	    implementation 'com.squareup.retrofit2:converter-scalars:2.5.0'
	    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.2.0'
	    implementation 'com.squareup.okhttp3:logging-interceptor:3.9.0'
	    implementation 'io.gsonfire:gson-fire:1.8.3'
	    // DI Lib
	    implementation 'com.google.dagger:dagger-android:2.21'
	    implementation 'com.google.dagger:dagger-android-support:2.21'

	    annotationProcessor 'com.google.dagger:dagger-android-processor:2.23.2'
	    annotationProcessor 'com.google.dagger:dagger-compiler:2.23.2'
	    // Jetpack
	    // Room components
	    implementation 'androidx.room:room-runtime:2.1.0'
	    implementation 'androidx.room:room-rxjava2:2.1.0'
	    annotationProcessor 'androidx.room:room-compiler:2.1.0'
	    androidTestImplementation 'androidx.room:room-testing:2.1.0'
	    debugImplementation 'com.amitshekhar.android:debug-db:1.0.6'
	    // Lifecycle components
	    implementation "androidx.lifecycle:lifecycle-extensions:2.2.0-alpha02"
	    annotationProcessor "androidx.lifecycle:lifecycle-compiler:2.2.0-alpha02"
	    // RX Java
	    implementation "io.reactivex.rxjava2:rxjava:2.1.9"
	    implementation "io.reactivex.rxjava2:rxandroid:2.0.2"

	    implementation 'com.github.jd-alexander:LikeButton:0.2.3'

	    testImplementation 'junit:junit:4.12'
	    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
	    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
	    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"

}
```

---
