# Sony QX10 Camera Controller

An advanced Android application for controlling Sony Cyber-shot DSC-QX10 camera via Wi-Fi with Material Design 3 UI.

## Features

### 📹 Camera Controls
- Live View streaming (MJPEG)
- Picture capture
- Manual shutter speed control
- Aperture (F-number) control
- ISO sensitivity control
- Focus mode selection (AF-S, AF-C, Manual Focus)
- Exposure compensation adjustment
- Image stabilization toggle
- Multiple effect modes (Sepia, B&W, Vivid, Neutral)

### 🎨 UI Features
- Material Design 3 interface
- Dark/Light theme support
- Responsive Material Components
- Smooth animations and transitions
- Real-time camera status updates
- Tab-based navigation

## Requirements

- **Android:** 14 (API 34) or higher
- **Camera:** Sony Cyber-shot DSC-QX10
- **Network:** Wi-Fi connection between device and camera
- **Kotlin:** 1.9.0+

## Installation

### From Source

1. Clone the repository:
   ```bash
   git clone https://github.com/mulishboy92-pixel/sony-qx10-camera-controller.git
   cd sony-qx10-camera-controller
   ```

2. Open in Android Studio

3. Build the project:
   ```bash
   ./gradlew build
   ```

4. Connect your Android device and run:
   ```bash
   ./gradlew installDebug
   ```

### APK Installation

After building the release APK:
```bash
./gradlew assembleRelease
```

The APK will be available at: `app/build/outputs/apk/release/app-release.apk`

Transfer the APK to your Android device and install it.

## Usage

1. Connect your Android device to the Sony QX10 Wi-Fi network
2. Launch the application
3. Click "Connect" to establish connection with the camera
4. Use "Live View" tab for real-time preview and picture capture
5. Use "Controls" tab to adjust camera settings

## Architecture

- **Pattern:** MVVM with Jetpack Compose
- **Async:** Kotlin Coroutines
- **Networking:** OkHttp + JSON-RPC
- **UI:** Material Design 3
- **Build:** Gradle (KTS)

## API Reference

This application uses Sony's **Camera Remote API v1.40** for communication.

### Supported Commands

- `startRecMode` - Initialize recording mode
- `startLiveview` - Start live view stream (MJPEG)
- `stopLiveview` - Stop live view
- `actTakePicture` - Capture photograph
- `setShutterSpeed` - Control exposure time
- `setFNumber` - Control aperture value
- `setIsoSpeedRate` - Control ISO sensitivity
- `setFocusMode` - Select focus mode
- `getEvent` - Retrieve camera status

### API Details

- **Protocol:** HTTP/1.1 over Wi-Fi
- **Format:** JSON-RPC 2.0
- **Endpoint:** `http://192.168.122.1:10000/sony/camera`
- **Liveview Stream:** MJPEG format

## Permissions

- `INTERNET` - Network communication with camera
- `CHANGE_NETWORK_STATE` - Wi-Fi management
- `ACCESS_NETWORK_STATE` - Network status
- `READ_EXTERNAL_STORAGE` - Access saved images
- `WRITE_EXTERNAL_STORAGE` - Save captured photos
- `CAMERA` - Camera hardware access

## Build Details

- **Language:** Kotlin
- **Compose Version:** 1.5.4
- **Material 3:** 1.1.2
- **Target SDK:** 34
- **Minimum SDK:** 34

## Dependencies

```gradle
// Core
androidx.core:core-ktx:1.12.0
androidx.appcompat:appcompat:1.6.1

// Compose
androidx.compose.ui:ui:1.5.4
androidx.compose.material3:material3:1.1.2

// Networking
com.squareup.okhttp3:okhttp:4.11.0
com.google.code.gson:gson:2.10.1

// Async
org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3
```

## Troubleshooting

### Connection Issues
1. Ensure camera and device are on the same Wi-Fi network
2. Check if camera is powered on and in recording mode
3. Verify camera IP address (usually 192.168.122.1:10000)

### Build Issues
1. Ensure Java 17+ is installed
2. Update Android Studio to latest version
3. Clear Gradle cache: `./gradlew clean`

## License

Apache License 2.0 - See LICENSE file for details

## Contributing

Contributions are welcome! Please submit pull requests to the main branch.

## Support

For issues, questions, or feature requests, please open an issue on GitHub.

---

**Created:** 2024
**Target Platform:** Android 14+
**Status:** Active Development
