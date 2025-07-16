package com.gillesdaumail.gs1scanner.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents a parsed GS1-128 barcode data
 */
@Parcelize
data class GS1Data(
    val gtin: String? = null,
    val lotNumber: String? = null,
    val expirationDate: String? = null,
    val rawData: String
) : Parcelable

/**
 * Represents an Application Identifier with its value
 */
data class ApplicationIdentifier(
    val ai: String,
    val value: String
)