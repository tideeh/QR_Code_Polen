package br.com.polenflorestal.qrcodepolen

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val versionName = packageManager.getPackageInfo(packageName, 0).versionName

        findViewById<TextView>(R.id.splash_version_name).text = versionName
    }
}
