# Deployment Guide for GS1 Scanner

## Building the Production APK

### 1. Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 34
- JDK 17 or later

### 2. Generate Signing Key

For production release, you need a signing key:

```bash
keytool -genkey -v -keystore gs1-scanner-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias gs1-scanner
```

Keep this keystore file secure and backed up!

### 3. Configure Signing in build.gradle

Add to `app/build.gradle`:

```gradle
android {
    signingConfigs {
        release {
            storeFile file('../gs1-scanner-release-key.jks')
            storePassword 'your_store_password'
            keyAlias 'gs1-scanner'
            keyPassword 'your_key_password'
        }
    }
    
    buildTypes {
        release {
            minifyEnabled true
            shrinkResources true
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            signingConfig signingConfigs.release
        }
    }
}
```

### 4. Build Release APK

```bash
./gradlew assembleRelease
```

The signed APK will be generated at: `app/build/outputs/apk/release/app-release.apk`

### 5. Verify APK

Check the APK is properly signed:
```bash
jarsigner -verify -verbose -certs app/build/outputs/apk/release/app-release.apk
```

## Testing on Samsung Galaxy A54

### Device Preparation
1. Enable Developer Options:
   - Go to Settings → About phone
   - Tap Build number 7 times
   - Enter your PIN/password

2. Enable USB Debugging:
   - Go to Settings → Developer options
   - Enable USB debugging

3. Install via ADB:
   ```bash
   adb install app/build/outputs/apk/release/app-release.apk
   ```

### Manual Installation
1. Transfer APK to device (USB, email, cloud storage)
2. Open file manager and navigate to APK
3. Tap to install (may need to allow unknown sources)

## Samsung Galaxy A54 Specific Optimizations

### Performance Settings
The app is optimized for:
- Exynos 1380 processor
- 6GB/8GB RAM configurations
- One UI 6.1 interface guidelines

### Camera Configuration
- Uses CameraX CAMERA2 implementation for best Samsung compatibility
- Optimized for rear camera (50MP main sensor)
- Auto-focus enabled for clear barcode reading

### Battery Optimization
To prevent the app from being killed during scanning:
1. Go to Settings → Device care → Battery
2. Tap "More battery settings"
3. Select "Optimize battery usage"
4. Find "GS1 Scanner" and set to "Don't optimize"

## Distribution Options

### 1. Direct APK Distribution
- Host the APK on your server
- Provide download link
- Include installation instructions

### 2. Google Play Store (Future)
- Create Play Console account
- Upload AAB (Android App Bundle)
- Complete store listing
- Submit for review

### 3. Samsung Galaxy Store (Future)
- Register Samsung developer account
- Upload APK to Galaxy Store
- Follow Samsung's review process

## Production Checklist

- [ ] APK signed with production keystore
- [ ] Proguard/R8 optimization enabled
- [ ] Debug logging disabled
- [ ] Tested on Samsung Galaxy A54
- [ ] Camera permissions working
- [ ] GS1 parsing tested with real barcodes
- [ ] UI tested in both light and dark modes
- [ ] Performance tested (no memory leaks)
- [ ] Battery usage optimized

## Troubleshooting Deployment

### APK Installation Fails
- Check if "Install unknown apps" is enabled for the installer
- Verify APK is not corrupted (try re-downloading)
- Clear space on device if storage is full

### App Crashes on Launch
- Check device logs: `adb logcat | grep gs1scanner`
- Ensure device meets minimum requirements (Android 7.0+)
- Verify all permissions are granted

### Camera Not Working
- Check camera permission in app settings
- Test with other camera apps to ensure hardware works
- Restart the app or device

### Poor Scanning Performance
- Ensure adequate lighting
- Clean camera lens
- Update to latest version
- Check if other apps are using camera simultaneously

## Monitoring and Analytics

For production deployment, consider adding:
- Crash reporting (Firebase Crashlytics)
- Performance monitoring
- Usage analytics
- User feedback collection

## Support and Maintenance

### Regular Updates
- Update dependencies monthly
- Test with latest Android versions
- Monitor Samsung One UI updates
- Address user feedback promptly

### Bug Reporting
Encourage users to report issues with:
- Device model and Android version
- Steps to reproduce
- Screenshots/logs if possible
- Barcode that failed to scan (if applicable)