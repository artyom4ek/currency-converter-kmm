## Currency Converter Kotlin Multiplatform App

### Features
Works on Android and iOS. Native UIs. Common business logic. Japanese and English (by default) language support.

### Project structure
**Architecture:** Clean Architecture + MVVM + Shared ViewModels<br/>
**UI:** Android _Jetpack Compose_, iOS _SwiftUI_<br/>
**Common libs:**
* Koin
* Ktor
* Serialization
* SqlDelight
* Kotlinx Coroutines
* Kotlinx Datetime
* MOKO Resources
* BigNum
* Multiplatform Settings
* BuildKonfig

### Solved problems
- _iOS app._ `Uncaught Kotlin exception: kotlin.IllegalStateException: There is no event loop. Use runBlocking { ... } to start one.`
- _Network request issue._ `io.ktor.client.call.NoTransformationFoundException: No transformation found: class io.ktor.utils.io.ByteBufferChannel.`
- _Store private key issue._ `buildkonfig` works with errors after project rebuild.
- _iOS app building._ iOS did not find the _shared module_.
- Database. DB tables not generated correctly for both platforms.

### Future improvements
* Add custom theme for Android and iOS.
* Adapt UI for different types of screens.
* Add error display separately for each case.
* Fix the issue where the error is not displayed again on Android.

### Tested on
* Xiaomi Redmi Note 6 Pro
* iPhone 11 Pro
* Android emulators, iOS simulators