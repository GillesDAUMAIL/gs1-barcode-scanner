package com.gillesdaumail.gs1scanner.domain.usecase

import com.gillesdaumail.gs1scanner.domain.model.GS1Data
import com.gillesdaumail.gs1scanner.domain.parser.GS1Parser
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

/**
 * Unit tests for ProcessBarcodeUseCase
 */
class ProcessBarcodeUseCaseTest {

    @Mock
    private lateinit var gs1Parser: GS1Parser

    private lateinit var processBarcodeUseCase: ProcessBarcodeUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        processBarcodeUseCase = ProcessBarcodeUseCase(gs1Parser)
    }

    @Test
    fun `execute with valid barcode should return success`() {
        val input = "0112345678901234"
        val expectedData = GS1Data(gtin = "12345678901234", rawData = input)
        
        whenever(gs1Parser.isValidGS1Data(input)).thenReturn(true)
        whenever(gs1Parser.parseGS1Data(input)).thenReturn(expectedData)
        
        val result = processBarcodeUseCase.execute(input)
        
        assertTrue(result.isSuccess)
        assertEquals(expectedData, result.getOrNull())
    }

    @Test
    fun `execute with invalid barcode should return failure`() {
        val input = "invalid_barcode"
        
        whenever(gs1Parser.isValidGS1Data(input)).thenReturn(false)
        
        val result = processBarcodeUseCase.execute(input)
        
        assertTrue(result.isFailure)
        assertEquals("Invalid GS1-128 barcode format", result.exceptionOrNull()?.message)
    }

    @Test
    fun `execute with empty barcode should return failure`() {
        val input = ""
        
        val result = processBarcodeUseCase.execute(input)
        
        assertTrue(result.isFailure)
        assertEquals("Barcode data is empty", result.exceptionOrNull()?.message)
    }

    @Test
    fun `execute with blank barcode should return failure`() {
        val input = "   "
        
        val result = processBarcodeUseCase.execute(input)
        
        assertTrue(result.isFailure)
        assertEquals("Barcode data is empty", result.exceptionOrNull()?.message)
    }
}