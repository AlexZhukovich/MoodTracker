package com.alexzh.moodtracker.presentation.feature.settings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.R

@ExperimentalMaterial3Api
@Composable
fun SettingsScreen(
    onProfile: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SmallTopAppBar(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.inversePrimary),
                title = { Text(stringResource(R.string.settingsScreen_title)) }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize()
            .padding(
                start = 0.dp,
                top = paddingValues.calculateTopPadding(),
                end = 0.dp,
                bottom = paddingValues.calculateBottomPadding()
            )
        ) {
            ProfileSettingItem(onClick = { onProfile() })
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f))
        }
    }
}

@Composable
fun ProfileSettingItem(
    @StringRes title: Int = R.string.settingsScreen_profile_title,
    @StringRes subtitle: Int = R.string.settingsScreen_profile_subtitle,
    @DrawableRes icon: Int = R.drawable.ic_settings_account,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .height(72.dp)
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Image(
            modifier = Modifier.size(56.dp),
            painter = painterResource(icon),
            contentDescription = stringResource(R.string.settingsScreen_profile_contentDescription)
        )
        Column(
            modifier = Modifier.fillMaxWidth()
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