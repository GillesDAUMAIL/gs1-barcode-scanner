# GS1-128 Barcode Scanner for Android

A native Android application designed for Samsung Galaxy A54 (Android 14, One UI 6.1) to scan and decode GS1-128 barcodes.

## Features

### Core Functionality
- **GS1-128 Barcode Scanning**: Uses CameraX and ML Kit for real-time barcode detection
- **GS1 Data Parsing**: Supports GTIN (01), Lot Number (10), and Expiration Date (17) Application Identifiers
- **Material Design 3 UI**: Optimized for Samsung One UI 6.1 with dark/light theme support
- **Camera Permissions**: Automatic permission handling with user-friendly prompts

### Technical Architecture
- **Language**: Kotlin with Coroutines
- **UI Framework**: Jetpack Compose + Material Design 3
- **Camera**: CameraX with ML Kit Barcode Scanning
- **Architecture**: MVVM with StateFlow and Hilt DI
- **Navigation**: Navigation Compose
- **Testing**: JUnit with MockK

## Project Structure

```
app/src/main/java/com/gillesdaumail/gs1scanner/
├── ui/
│   ├── theme/          # Material Design 3 theme
│   ├── scanner/        # Scanner screen & ViewModel
│   ├── result/         # Result display screen
│   └── components/     # Reusable UI components
├── domain/
│   ├── model/          # Domain models (GS1Data, ScanResult)
│   ├── parser/         # GS1-128 parser implementation
│   └── usecase/        # Business logic use cases
├── camera/             # CameraX + ML Kit integration
└── di/                 # Hilt dependency injection
```

## Building the Application

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- Android SDK 34
- JDK 17 or later
- Gradle 8.7+

### Build Steps

1. **Clone the repository**:
   ```bash
   git clone https://github.com/GillesDAUMAIL/gs1-barcode-scanner.git
   cd gs1-barcode-scanner
   ```

2. **Open in Android Studio**:
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory

3. **Sync Project**:
   - Click "Sync Now" when prompted
   - Wait for Gradle sync to complete

4. **Build the project**:
   ```bash
   ./gradlew build
   ```

5. **Run tests**:
   ```bash
   ./gradlew test
   ```

### Building APK

#### Debug APK
```bash
./gradlew assembleDebug
```
Output: `app/build/outputs/apk/debug/app-debug.apk`

#### Release APK
```bash
./gradlew assembleRelease
```
Output: `app/build/outputs/apk/release/app-release.apk`

## Installation

### Development Installation
1. Enable USB Debugging on your Samsung Galaxy A54
2. Connect device via USB
3. Run from Android Studio or use:
   ```bash
   ./gradlew installDebug
   ```

### Production Installation
1. Transfer the signed APK to your device
2. Enable "Install unknown apps" for your file manager
3. Tap the APK file to install

## Usage

1. **Launch the app**: Tap the GS1 Scanner icon
2. **Grant camera permission**: Allow camera access when prompted
3. **Scan barcode**: Point camera at GS1-128 barcode within the guide frame
4. **View results**: Automatically navigates to result screen showing parsed data
5. **Scan again**: Tap "Scan Again" button to scan another barcode

## GS1-128 Support

The app supports the following Application Identifiers (AI):

- **GTIN (01)**: Global Trade Item Number (14 digits)
- **Lot Number (10)**: Batch/Lot Number (variable length)
- **Expiration Date (17)**: Use By Date (YYMMDD format, displayed as DD/MM/YYYY)

### Example Barcode Data
```
01123456789012341017241231 → 
GTIN: 12345678901234
Expiration: 31/12/2024
```

## Testing

### Unit Tests
- **GS1ParserTest**: Tests parsing logic for various GS1 formats
- **ProcessBarcodeUseCaseTest**: Tests business logic validation

Run tests:
```bash
./gradlew test
```

### Integration Tests
```bash
./gradlew connectedAndroidTest
```

## Samsung Galaxy A54 Optimization

- **Camera Performance**: Optimized for Exynos 1380 processor
- **One UI Compatibility**: Colors and spacing adapted for One UI 6.1
- **Memory Management**: Efficient resource usage to prevent leaks
- **Battery Optimization**: Camera usage optimized for power efficiency

## Dependencies

### Core Dependencies
- Jetpack Compose BOM 2024.06.00
- CameraX 1.3.4
- ML Kit Barcode Scanning 17.2.0
- Hilt 2.48
- Navigation Compose 2.7.7
- Accompanist Permissions 0.32.0

### Testing Dependencies
- JUnit 4.13.2
- Mockito 5.5.0
- Mockito Kotlin 5.1.0
- Coroutines Test 1.7.3

## Troubleshooting

### Common Issues

1. **Camera permission denied**:
   - Go to Settings → Apps → GS1 Scanner → Permissions
   - Enable Camera permission

2. **Barcode not detected**:
   - Ensure good lighting conditions
   - Hold device steady
   - Verify it's a GS1-128 barcode (Code 128 format)

3. **Build errors**:
   - Ensure Android SDK 34 is installed
   - Check internet connection for dependency downloads
   - Clean and rebuild: `./gradlew clean build`

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Technical Support

For technical issues, please create an issue in the GitHub repository with:
- Device information (Samsung Galaxy A54, Android version)
- Steps to reproduce the issue
- Expected vs actual behavior
- Screenshots if applicable
