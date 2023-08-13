package com.alexzh.moodtracker.presentation.feature.settings.backup

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts

class RestoreBackupFileContract : ActivityResultContracts.OpenDocument() {
    override fun createIntent(context: Context, input: Array<String>): Intent {
        super.createIntent(context, input)
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "*/*"

        intent.addFlags(
            Intent.FLAG_GRANT_WRITE_URI_PERMISSION or
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        return intent
    }
}
