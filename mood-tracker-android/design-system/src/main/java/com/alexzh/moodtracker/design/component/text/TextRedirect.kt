package com.alexzh.moodtracker.design.component.text

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle

@Composable
fun TextRedirect(
    text: AnnotatedString,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge
) {
    Text(
        color = MaterialTheme.colorScheme.onBackground,
        text = text,
        style = textStyle,
        modifier = modifier
            .clickable { onClick() }
    )
}