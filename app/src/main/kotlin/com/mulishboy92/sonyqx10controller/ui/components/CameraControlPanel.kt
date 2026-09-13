package com.mulishboy92.sonyqx10controller.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraControlPanel(
    shutterSpeed: String,
    onShutterSpeedChange: (String) -> Unit,
    fNumber: String,
    onFNumberChange: (String) -> Unit,
    iso: String,
    onIsoChange: (String) -> Unit,
    focusMode: String,
    onFocusModeChange: (String) -> Unit,
    exposureCompensation: String,
    onExposureCompensationChange: (String) -> Unit,
    imageStabilization: Boolean,
    onImageStabilizationChange: (Boolean) -> Unit,
    effectMode: String,
    onEffectModeChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        SettingRow(
            label = "Shutter Speed",
            value = shutterSpeed,
            options = listOf("1/500", "1/250", "1/125", "1/60", "1/30", "1/15"),
            onValueChange = onShutterSpeedChange
        )

        Divider(modifier = Modifier.padding(vertical = 12.dp))

        SettingRow(
            label = "F-Number",
            value = fNumber,
            options = listOf("2.0", "2.2", "2.5", "2.8", "3.5", "4.0", "5.6"),
            onValueChange = onFNumberChange
        )

        Divider(modifier = Modifier.padding(vertical = 12.dp))

        SettingRow(
            label = "ISO",
            value = iso,
            options = listOf("50", "100", "200", "400", "800", "1600", "3200"),
            onValueChange = onIsoChange
        )

        Divider(modifier = Modifier.padding(vertical = 12.dp))

        SettingRow(
            label = "Focus Mode",
            value = focusMode,
            options = listOf("AF-S", "AF-C", "MF"),
            onValueChange = onFocusModeChange
        )

        Divider(modifier = Modifier.padding(vertical = 12.dp))

        SettingRow(
            label = "Exposure Compensation",
            value = exposureCompensation,
            options = listOf("-2.0", "-1.5", "-1.0", "-0.5", "0.0", "0.5", "1.0", "1.5", "2.0"),
            onValueChange = onExposureCompensationChange
        )

        Divider(modifier = Modifier.padding(vertical = 12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Image Stabilization",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            Switch(
                checked = imageStabilization,
                onCheckedChange = onImageStabilizationChange
            )
        }

        Divider(modifier = Modifier.padding(vertical = 12.dp))

        SettingRow(
            label = "Effect Mode",
            value = effectMode,
            options = listOf("Off", "Sepia", "Black & White", "Vivid", "Neutral"),
            onValueChange = onEffectModeChange
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingRow(
    label: String,
    value: String,
    options: List<String>,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )

        Box {
            Button(
                onClick = { expanded = true },
                modifier = Modifier.width(120.dp)
            ) {
                Text(value)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.width(120.dp)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onValueChange(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
