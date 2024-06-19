package com.example.promptimize

import android.app.Application
import android.content.Context
import com.example.promptimize.utils.Constants
import org.acra.ACRA
import org.acra.BuildConfig
import org.acra.config.CoreConfigurationBuilder
import org.acra.config.DialogConfigurationBuilder
import org.acra.config.MailSenderConfigurationBuilder
import org.acra.data.StringFormat


class GitApplication : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        ACRA.init(
            this, CoreConfigurationBuilder()
                .withBuildConfigClass(BuildConfig::class.java)
                .withReportFormat(StringFormat.JSON)
                .withPluginConfigurations(

                    // Dialog configuration:
                    DialogConfigurationBuilder()
                        .withText(getString(R.string.dialog_text))
                        .withTitle(getString(R.string.dialog_title))
                        .withPositiveButtonText(getString(R.string.dialog_positive))
                        .withNegativeButtonText(getString(R.string.dialog_negative))
                        .withResTheme(R.style.AppTheme_Dialog)
                        .build(),

                    // Mail sender configuration:
                    MailSenderConfigurationBuilder()
                        .withMailTo(Constants.email)
                        .withReportFileName("crash_report.txt")
                        .withReportAsFile(true)
                        .build()
                )
        )
    }
}