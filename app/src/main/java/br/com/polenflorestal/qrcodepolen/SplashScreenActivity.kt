package br.com.polenflorestal.qrcodepolen

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.datatransport.BuildConfig


class SplashScreenActivity : AppCompatActivity() {

    private lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        sharedPreferences = getSharedPreferences(SP_NOME, Context.MODE_PRIVATE)
        val versionName = packageManager.getPackageInfo(packageName, 0).versionName

        //findViewById<TextView>(R.id.splash_version_name).text = versionName

        checkFirstRun()

        DataBaseUtil.abrir(this)
        val c : Cursor? = DataBaseUtil.buscar("Arvore", arrayOf<String>("codigo", "local"), "local = 'Viçosa/MG'", "")

        while (c?.moveToNext()!!){
            val cod : String = c.getString(0)
            val emp : String = c.getString(1)

            Log.i("BANCO_DADOS", "teste busca: $cod $emp");
        }

        Handler().postDelayed( {fechaSplash()}, 500)
    }

    private fun fechaSplash() {
        val it = Intent(this, MainActivity::class.java)
        startActivity(it)
        finish()
    }

    private fun checkFirstRun() {
        val currentVersionCode : Int = BuildConfig.VERSION_CODE
        val savedVersionCode : Int = sharedPreferences.getInt(SP_KEY_VERSION_CODE, DEFAULT_INT_VALUE)

        when {
            currentVersionCode == savedVersionCode -> {
                // this is just a normal run
                return
            }
            savedVersionCode == DEFAULT_INT_VALUE -> {
                // this is a new install (or the user cleared the preferences)
                // cria o BD, ...

                DataBaseUtil.abrir(this)
                DataBaseUtil.criaDB()

                val editor = sharedPreferences.edit()
                editor.putInt(SP_KEY_VERSION_CODE, currentVersionCode)
                editor.apply()

                return
            }
            currentVersionCode > savedVersionCode -> {
                // this is an upgrade
                // atualiza o BD, ...

                DataBaseUtil.abrir(this)
                DataBaseUtil.criaDB()

                val editor = sharedPreferences.edit()
                editor.putInt(SP_KEY_VERSION_CODE, currentVersionCode)
                editor.apply()

                return
            }
        }
    }

}
