package br.com.polenflorestal.qrcodepolen

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.Toolbar
import java.text.SimpleDateFormat
import java.util.*


class ExibeArvore : AppCompatActivity() {
    lateinit var codigo: String
    var arvore_pos: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        codigo = intent.getStringExtra("qr_code")
        DataBaseUtil.abrir(this)
        var cursor = DataBaseUtil.buscar("Arvore", arrayOf<String>(), "codigo = '$codigo'", "")
        val comentarios = DataBaseUtil.buscar("Comentario", arrayOf<String>(), "arvore_codigo = '$codigo'", "")

        if (cursor?.count ?: 0 <= 0) {
            ToastUtil.show(this, "Código não encontrado no Banco de Dados!", Toast.LENGTH_LONG)
            finish()
        }

        while (cursor?.moveToNext()!!) {
            val tipo = cursor?.getInt(cursor.getColumnIndex("tipo"))
            val local = cursor?.getString(cursor.getColumnIndex("local"))
            val parcela = cursor?.getInt(cursor.getColumnIndex("parcela"))
            val linha = cursor?.getInt(cursor.getColumnIndex("linha"))
            val bloco = cursor?.getInt(cursor.getColumnIndex("bloco"))
            arvore_pos = cursor?.getInt(cursor.getColumnIndex("arvore_pos"))
            val codigo_geno = cursor?.getString(cursor.getColumnIndex("codigo_geno"))
            val genitor_fem = cursor?.getString(cursor.getColumnIndex("genitor_fem"))
            val genitor_mas = cursor?.getString(cursor.getColumnIndex("genitor_mas"))
            val data_plantio = cursor?.getString(cursor.getColumnIndex("data_plantio"))
            val ult_medicao = cursor?.getString(cursor.getColumnIndex("ult_medicao"))
            val dap = cursor?.getString(cursor.getColumnIndex("dap"))
            val altura = cursor?.getString(cursor.getColumnIndex("altura"))
            val vol = cursor?.getString(cursor.getColumnIndex("vol"))
            val procedencia = cursor?.getString(cursor.getColumnIndex("procedencia"))
            val historico = cursor?.getString(cursor.getColumnIndex("historico"))
            val especie_comp = cursor?.getString(cursor.getColumnIndex("especie_comp"))

            if (tipo == 0) {
                setContentView(R.layout.activity_exibe_arvore)

                findViewById<TextView>(R.id.local).text = local
                findViewById<TextView>(R.id.data_plantio).text =
                    SimpleDateFormat("dd/MM/yyyy").format(
                        SimpleDateFormat("MM/dd/yyyy").parse(data_plantio)
                    )
                findViewById<TextView>(R.id.talhao).text = bloco.toString()
                findViewById<TextView>(R.id.individuo).text = arvore_pos.toString()
                findViewById<TextView>(R.id.especie).text = codigo_geno
                findViewById<TextView>(R.id.genitores).text = genitor_fem

                findViewById<TextView>(R.id.ult_medicao).text =
                    SimpleDateFormat("dd/MM/yyyy").format(
                        SimpleDateFormat("MM/dd/yyyy").parse(ult_medicao)
                    )
                findViewById<TextView>(R.id.dap).text = "$dap cm"
                findViewById<TextView>(R.id.altura).text = "$altura m"
                findViewById<TextView>(R.id.vol).text = "$vol m³"

                while (comentarios?.moveToNext()!!) {
                    var data = comentarios.getString(comentarios.getColumnIndex("data"))
                    //data = SimpleDateFormat("dd/MM/yyyy").format(SimpleDateFormat("MM/dd/yyyy").parse(data))
                    val com = comentarios.getString(comentarios.getColumnIndex("comentario"))
                    exibeComentario(data, com)
                }

            } else {
                setContentView(R.layout.activity_exibe_arvore1)

                findViewById<ImageView>(R.id.empresa_logo).setImageDrawable(getDrawable(R.drawable.empresa_icon2))

                val uri =
                    "@drawable/" + codigo.toLowerCase() + "_1" // where myresource (without the extension) is the file
                val imageResource = resources.getIdentifier(uri, null, packageName)
                findViewById<ImageView>(R.id.arvore_img_1).setImageResource(imageResource)

                findViewById<TextView>(R.id.local).text = local
                findViewById<TextView>(R.id.data_plantio).text =
                    SimpleDateFormat("dd/MM/yyyy").format(
                        SimpleDateFormat("MM/dd/yyyy").parse(data_plantio)
                    )
                //findViewById<TextView>(R.id.talhao).text = bloco.toString()
                findViewById<TextView>(R.id.individuo).text = arvore_pos.toString()
                findViewById<TextView>(R.id.especie).text = codigo_geno
                findViewById<TextView>(R.id.especie_comp).text = especie_comp
                //findViewById<TextView>(R.id.genitores).text = genitor_fem

                //findViewById<TextView>(R.id.ult_medicao).text = SimpleDateFormat("dd-MM-yyyy").parse(ult_medicao).toString()
                //findViewById<TextView>(R.id.dap).text = "$dap cm"
                //findViewById<TextView>(R.id.altura).text = "$altura m"
                //findViewById<TextView>(R.id.vol).text = "$vol m³"

                findViewById<TextView>(R.id.procedencia).text = procedencia
                findViewById<TextView>(R.id.historico).text = historico
            }

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

    fun abrirCroqui(view: View) {
        val intent = Intent(this, Croqui::class.java)
        intent.putExtra("arvore_pos", arvore_pos)
        startActivity(intent)
    }

    fun exibeComentario(data: String, com: String){
        val params = LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT)
        params.topMargin = 5

        val com1 = TextView(this)
        val temp : String = "- $data: $com"
        com1.text = temp
        com1.setTextColor(Color.parseColor("#1433DC"))
        com1.gravity = Gravity.START or Gravity.CENTER_VERTICAL
        com1.layoutParams = params
        findViewById<LinearLayout>(R.id.linear_comentarios).addView(com1)
    }

    fun adicionarComentario(view: View) {
        var comentario: String = ""
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Adicionar Comentário")

// Set up the input

// Set up the input
        val input = EditText(this)
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        // Set up the buttons
        builder.setPositiveButton(
            "OK"
        ) { dialog, which ->
            comentario = input.text.toString()

            if(comentario == "" || comentario.length == 0){
                ToastUtil.show(this, "Erro ao inserir comentário: comentário vazio!", Toast.LENGTH_LONG)
            }
            else{
                val todayDate: Date = Calendar.getInstance().time
                val formatter = SimpleDateFormat("dd/MM/yyyy")
                val todayString = formatter.format(todayDate)

                val valores = ContentValues()
                valores.put("arvore_codigo", codigo)
                valores.put("comentario", comentario)
                valores.put("data", todayString)
                DataBaseUtil.inserir("Comentario", valores)

                exibeComentario(todayString, comentario)
            }
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, which -> dialog.cancel() }

        builder.show()
    }
}
