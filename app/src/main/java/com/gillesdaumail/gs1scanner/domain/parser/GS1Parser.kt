package com.gillesdaumail.gs1scanner.domain.parser

import com.gillesdaumail.gs1scanner.domain.model.ApplicationIdentifier
import com.gillesdaumail.gs1scanner.domain.model.GS1Data
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Parser for GS1-128 barcode data
 * Handles Application Identifiers (AI) parsing according to GS1 standards
 */
@Singleton
class GS1Parser @Inject constructor() {

    companion object {
        // GS1 Application Identifiers we support
        private const val AI_GTIN = "01"
        private const val AI_LOT_NUMBER = "10"
        private const val AI_EXPIRATION_DATE = "17"
        
        // GS1 Function Code 1 (FNC1) character
        private const val FNC1 = "\u001D"
        
        // AI lengths (for fixed-length AIs)
        private val AI_LENGTHS = mapOf(
            AI_GTIN to 14,
            AI_EXPIRATION_DATE to 6
        )
    }

    /**
     * Parse GS1-128 barcode data into structured format
     */
    fun parseGS1Data(rawData: String): GS1Data {
        val cleanData = cleanRawData(rawData)
        val ais = extractApplicationIdentifiers(cleanData)
        
        return GS1Data(
            gtin = ais.find { it.ai == AI_GTIN }?.value,
            lotNumber = ais.find { it.ai == AI_LOT_NUMBER }?.value,
            expirationDate = ais.find { it.ai == AI_EXPIRATION_DATE }?.value?.let { formatDate(it) },
            rawData = rawData
        )
    }

    /**
     * Clean raw barcode data - remove control characters and normalize
     */
    private fun cleanRawData(rawData: String): String {
        return rawData.replace("]C1", "")
            .replace("]d2", "")
            .replace("]Q3", "")
            .trim()
    }

    /**
     * Extract Application Identifiers from the data string
     */
    private fun extractApplicationIdentifiers(data: String): List<ApplicationIdentifier> {
        val ais = mutableListOf<ApplicationIdentifier>()
        var position = 0
        
        while (position < data.length) {
            // Try to find AI starting with 2-4 digits
            val aiMatch = findApplicationIdentifier(data, position)
            if (aiMatch != null) {
                val (ai, value, nextPosition) = aiMatch
                ais.add(ApplicationIdentifier(ai, value))
                position = nextPosition
            } else {
                // If no valid AI found, break to avoid infinite loop
                break
            }
        }
        
        return ais
    }

    /**
     * Find next Application Identifier at given position
     */
    private fun findApplicationIdentifier(data: String, startPos: Int): Triple<String, String, Int>? {
        // Try 2-digit AI first (most common)
        for (aiLength in 2..4) {
            if (startPos + aiLength <= data.length) {
                val ai = data.substring(startPos, startPos + aiLength)
                
                if (isValidAI(ai)) {
                    val valueStart = startPos + aiLength
                    val (value, nextPos) = extractValue(data, valueStart, ai)
                    if (value.isNotEmpty()) {
                        return Triple(ai, value, nextPos)
                    }
                }
            }
        }
        return null
    }

    /**
     * Check if the given string is a valid AI we support
     */
    private fun isValidAI(ai: String): Boolean {
        return ai in listOf(AI_GTIN, AI_LOT_NUMBER, AI_EXPIRATION_DATE)
    }

    /**
     * Extract value for the given AI
     */
    private fun extractValue(data: String, startPos: Int, ai: String): Pair<String, Int> {
        if (startPos >= data.length) return Pair("", startPos)
        
        val fixedLength = AI_LENGTHS[ai]
        
        return if (fixedLength != null) {
            // Fixed-length AI
            val endPos = minOf(startPos + fixedLength, data.length)
            Pair(data.substring(startPos, endPos), endPos)
        } else {
            // Variable-length AI - terminated by FNC1 or end of string
            val fnc1Pos = data.indexOf(FNC1, startPos)
            val endPos = if (fnc1Pos != -1) fnc1Pos else data.length
            Pair(data.substring(startPos, endPos), if (fnc1Pos != -1) fnc1Pos + 1 else endPos)
        }
    }

    /**
     * Format date from YYMMDD to readable format
     */
    private fun formatDate(dateStr: String): String {
        if (dateStr.length != 6) return dateStr
        
        return try {
            val year = "20${dateStr.substring(0, 2)}"
            val month = dateStr.substring(2, 4)
            val day = dateStr.substring(4, 6)
            "$day/$month/$year"
        } catch (e: Exception) {
            dateStr
        }
    }

    /**
     * Validate if the data contains valid GS1-128 structure
     */
    fun isValidGS1Data(rawData: String): Boolean {
        val cleanData = cleanRawData(rawData)
        val ais = extractApplicationIdentifiers(cleanData)
        return ais.isNotEmpty()
    }
}