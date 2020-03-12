package br.com.polenflorestal.qrcodepolen

import `in`.mrasif.libs.easyqr.EasyQR
import `in`.mrasif.libs.easyqr.QRScanner
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var tvData : TextView
    private lateinit var btnQRScan : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvData = findViewById(R.id.tvData)
        btnQRScan = findViewById(R.id.btnQRScan)
    }

    fun qrScan(view: View) {
        val intent = Intent(this, QRScanner::class.java)
        intent.putExtra(EasyQR.CAMERA_BORDER,100);
        intent.putExtra(EasyQR.CAMERA_BORDER_COLOR,"#C1000000");
        intent.putExtra(EasyQR.IS_SCAN_BAR,true);
        intent.putExtra(EasyQR.IS_BEEP,true);
        intent.putExtra(EasyQR.BEEP_RESOURCE_ID,R.raw.beep);
        startActivityForResult(intent, EasyQR.QR_SCANNER_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when {
            requestCode == EasyQR.QR_SCANNER_REQUEST -> {
                if (resultCode == RESULT_OK) {
                    tvData.text = data?.getStringExtra(EasyQR.DATA) ?: "-1";
                }
            }
        }
    }

}
