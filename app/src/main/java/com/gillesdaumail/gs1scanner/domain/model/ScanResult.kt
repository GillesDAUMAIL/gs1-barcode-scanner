package com.gillesdaumail.gs1scanner.domain.model

/**
 * Sealed class representing different scan result states
 */
sealed class ScanResult {
    data object Scanning : ScanResult()
    data class Success(val gs1Data: GS1Data) : ScanResult()
    data class Error(val message: String) : ScanResult()
}