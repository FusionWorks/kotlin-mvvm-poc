# Android MVVM Architecture with Kotlin

This project demonstrates modern Android development using MVVM architecture pattern with Kotlin, migrated from the original MVP Java implementation.

## 🏗️ Architecture

- **MVVM Architecture**: Model-View-ViewModel pattern with Repository pattern
- **Language**: 100% Kotlin
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)

## 🛠️ Tech Stack

### Core
- **Kotlin**: Primary programming language
- **Coroutines**: For asynchronous programming
- **Flow**: For reactive programming
- **Hilt**: Dependency injection
- **ViewBinding**: Type-safe view references

### Architecture Components
- **ViewModel**: UI-related data holder with lifecycle awareness
- **LiveData & StateFlow**: Observable data holder classes
- **Room**: Database abstraction layer
- **Repository Pattern**: Single source of truth for data

### Networking
- **Retrofit**: HTTP client for API calls
- **OkHttp**: HTTP & HTTP/2 client
- **Moshi**: JSON library for Kotlin

### UI
- **Material Design Components**: Modern UI components
- **RecyclerView**: List implementation with ListAdapter
- **ViewPager2**: Modern paging implementation
- **Navigation Component**: App navigation

### Testing
- **JUnit**: Unit testing framework
- **Espresso**: UI testing framework
- **Mockito**: Mocking framework

### Build System
- **Gradle Version Catalogs**: Centralized dependency management
- **Kotlin DSL**: Modern build script syntax

## 📦 Module Structure

```
app/
├── src/main/kotlin/com/mindorks/framework/mvvm/
│   ├── data/
│   │   ├── local/
│   │   │   ├── database/        # Room database implementation
│   │   │   └── prefs/           # SharedPreferences wrapper
│   │   ├── network/             # API interfaces and implementations
│   │   ├── repository/          # Data repository implementations
│   │   └── model/               # Data models
│   ├── ui/
│   │   ├── base/                # Base classes for Activities/Fragments/ViewModels
│   │   ├── main/                # Main screen with question cards
│   │   ├── login/               # Login functionality
│   │   ├── splash/              # Splash screen
│   │   └── feed/                # Feed screens (blogs/open source)
│   ├── utils/                   # Utility classes
│   └── MvvmApp.kt              # Application class
├── src/debug/                   # Debug-only code (Flipper, database inspection)
└── src/test/                    # Unit tests
```

## 🚀 Key Features

### Modern Android Development
- **Kotlin Coroutines**: Replaces RxJava for simpler async code
- **Room Database**: Replaces GreenDAO with better Kotlin support
- **Modern Networking**: Retrofit + Coroutines for API calls
- **Type Safety**: Leverages Kotlin's type system for safer code

### Legacy Library Replacements
- ✅ **AndroidNetworking** → **Retrofit + OkHttp**
- ✅ **Calligraphy** → **Native Font Resources**
- ✅ **PlaceHolderView** → **RecyclerView + ListAdapter**
- ✅ **Debug-DB** → **Flipper** (debug builds)
- ✅ **RxJava** → **Kotlin Coroutines + Flow**

### Enhanced Features
- **Swipe Cards**: Custom swipe implementation for question cards
- **Modern Adapters**: ListAdapter with DiffUtil for efficient updates
- **Network Error Handling**: Comprehensive error handling with sealed classes
- **Debug Tools**: Flipper integration for debugging (debug builds only)

## 🔧 Setup

### Prerequisites
- Android Studio Arctic Fox or later
- JDK 17 or later
- Android SDK 34

### Getting Started
1. Clone the repository
```bash
git clone https://github.com/your-username/android-mvvm-architecture.git
```

2. Open the project in Android Studio

3. Sync project with Gradle files

4. Run the app

### Build Configuration
The project uses Gradle Version Catalogs for dependency management. All dependencies are defined in `gradle/libs.versions.toml`.

## 🧪 Testing

### Unit Tests
```bash
./gradlew test
```

### Instrumentation Tests
```bash
./gradlew connectedAndroidTest
```

## 🐛 Debug Tools

### Debug Builds
Debug builds include additional tools:
- **Flipper**: Network and database inspection
- **LeakCanary**: Memory leak detection
- **Debug Activity**: Database inspection and export

### Access Debug Menu
In debug builds, you can access debug tools through the navigation drawer or by directly starting the `DebugActivity`.

## 🏗️ Migration from MVP

This project was migrated from MVP (Java) to MVVM (Kotlin). Key migration points:

### Architecture Changes
- **Presenters** → **ViewModels**
- **View Interfaces** → **LiveData/StateFlow observers**
- **Manual lifecycle management** → **Automatic lifecycle awareness**

### Technology Updates
- **Java** → **Kotlin**
- **RxJava** → **Coroutines + Flow**
- **GreenDAO** → **Room**
- **Legacy networking** → **Modern Retrofit**

### Code Quality Improvements
- Type safety with Kotlin
- Null safety
- Coroutines for cleaner async code
- Modern Android Architecture Components

## 📚 Learning Resources

- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
- [Kotlin Coroutines Guide](https://kotlinlang.org/docs/coroutines-guide.html)
- [Room Database Guide](https://developer.android.com/training/data-storage/room)
- [Hilt Dependency Injection](https://developer.android.com/training/dependency-injection/hilt-android)

## 📄 License

```
Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://mindorks.com/license/apache-v2

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📞 Support

For questions and support, please visit:
- [Mindorks Blog](https://blog.mindorks.com)
- [Mindorks GitHub](https://github.com/MindorksOpenSource)

---

**Happy Coding!** 🎉