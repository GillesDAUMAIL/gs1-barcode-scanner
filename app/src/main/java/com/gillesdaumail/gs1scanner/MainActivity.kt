package com.gillesdaumail.gs1scanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gillesdaumail.gs1scanner.camera.CameraManager
import com.gillesdaumail.gs1scanner.domain.model.GS1Data
import com.gillesdaumail.gs1scanner.domain.model.ScanResult
import com.gillesdaumail.gs1scanner.ui.result.ResultScreen
import com.gillesdaumail.gs1scanner.ui.scanner.ScannerScreen
import com.gillesdaumail.gs1scanner.ui.theme.GS1ScannerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var cameraManager: CameraManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            GS1ScannerTheme {
                GS1ScannerApp(cameraManager = cameraManager)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraManager.release()
    }
}

@Composable
fun GS1ScannerApp(
    cameraManager: CameraManager,
    navController: NavHostController = rememberNavController()
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "scanner",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("scanner") {
                ScannerScreen(
                    onScanResult = { result ->
                        // Navigate to result screen with the scanned data
                        // In a real app, you'd pass the data properly through navigation
                        navController.currentBackStackEntry?.savedStateHandle?.set("gs1_data", result.gs1Data)
                        navController.navigate("result")
                    },
                    cameraManager = cameraManager
                )
            }
            
            composable("result") {
                val gs1Data = navController.previousBackStackEntry?.savedStateHandle?.get<GS1Data>("gs1_data")
                
                gs1Data?.let { data ->
                    ResultScreen(
                        gs1Data = data,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onScanAgain = {
                            navController.popBackStack("scanner", inclusive = false)
                        }
                    )
                }
            }
        }
    }
}