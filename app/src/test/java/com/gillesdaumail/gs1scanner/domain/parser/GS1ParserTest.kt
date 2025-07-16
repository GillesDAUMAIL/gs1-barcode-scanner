package com.gillesdaumail.gs1scanner.domain.parser

import com.gillesdaumail.gs1scanner.domain.model.GS1Data
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for GS1Parser
 */
class GS1ParserTest {

    private lateinit var gs1Parser: GS1Parser

    @Before
    fun setUp() {
        gs1Parser = GS1Parser()
    }

    @Test
    fun `parseGS1Data with valid GTIN only should return correct data`() {
        val input = "0112345678901234"
        val result = gs1Parser.parseGS1Data(input)
        
        assertEquals("12345678901234", result.gtin)
        assertNull(result.lotNumber)
        assertNull(result.expirationDate)
        assertEquals(input, result.rawData)
    }

    @Test
    fun `parseGS1Data with GTIN and lot number should return correct data`() {
        val input = "011234567890123410ABC123"
        val result = gs1Parser.parseGS1Data(input)
        
        assertEquals("12345678901234", result.gtin)
        assertEquals("ABC123", result.lotNumber)
        assertNull(result.expirationDate)
        assertEquals(input, result.rawData)
    }

    @Test
    fun `parseGS1Data with all supported fields should return correct data`() {
        val input = "01123456789012341017231231\u001D10LOT123"
        val result = gs1Parser.parseGS1Data(input)
        
        assertEquals("12345678901234", result.gtin)
        assertEquals("LOT123", result.lotNumber)
        assertEquals("31/12/2023", result.expirationDate)
        assertEquals(input, result.rawData)
    }

    @Test
    fun `parseGS1Data with expiration date should format correctly`() {
        val input = "0112345678901234172312311"
        val result = gs1Parser.parseGS1Data(input)
        
        assertEquals("12345678901234", result.gtin)
        assertEquals("31/12/2023", result.expirationDate)
    }

    @Test
    fun `isValidGS1Data with valid data should return true`() {
        val input = "0112345678901234"
        val result = gs1Parser.isValidGS1Data(input)
        
        assertTrue(result)
    }

    @Test
    fun `isValidGS1Data with invalid data should return false`() {
        val input = "invalid_data"
        val result = gs1Parser.isValidGS1Data(input)
        
        assertFalse(result)
    }

    @Test
    fun `parseGS1Data with empty string should return data with empty fields`() {
        val input = ""
        val result = gs1Parser.parseGS1Data(input)
        
        assertNull(result.gtin)
        assertNull(result.lotNumber)
        assertNull(result.expirationDate)
        assertEquals(input, result.rawData)
    }
}