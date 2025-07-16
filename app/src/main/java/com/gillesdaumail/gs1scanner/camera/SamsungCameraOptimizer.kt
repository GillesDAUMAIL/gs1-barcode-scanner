package com.gillesdaumail.gs1scanner.camera

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.util.Size
import androidx.camera.camera2.interop.Camera2CameraInfo
import androidx.camera.core.CameraInfo
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import kotlinx.coroutines.guava.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Samsung Galaxy A54 specific camera optimizations
 * Optimized for Exynos 1380 processor and One UI 6.1
 */
@Singleton
class SamsungCameraOptimizer @Inject constructor() {

    /**
     * Get optimal preview size for Samsung Galaxy A54
     * Balances performance and quality for barcode scanning
     */
    fun getOptimalPreviewSize(): Size {
        // Samsung Galaxy A54 optimal resolution for barcode scanning
        // Balances CPU usage (Exynos 1380) with scan accuracy
        return Size(1080, 1920) // 16:9 aspect ratio
    }

    /**
     * Get optimal analysis resolution for ML Kit
     * Lower resolution for faster processing on Exynos 1380
     */
    fun getOptimalAnalysisSize(): Size {
        return Size(640, 480) // VGA resolution for fast ML processing
    }

    /**
     * Check if device supports Samsung specific camera features
     */
    suspend fun isSamsungOptimizationSupported(context: Context): Boolean {
        return try {
            val cameraProvider = ProcessCameraProvider.getInstance(context).await()
            val cameraInfo = cameraProvider.getCameraInfo(CameraSelector.DEFAULT_BACK_CAMERA)
            
            // Check if it's a Samsung camera implementation
            val camera2Info = Camera2CameraInfo.from(cameraInfo)
            val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
            val cameraId = camera2Info.cameraId
            val characteristics = cameraManager.getCameraCharacteristics(cameraId)
            
            // Check for Samsung specific features
            val vendor = characteristics.get(CameraCharacteristics.INFO_VERSION)
            vendor?.contains("Samsung", ignoreCase = true) == true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Get recommended frame rate for Samsung Galaxy A54
     * Optimized for battery life and performance
     */
    fun getRecommendedFrameRate(): Int {
        // 15 FPS is optimal for barcode scanning on Galaxy A54
        // Balances responsiveness with battery life
        return 15
    }

    /**
     * Check if device is Samsung Galaxy A54 specifically
     */
    fun isSamsungGalaxyA54(): Boolean {
        val manufacturer = android.os.Build.MANUFACTURER.lowercase()
        val model = android.os.Build.MODEL.lowercase()
        
        return manufacturer == "samsung" && 
               (model.contains("sm-a546") || model.contains("galaxy a54"))
    }

    /**
     * Get One UI specific camera settings
     */
    fun getOneUIOptimizedSettings(): CameraSettings {
        return CameraSettings(
            enableHdr = false, // Disable HDR for faster processing
            enableOis = true,  // Enable OIS for stable scanning
            focusMode = "continuous-picture", // Continuous autofocus
            exposureCompensation = 0,
            flashMode = "auto"
        )
    }
}

/**
 * Camera settings data class
 */
data class CameraSettings(
    val enableHdr: Boolean,
    val enableOis: Boolean,
    val focusMode: String,
    val exposureCompensation: Int,
    val flashMode: String
)