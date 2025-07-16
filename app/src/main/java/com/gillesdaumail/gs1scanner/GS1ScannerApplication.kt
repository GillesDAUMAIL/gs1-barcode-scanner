package com.gillesdaumail.gs1scanner

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for GS1 Barcode Scanner
 * Annotated with @HiltAndroidApp to enable Hilt dependency injection
 */
@HiltAndroidApp
class GS1ScannerApplication : Application()