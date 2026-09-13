package com.mulishboy92.sonyqx10controller.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mulishboy92.sonyqx10controller.api.SonyCamera
import com.mulishboy92.sonyqx10controller.ui.components.CameraControlPanel
import com.mulishboy92.sonyqx10controller.ui.components.LiveViewPanel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraControlScreen() {
    val scope = rememberCoroutineScope()
    val sonyCamera = remember { SonyCamera() }
    
    var isConnected by remember { mutableStateOf(false) }
    var isLiveViewActive by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(0) }
    var statusMessage by remember { mutableStateOf("Disconnected") }
    
    var shutterSpeed by remember { mutableStateOf("1/250") }
    var fNumber by remember { mutableStateOf("2.8") }
    var iso by remember { mutableStateOf("100") }
    var focusMode by remember { mutableStateOf("AF-S") }
    var exposureCompensation by remember { mutableStateOf("0.0") }
    var imageStabilization by remember { mutableStateOf(true) }
    var effectMode by remember { mutableStateOf("Off") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Sony QX10 Controller",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "Camera Status",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Text(
                                statusMessage,
                                fontSize = 14.sp,
                                color = if (isConnected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                            )
                        }
                        Button(
                            onClick = {
                                scope.launch {
                                    try {
                                        sonyCamera.startRecMode()
                                        isConnected = true
                                        statusMessage = "Connected"
                                    } catch (e: Exception) {
                                        statusMessage = "Connection Failed"
                                    }
                                }
                            },
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text(if (isConnected) "Connected" else "Connect")
                        }
                    }
                }
            }

            TabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Live View") },
                    icon = { Icon(Icons.Filled.CameraAlt, contentDescription = "Live View") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Controls") },
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "Controls") }
                )
            }

            when (selectedTab) {
                0 -> LiveViewPanel(
                    isActive = isLiveViewActive,
                    onStartLiveView = {
                        scope.launch {
                            sonyCamera.startLiveview().onSuccess {
                                isLiveViewActive = true
                                statusMessage = "Live View Active"
                            }.onFailure {
                                statusMessage = "Live View Failed"
                            }
                        }
                    },
                    onStopLiveView = {
                        scope.launch {
                            sonyCamera.stopLiveview().onSuccess {
                                isLiveViewActive = false
                                statusMessage = "Live View Stopped"
                            }
                        }
                    },
                    onTakePicture = {
                        scope.launch {
                            sonyCamera.actTakePicture().onSuccess {
                                statusMessage = "Picture Taken"
                            }.onFailure {
                                statusMessage = "Capture Failed"
                            }
                        }
                    }
                )
                1 -> CameraControlPanel(
                    shutterSpeed = shutterSpeed,
                    onShutterSpeedChange = {
                        shutterSpeed = it
                        scope.launch {
                            sonyCamera.setShutterSpeed(it)
                        }
                    },
                    fNumber = fNumber,
                    onFNumberChange = {
                        fNumber = it
                        scope.launch {
                            sonyCamera.setFNumber(it)
                        }
                    },
                    iso = iso,
                    onIsoChange = {
                        iso = it
                        scope.launch {
                            sonyCamera.setIsoSpeedRate(it)
                        }
                    },
                    focusMode = focusMode,
                    onFocusModeChange = {
                        focusMode = it
                        scope.launch {
                            sonyCamera.setFocusMode(it)
                        }
                    },
                    exposureCompensation = exposureCompensation,
                    onExposureCompensationChange = { exposureCompensation = it },
                    imageStabilization = imageStabilization,
                    onImageStabilizationChange = { imageStabilization = it },
                    effectMode = effectMode,
                    onEffectModeChange = { effectMode = it }
                )
            }
        }
    }
}
