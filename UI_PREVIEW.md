# GS1 Scanner - Visual UI Preview

## Scanner Screen Layout
```
┌─────────────────────────────────────┐
│ ●●● Samsung Galaxy A54 Status Bar   │
├─────────────────────────────────────┤
│                                     │
│    [Camera Preview Full Screen]     │
│                                     │
│     ┌─────────────────────────┐     │
│     │ ┌─┐               ┌─┐   │     │
│     │ │ │               │ │   │     │
│     │ └─┘  Scan Area    └─┘   │     │
│     │                         │     │
│     │ ┌─┐               ┌─┐   │     │
│     │ │ │               │ │   │     │
│     │ └─┘               └─┘   │     │
│     └─────────────────────────┘     │
│                                     │
│                                     │
│  ┌─────────────────────────────────┐ │
│  │ 📱 Point camera at GS1-128     │ │
│  │    barcode                      │ │
│  └─────────────────────────────────┘ │
└─────────────────────────────────────┘
```

## Result Screen Layout
```
┌─────────────────────────────────────┐
│ ← GS1 Scanner     Scan Result       │
├─────────────────────────────────────┤
│                                     │
│  ┌─────────────────────────────────┐ │
│  │  📱  GS1-128 Barcode Decoded   │ │
│  └─────────────────────────────────┘ │
│                                     │
│  ┌─────────────────────────────────┐ │
│  │ 🏷️  GTIN (01)                   │ │
│  │     12345678901234              │ │
│  │     Global Trade Item Number    │ │
│  └─────────────────────────────────┘ │
│                                     │
│  ┌─────────────────────────────────┐ │
│  │ 📄  Lot Number (10)             │ │
│  │     ABC123                      │ │
│  │     Batch/Lot Number            │ │
│  └─────────────────────────────────┘ │
│                                     │
│  ┌─────────────────────────────────┐ │
│  │ 📅  Expiration Date (17)        │ │
│  │     31/12/2024                  │ │
│  │     Use By Date                 │ │
│  └─────────────────────────────────┘ │
│                                     │
│  ┌─────────────────────────────────┐ │
│  │ Raw Data                        │ │
│  │ 01123456789012341724123110ABC123│ │
│  └─────────────────────────────────┘ │
│                                     │
│  ┌─────────────────────────────────┐ │
│  │     📱 Scan Again               │ │
│  └─────────────────────────────────┘ │
└─────────────────────────────────────┘
```

## Key Visual Features

### Material Design 3 Elements
- **Dynamic Colors**: Adapts to Samsung One UI theme
- **Cards**: Elevated surfaces for data display
- **Icons**: Material icons for clear data identification
- **Typography**: One UI optimized font sizes and weights

### Samsung One UI 6.1 Integration
- **Corner Radius**: 16dp rounded corners throughout
- **Spacing**: 16dp/24dp/32dp consistent spacing
- **Colors**: Primary blue (#1F5AA6) with dynamic variants
- **Elevation**: Subtle shadows for depth

### Camera Interface
- **Overlay Guide**: Visual scanning frame with corner indicators
- **Real-time Preview**: Full-screen camera with optimal aspect ratio
- **Permission Handling**: Clear permission request with explanation
- **Status Indicators**: Loading states and error messages

### Data Presentation
- **Structured Layout**: Clear hierarchy of information
- **Color Coding**: Different colors for different data types
- **Readable Format**: Date formatted as DD/MM/YYYY
- **Raw Data**: Technical details for debugging

This visual representation shows how the application would appear on a Samsung Galaxy A54, providing an intuitive and modern scanning experience optimized for GS1-128 barcode processing.