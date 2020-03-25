package br.com.polenflorestal.qrcodepolen

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class Croqui : AppCompatActivity() {

    var arvore_pos : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_croqui)

        arvore_pos = intent.getIntExtra("arvore_pos", 0)

        if(arvore_pos < 1 || arvore_pos > 88){
            ToastUtil.show(this, "Croqui não encontrado!", Toast.LENGTH_LONG)
            finish()
        }

        val cID : String = "c"+arvore_pos

        findViewById<TextView>( resources.getIdentifier(cID, "id", packageName)).setBackgroundColor(
            Color.parseColor("#ff0000"))

        configuraToolbar()
    }

    private fun configuraToolbar() { // adiciona a barra de tarefas na tela
        val myToolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(myToolbar)
        // adiciona a seta de voltar na barra de tarefas
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_18_1x)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.title =
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
