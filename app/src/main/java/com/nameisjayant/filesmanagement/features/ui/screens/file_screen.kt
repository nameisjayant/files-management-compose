package com.nameisjayant.filesmanagement.features.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nameisjayant.filesmanagement.R


@Composable
fun FilesScreen(
    modifier: Modifier = Modifier
) {

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
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd
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
                        color = Color.Black,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.W600
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                SearchBar {

                }
            }

        }
    }
}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    BasicTextField(
        value = "", onValueChange = {},
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
                    color = Color.LightGray.copy(alpha = 0.3f),
                    CircleShape
                )
        ) {
            Row(
                modifier = Modifier.padding(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(18.dp)
                        .align(CenterVertically)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = stringResource(R.string.search), style = TextStyle(
                        color = Color.Gray,
                        fontSize = 12.sp,
                    )
                )
            }
        }
    }

}