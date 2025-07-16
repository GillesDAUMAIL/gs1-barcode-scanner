package com.gillesdaumail.gs1scanner.domain.usecase

import com.gillesdaumail.gs1scanner.domain.model.GS1Data
import com.gillesdaumail.gs1scanner.domain.parser.GS1Parser
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use case for processing scanned barcode data
 */
@Singleton
class ProcessBarcodeUseCase @Inject constructor(
    private val gs1Parser: GS1Parser
) {
    /**
     * Process raw barcode data and return parsed GS1 data
     */
    fun execute(rawBarcodeData: String): Result<GS1Data> {
        return try {
            if (rawBarcodeData.isBlank()) {
                Result.failure(Exception("Barcode data is empty"))
            } else if (!gs1Parser.isValidGS1Data(rawBarcodeData)) {
                Result.failure(Exception("Invalid GS1-128 barcode format"))
            } else {
                val gs1Data = gs1Parser.parseGS1Data(rawBarcodeData)
                Result.success(gs1Data)
            }
        } catch (e: Exception) {
            Result.failure(Exception("Failed to parse barcode: ${e.message}"))
        }
    }
}