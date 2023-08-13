package com.alexzh.moodtracker.design.component.settings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun SettingsItem(
    @StringRes title: Int,
    @StringRes subtitle: Int,
    @DrawableRes icon: Int,
    @StringRes contentDescription: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Icon(
            modifier = Modifier
                .size(36.dp)
                .align(Alignment.CenterVertically),
            painter = painterResource(icon),
            contentDescription = stringResource(contentDescription),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 8.dp, top = 4.dp, bottom = 4.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(title)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(subtitle),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}