package com.nameisjayant.filesmanagement.features.ui.screens

import android.content.Context
import android.os.Build
import android.os.StatFs
import android.os.storage.StorageManager
import android.os.storage.StorageVolume
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nameisjayant.filesmanagement.R


@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun FilesScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val storageManager = context.getSystemService(Context.STORAGE_SERVICE) as StorageManager


    Box(
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
        ) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd
                ) {
                    IconButton(onClick = {}, modifier = Modifier.size(24.dp)) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
            }
            item {
                Text(
                    text = stringResource(R.string.files), style = TextStyle(
                        color = Color.Black, fontSize = 40.sp, fontWeight = FontWeight.W600
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                SearchBar {}
                Spacer(modifier = Modifier.height(20.dp))
            }
            item {
                StorageRow(
                    storageManager = storageManager
                )
            }

        }
    }
}

@RequiresApi(Build.VERSION_CODES.R)
@Composable
private fun StorageRow(
    modifier: Modifier = Modifier,
    storageManager: StorageManager
) {

    Box(
        modifier = modifier.background(
            Color.Blue.copy(alpha = 0.8f),
            RoundedCornerShape(16.dp)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = stringResource(R.string.device_storage), style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500
                )
            )
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                Text(
                    text = "${getTotalSpace(storageManager)} GB", style = TextStyle(
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.W500
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Divider(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${getAvailableSpace(storageManager)} GB",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 12.sp,
                        baselineShift = BaselineShift.Subscript
                    ),
                )
            }
        }
    }

}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier, onClick: () -> Unit
) {

    BasicTextField(
        value = "",
        onValueChange = {},
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick, interactionSource = remember {
                MutableInteractionSource()
            }, indication = null),
        enabled = false
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .background(
                    color = Color.LightGray.copy(alpha = 0.3f), CircleShape
                )
        ) {
            Row(
                modifier = Modifier.padding(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.Gray.copy(alpha = 0.6f),
                    modifier = Modifier
                        .size(18.dp)
                        .align(CenterVertically)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = stringResource(R.string.search), style = TextStyle(
                        color = Color.Gray.copy(alpha = 0.6f),
                        fontSize = 12.sp,
                    )
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.R)
fun getTotalSpace(storageManager: StorageManager): Long {
    var totalSpace: Long = 0L
    val storageVolumes: List<StorageVolume> = storageManager.storageVolumes

    for (storageVolume in storageVolumes) {
        val path = storageVolume.directory?.path ?: ""

        val statFs = StatFs(path)
        val blockSize: Long = statFs.blockSizeLong
        val totalBlocks: Long = statFs.blockCountLong
        val totalSizeBytes: Long = totalBlocks * blockSize
        totalSpace = totalSizeBytes / (1024 * 1024 * 1024)

    }
    return totalSpace
}

@RequiresApi(Build.VERSION_CODES.R)
fun getAvailableSpace(storageManager: StorageManager): Long {
    var availableSpace = 0L
    val storageVolumes: List<StorageVolume> = storageManager.storageVolumes

    for (storageVolume in storageVolumes) {
        val path = storageVolume.directory?.path ?: ""
        val statFs = StatFs(path)
        val availableBlocks: Long = statFs.availableBlocksLong

        availableSpace = availableBlocks / (1024 * 1024 * 1024)

    }
    return availableSpace
}
