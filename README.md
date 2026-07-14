# WasteTimer ⏱️

WasteTimer is a strictly utility-focused Android application designed to track one thing: wasted time. 

The philosophy is simple: start the timer when you catch yourself procrastinating, and stop it when you are back on track. The timer aggregates all wasted time permanently until a manual reset is triggered, helping you visualize and confront productivity leaks.

## Features
- **Quick Settings Tile:** Start and stop tracking instantly from the Android drop-down menu.
- **Floating Overlay:** A minimalist, draggable 20% opacity overlay (34dp) that tracks time over other apps.
- **Persistent Service:** Timer runs in an un-killable Foreground Service.
- **Permanent Ledger:** Every reset is logged to an encrypted Room database. Nothing is deleted unless explicitly chosen.
- **Advanced Analytics:** Dynamic Compose-based charts mapping out average sessions, worst days, and weekly trends.
- **Material 3 UI:** Fully responsive, accessible, dark-mode ready interface.

## Tech Stack
- Kotlin
- Jetpack Compose (Material 3)
- MVVM Architecture + Repository Pattern
- Dagger Hilt (Dependency Injection)
- Room Database
- Coroutines & StateFlow
- GitHub Actions (CI/CD)

## Installation
Go to the **Actions** tab in this repository. Click the latest successful build, and download the `WasteTimer-Release-APK` artifact at the bottom of the page to install it directly on your device.

## License
MIT License
