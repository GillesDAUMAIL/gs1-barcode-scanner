package com.gillesdaumail.gs1scanner.ui.components

import android.content.Context
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.gillesdaumail.gs1scanner.camera.CameraManager

/**
 * Camera preview composable with scanning overlay
 */
@Composable
fun CameraPreview(
    cameraManager: CameraManager,
    onBarcodeDetected: (String) -> Unit,
    onError: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    
    val previewView = remember {
        PreviewView(context).apply {
            scaleType = PreviewView.ScaleType.FILL_CENTER
        }
    }

    LaunchedEffect(cameraManager) {
        try {
            cameraManager.startCamera(
                context = context,
                lifecycleOwner = lifecycleOwner,
                previewView = previewView,
                onBarcodeDetected = onBarcodeDetected
            )
        } catch (e: Exception) {
            onError("Failed to start camera: ${e.message}")
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            cameraManager.stopCamera()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        )
        
        // Scanning overlay
        ScanningOverlay(
            modifier = Modifier.fillMaxSize()
        )
    }
}

/**
 * Scanning overlay with guide rectangle
 */
@Composable
private fun ScanningOverlay(
    modifier: Modifier = Modifier
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.background.copy(alpha = 0.7f)
    
    Canvas(modifier = modifier) {
        drawScanningOverlay(
            primaryColor = primaryColor,
            backgroundColor = backgroundColor
        )
    }
}

/**
 * Draw the scanning overlay with cut-out rectangle
 */
private fun DrawScope.drawScanningOverlay(
    primaryColor: Color,
    backgroundColor: Color
) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    
    // Define scanning area (centered rectangle)
    val scanAreaWidth = canvasWidth * 0.8f
    val scanAreaHeight = scanAreaWidth * 0.4f
    val left = (canvasWidth - scanAreaWidth) / 2
    val top = (canvasHeight - scanAreaHeight) / 2
    val right = left + scanAreaWidth
    val bottom = top + scanAreaHeight
    
    val cornerRadius = 16.dp.toPx()
    
    // Create path for the scanning area
    val scanAreaPath = Path().apply {
        addRoundRect(
            RoundRect(
                Rect(left, top, right, bottom),
                CornerRadius(cornerRadius)
            )
        )
    }
    
    // Draw semi-transparent overlay everywhere except the scanning area
    clipPath(scanAreaPath, ClipOp.Difference) {
        drawRect(backgroundColor)
    }
    
    // Draw scanning frame
    drawRoundRect(
        color = primaryColor,
        topLeft = androidx.compose.ui.geometry.Offset(left, top),
        size = Size(scanAreaWidth, scanAreaHeight),
        cornerRadius = CornerRadius(cornerRadius),
        style = Stroke(width = 3.dp.toPx())
    )
    
    // Draw corner indicators
    val cornerLength = 30.dp.toPx()
    val cornerThickness = 4.dp.toPx()
    
    // Top-left corner
    drawLine(
        color = primaryColor,
        start = androidx.compose.ui.geometry.Offset(left, top + cornerLength),
        end = androidx.compose.ui.geometry.Offset(left, top),
        strokeWidth = cornerThickness
    )
    drawLine(
        color = primaryColor,
        start = androidx.compose.ui.geometry.Offset(left, top),
        end = androidx.compose.ui.geometry.Offset(left + cornerLength, top),
        strokeWidth = cornerThickness
    )
    
    // Top-right corner
    drawLine(
        color = primaryColor,
        start = androidx.compose.ui.geometry.Offset(right, top + cornerLength),
        end = androidx.compose.ui.geometry.Offset(right, top),
        strokeWidth = cornerThickness
    )
    drawLine(
        color = primaryColor,
        start = androidx.compose.ui.geometry.Offset(right, top),
        end = androidx.compose.ui.geometry.Offset(right - cornerLength, top),
        strokeWidth = cornerThickness
    )
    
    // Bottom-left corner
    drawLine(
        color = primaryColor,
        start = androidx.compose.ui.geometry.Offset(left, bottom - cornerLength),
        end = androidx.compose.ui.geometry.Offset(left, bottom),
        strokeWidth = cornerThickness
    )
    drawLine(
        color = primaryColor,
        start = androidx.compose.ui.geometry.Offset(left, bottom),
        end = androidx.compose.ui.geometry.Offset(left + cornerLength, bottom),
        strokeWidth = cornerThickness
    )
    
    // Bottom-right corner
    drawLine(
        color = primaryColor,
        start = androidx.compose.ui.geometry.Offset(right, bottom - cornerLength),
        end = androidx.compose.ui.geometry.Offset(right, bottom),
        strokeWidth = cornerThickness
    )
    drawLine(
        color = primaryColor,
        start = androidx.compose.ui.geometry.Offset(right, bottom),
        end = androidx.compose.ui.geometry.Offset(right - cornerLength, bottom),
        strokeWidth = cornerThickness
    )
}