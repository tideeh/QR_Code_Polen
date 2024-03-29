package br.com.polenflorestal.qrcodepolen

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.ListenerRegistration


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var btnQRScan: ImageButton
    val REQUEST_ID_MULTIPLE_PERMISSIONS = 7
    //private var listenerComentarios : ListenerRegistration? = null

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        configuraToolbarComNavDrawer()

        btnQRScan = findViewById(R.id.btnQRScan)
    }

    override fun onDestroy() {
        super.onDestroy()
        //listenerComentarios.remove()
        //Log.i("MY_FIREBASE_MAIN", "listener removido")
    }


    fun btnScan(view: View) {

        if (checkAndRequestPermissions() == true) {
            scanQR()
        }
    }

    private fun scanQR() {

        val intent = Intent(this, QRScanner::class.java)

        intent.putExtra(QR_IS_TOOLBAR_SHOW, true)
        //intent.putExtra(QR_TOOLBAR_DRAWABLE_ID,R.drawable.ic_audiotrack_dark)
        //intent.putExtra(QR_TOOLBAR_TEXT,"My QR")
        //intent.putExtra(QR_TOOLBAR_BACKGROUND_COLOR,"#0588EE")
        //intent.putExtra(QR_TOOLBAR_TEXT_COLOR,"#FFFFFF")
        intent.putExtra(QR_BACKGROUND_COLOR, "#000000")
        //intent.putExtra(QR_CAMERA_MARGIN_LEFT,50)
        //intent.putExtra(QR_CAMERA_MARGIN_TOP,50)
        //intent.putExtra(QR_CAMERA_MARGIN_RIGHT,50)
        //intent.putExtra(QR_CAMERA_MARGIN_BOTTOM,50)
        intent.putExtra(QR_CAMERA_BORDER, 50)
        intent.putExtra(QR_CAMERA_BORDER_COLOR, "#C1000000")
        intent.putExtra(QR_IS_SCAN_BAR, true)
        intent.putExtra(QR_IS_BEEP, true)
        intent.putExtra(QR_BEEP_RESOURCE_ID, R.raw.beep)

        startActivityForResult(intent, QR_QR_SCANNER_REQUEST)

    }

    private fun checkAndRequestPermissions(): Boolean {
        val camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val wtite =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val read =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (wtite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (read != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (listPermissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray(),
                REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_ID_MULTIPLE_PERMISSIONS -> {
                val perms: MutableMap<String, Int> = HashMap()
                // Initialize the map with both permissions
                perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] =
                    PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.CAMERA] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.READ_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
                // Fill with actual results from user
                if (grantResults.size > 0) {
                    var i = 0
                    while (i < permissions.size) {
                        perms[permissions[i]] = grantResults[i]
                        i++
                    }
                    // Check for both permissions
                    if (perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED && perms[Manifest.permission.CAMERA] == PackageManager.PERMISSION_GRANTED && perms[Manifest.permission.READ_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
                    ) {
                        scanQR()
                        // process the normal flow
//else any one or both the permissions are not granted
                    } else {
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
//show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ) || ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.CAMERA
                            ) || ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                        ) {
                            showDialogOK("Camera and Storage Permission required for this app",
                                DialogInterface.OnClickListener { dialog, which ->
                                    when (which) {
                                        DialogInterface.BUTTON_POSITIVE -> checkAndRequestPermissions()
                                        DialogInterface.BUTTON_NEGATIVE -> {
                                        }
                                    }
                                })
                        } else {
                            Toast.makeText(
                                    this,
                                    "Go to settings and enable permissions",
                                    Toast.LENGTH_LONG
                                )
                                .show()
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }
    }

    private fun showDialogOK(
        message: String,
        okListener: DialogInterface.OnClickListener
    ) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", okListener)
            .create()
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == QR_QR_SCANNER_REQUEST) {
            if (resultCode == RESULT_OK) {
                //tvSelected.text = data?.getStringExtra(QR_DATA) ?: "-1";
                val intent = Intent(this, ExibeArvore::class.java)
                intent.putExtra("qr_code", data?.getStringExtra(QR_DATA))
                startActivity(intent)
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    private fun configuraToolbarComNavDrawer() {
        val myToolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(myToolbar)
        //supportActionBar?.setDisplayShowHomeEnabled(true)
        mDrawerLayout = findViewById(R.id.drawer_layout)

        mDrawerToggle = ActionBarDrawerToggle(
            this,
            mDrawerLayout,
            myToolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )

        mDrawerLayout.addDrawerListener(mDrawerToggle)
        mDrawerLayout.post { mDrawerToggle.syncState() }

        //supportActionBar?.title = getString(R.string.fazenda)+": $fazendaCorrenteNome"
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            //R.id.nav_item_testar_amostra -> {
            //    startActivity(Intent(this, ExecutarTeste1Activity::class.java))
            //}

        }

        mDrawerLayout.closeDrawers()

        return true
    }

}
