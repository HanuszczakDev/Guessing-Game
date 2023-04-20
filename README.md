# Guessing-Game ❓

Gallows game concept, with use of the latest Android technology.

## Technology Used ✨

- Compose
- Navigation
- ViewModel
- ViewModelFactory
- Safe Args

GRADLE KOTLIN / LIBRARY VERSION
```gradle
Kotlin version '1.8.0'
kotlinCompilerExtensionVersion '1.4.1'
compose_version = '1.4.1'
```

SDK VERSION
```gradle
compileSdk 33
minSdk 24
targetSdk 33
```

## Project Milestones

### ViewModel
Application uses GameViewModel and ResultViewModel to separate the business logic from the view.

### Compose
In the latest version of the application classic approach of creating the views with the XML layout files are replaced by the Jetpack Compose UI.

### Navigation Safe-Args
In case of Guessing Game passing between Fragments String argument type with result of the Game.