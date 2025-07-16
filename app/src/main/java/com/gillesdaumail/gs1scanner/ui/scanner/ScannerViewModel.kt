package com.gillesdaumail.gs1scanner.ui.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gillesdaumail.gs1scanner.domain.model.ScanResult
import com.gillesdaumail.gs1scanner.domain.usecase.ProcessBarcodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the scanner screen
 */
@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val processBarcodeUseCase: ProcessBarcodeUseCase
) : ViewModel() {

    private val _scanResult = MutableStateFlow<ScanResult>(ScanResult.Scanning)
    val scanResult: StateFlow<ScanResult> = _scanResult.asStateFlow()

    private val _isCameraPermissionGranted = MutableStateFlow(false)
    val isCameraPermissionGranted: StateFlow<Boolean> = _isCameraPermissionGranted.asStateFlow()

    /**
     * Process detected barcode
     */
    fun onBarcodeDetected(rawData: String) {
        viewModelScope.launch {
            val result = processBarcodeUseCase.execute(rawData)
            
            _scanResult.value = if (result.isSuccess) {
                ScanResult.Success(result.getOrThrow())
            } else {
                ScanResult.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    /**
     * Reset to scanning state
     */
    fun resetToScanning() {
        _scanResult.value = ScanResult.Scanning
    }

    /**
     * Update camera permission status
     */
    fun updateCameraPermission(granted: Boolean) {
        _isCameraPermissionGranted.value = granted
    }

    /**
     * Handle scan error
     */
    fun onScanError(message: String) {
        _scanResult.value = ScanResult.Error(message)
    }
}