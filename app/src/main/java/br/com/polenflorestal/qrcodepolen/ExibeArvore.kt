package br.com.polenflorestal.qrcodepolen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class ExibeArvore : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exibe_arvore)

        val codigo : String = intent.getStringExtra("qr_code")
        DataBaseUtil.abrir(this)
        var cursor = DataBaseUtil.buscar("Arvore", arrayOf<String>(), "codigo = '$codigo'", "")

        while (cursor?.moveToNext()!!){
            val empresa = cursor?.getString(cursor.getColumnIndex("empresa_resgate"))
            val parcela = cursor?.getInt(cursor.getColumnIndex("parcela"))
            val linha = cursor?.getInt(cursor.getColumnIndex("linha"))
            val bloco = cursor?.getInt(cursor.getColumnIndex("bloco"))
            val arvore_pos = cursor?.getInt(cursor.getColumnIndex("arvore_pos"))
            val codigo_geno = cursor?.getString(cursor.getColumnIndex("codigo_geno"))
            val genitor_fem = cursor?.getString(cursor.getColumnIndex("genitor_fem"))
            val genitor_mas = cursor?.getString(cursor.getColumnIndex("genitor_mas"))
            val data_plantio = cursor?.getString(cursor.getColumnIndex("data_plantio"))

            findViewById<TextView>(R.id.data_plantio).text = data_plantio
            findViewById<TextView>(R.id.talhao).text = bloco.toString()
            findViewById<TextView>(R.id.individuo).text = arvore_pos.toString()
            findViewById<TextView>(R.id.especie).text = codigo_geno
            findViewById<TextView>(R.id.genitores).text = genitor_fem

            break
        }

        configuraToolbar()
    }

    private fun configuraToolbar() { // adiciona a barra de tarefas na tela
        val myToolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(myToolbar)
        // adiciona a seta de voltar na barra de tarefas
        supportActionBar?.setDisplayShowHomeEnabled(true)
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
