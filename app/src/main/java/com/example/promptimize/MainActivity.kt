package com.example.promptimize

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.promptimize.databinding.ActivityMainBinding
import com.example.promptimize.utils.Constants
import com.example.promptimize.utils.Constants.shareMessage
import org.acra.BuildConfig

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // force dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appBarLayout = binding.appBarLayout

        //update app version in nav drawer
        val appVersion = binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tvAppVersion)
        appVersion.text = getString(R.string.app_version, BuildConfig.VERSION_NAME)

        binding.ivNavMenu.setOnClickListener {
            handleNavDrawer()
        }

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.itemReportBug -> {
                    reportBug()
                }

                R.id.itemShareApp -> {
                    shareApp()
                }

                R.id.itemSourceCode -> {
                    sourceCode()
                }

                R.id.itemDeveloper -> {
                    developerProfile()
                }
            }
            true
        }

        binding.ivFontSize.setOnClickListener {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigateUp()
            navController.navigate(R.id.fontSettingFragment)
        }

        binding.ivLanguage.setOnClickListener {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigateUp()
            navController.navigate(R.id.settingsFragment)
        }

    }

    private fun handleNavDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun shareApp() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareMessage += "https://github.com/dinisoraya/Promptimize"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Promptimize")
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(Intent.createChooser(shareIntent, "Share This App"))
    }

    private fun reportBug() {
        val subject = "Promptimize: Report Bug"
        val uriBuilder = StringBuilder("mailto:" + Uri.encode(Constants.email))
        uriBuilder.append("?subject=" + Uri.encode(subject))
        val uriString = uriBuilder.toString()

        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(uriString))
        startActivity(Intent.createChooser(intent, "Report Bug"))
    }

    private fun sourceCode() {
        val intent = Intent(
            Intent.ACTION_VIEW, Uri.parse(Constants.SOURCE_CODE_LINK)
        )
        startActivity(intent)

    }

    private fun developerProfile() {
        //close nav drawer
        binding.drawerLayout.close()

        //open about us page
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigateUp()
        navController.navigate(R.id.aboutUsFragment)
    }

    companion object {
        lateinit var appBarLayout: ConstraintLayout
    }
}