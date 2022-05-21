package com.alexzh.moodtracker.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Color.White,
    backgroundColor: Color = MaterialTheme.colorScheme.inversePrimary
) {
    Surface(
        color = backgroundColor,
        contentColor = textColor,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.padding(top = 4.dp, end = 4.dp, bottom = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Preview
@Composable
fun Preview_Chip() {
    LazyRow {
        items((1..3).toList()) {
            Chip(
                text = "Category $it"
            )
        }
    }
}