package br.com.polenflorestal.qrcodepolen

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log

object DataBaseUtil {
    private val NOME_DB: String = "qrcode_polen_db"
    private lateinit var db: SQLiteDatabase

    fun abrir(ctx: Context) {
        db = MyApp.context.openOrCreateDatabase(NOME_DB, Context.MODE_PRIVATE, null)
        Log.i("BANCO_DADOS", "Abriu conexão com o banco.");
    }

    fun inserir(tabela: String, valores: ContentValues): Long {
        val id = db.insert(tabela, null, valores)
        Log.i("BANCO_DADOS", "Cadastrou registro com o id [$id]")
        return id
    }

    fun atualizar(
        tabela: String?,
        valores: ContentValues?,
        where: String?
    ): Int {
        val count = db.update(tabela, valores, where, null)
        Log.i("BANCO_DADOS", "Atualizou [$count] registros")
        return count
    }

    fun deletar(tabela: String?, where: String?): Int {
        val count = db.delete(tabela, where, null)
        Log.i("BANCO_DADOS", "Deletou [$count] registros")
        return count
    }

    fun buscar(
        tabela: String?,
        colunas: Array<String>,
        where: String,
        orderBy: String?
    ): Cursor? {
        val c: Cursor
        c = if (where != "") db.query(
            tabela,
            colunas,
            where,
            null,
            null,
            null,
            orderBy
        ) else db.query(tabela, colunas, null, null, null, null, orderBy)
        Log.i(
            "BANCO_DADOS",
            "Realizou uma busca e retornou [" + c.count.toString() + "] registros."
        )
        return c
    }

    fun fechar() {
        if (db != null) {
            db.close()
            Log.i("BANCO_DADOS", "Fechou conexão com o Banco.")
        }
    }

    fun criaDB(){
        val SCRIPT_DATABASE_CREATE = arrayOf(
            "CREATE TABLE [Arvore] ([codigo] Text, [tipo] Integer, [local] Text, [parcela] Integer, [linha] Integer, [bloco] Integer, [arvore_pos] Integer, [codigo_geno] Text, [genitor_fem] Text, [genitor_mas] Text, [data_plantio] Text, [ult_medicao] Text, [dap] Integer, [altura] Integer, [vol] Float, [procedencia] Text, [historico] Text);",

            "CREATE TABLE Comentario (arvore_codigo TEXT, comentario TEXT, data TEXT);",

            "INSERT INTO [Arvore]([rowid], [codigo], [tipo], [local], [parcela], [linha], [bloco], [arvore_pos], [codigo_geno], [genitor_fem], [genitor_mas], [data_plantio], [ult_medicao], [dap], [altura], [vol], [procedencia], [historico]) VALUES(1, 'P587B87A3', 0, 'Viçosa', 587, 99, 87, 3, 'DG X U2', '(E. dunnii x E. grandis - Riocell) x (E. urophylla 2 - Cenibra)', 'Desconhecido (pol. Livre)', '2/18/2007 12:00:00 AM', '5/20/2019 12:00:00 AM', 20, 22, 0.18, null, null);",
            "INSERT INTO [Arvore]([rowid], [codigo], [tipo], [local], [parcela], [linha], [bloco], [arvore_pos], [codigo_geno], [genitor_fem], [genitor_mas], [data_plantio], [ult_medicao], [dap], [altura], [vol], [procedencia], [historico]) VALUES(2, 'P2089B338A1', 1, 'Viçosa', 2089, 14, 338, 1, 'Paubrasilia echinata', '(E. dunnii x E. grandis - Riocell) x (E. urophylla 2 - Cenibra)', 'Desconhecido (pol. Livre)', '8/27/2011 12:00:00 AM', '5/21/2019 12:00:00 AM', 21, 23, 0.19, 'Mariana-MG', null);",
            "INSERT INTO [Arvore]([rowid], [codigo], [tipo], [local], [parcela], [linha], [bloco], [arvore_pos], [codigo_geno], [genitor_fem], [genitor_mas], [data_plantio], [ult_medicao], [dap], [altura], [vol], [procedencia], [historico]) VALUES(3, 'P4361B729A5', 0, 'Viçosa', 4361, 37, 729, 5, 'U1 X U2', '(E. urophylla - IP) x (E. urophylla - Cenibra)', 'desconhecido (pol. Livre)', '3/20/2008 12:00:00 AM', '5/22/2019 12:00:00 AM', 22, 24, 0.2, null, null);",
            "INSERT INTO [Arvore]([rowid], [codigo], [tipo], [local], [parcela], [linha], [bloco], [arvore_pos], [codigo_geno], [genitor_fem], [genitor_mas], [data_plantio], [ult_medicao], [dap], [altura], [vol], [procedencia], [historico]) VALUES(4, 'P6722B1200A61', 0, 'Viçosa', 6722, 49, 1200, 61, 'U1 X U2', '(E. urophylla - IP) x (E. urophylla - Cenibra)', 'desconhecido (pol. Livre)', '8/24/2003 12:00:00 AM', '5/23/2019 12:00:00 AM', 23, 25, 0.21, null, null);",
            "INSERT INTO [Arvore]([rowid], [codigo], [tipo], [local], [parcela], [linha], [bloco], [arvore_pos], [codigo_geno], [genitor_fem], [genitor_mas], [data_plantio], [ult_medicao], [dap], [altura], [vol], [procedencia], [historico]) VALUES(5, 'P1040B156A103', 0, 'Viçosa', 1040, 20, 156, 103, 'DG X U2', '(E. dunnii x E. grandis - Riocell) x (E. urophylla 2 - Cenibra)', 'desconhecido (pol. Livre)', '7/13/2017 12:00:00 AM', '5/24/2019 12:00:00 AM', 24, 26, 0.22, null, null);",
            "INSERT INTO [Arvore]([rowid], [codigo], [tipo], [local], [parcela], [linha], [bloco], [arvore_pos], [codigo_geno], [genitor_fem], [genitor_mas], [data_plantio], [ult_medicao], [dap], [altura], [vol], [procedencia], [historico]) VALUES(6, 'P4769B805A1', 1, 'Viçosa', 4769, 39, 805, 1, 'Apuleia leiocarpa', '(E. dunnii x E. grandis - Riocell) x (E. urophylla 2 - Cenibra)', 'desconhecido (pol. Livre)', '7/14/2017 12:00:00 AM', '5/25/2019 12:00:00 AM', 25, 27, 0.23, 'Vale do Rio Doce', null);",
            "INSERT INTO [Arvore]([rowid], [codigo], [tipo], [local], [parcela], [linha], [bloco], [arvore_pos], [codigo_geno], [genitor_fem], [genitor_mas], [data_plantio], [ult_medicao], [dap], [altura], [vol], [procedencia], [historico]) VALUES(7, 'P7092B1276A4', 0, 'Viçosa', 7092, 51, 1276, 4, 'DG X C1 ', '(E. dunnii x E. grandis - Riocell) x (E. camaldulensis - Vallourec)', 'desconhecido (pol. Livre)', '7/15/2017 12:00:00 AM', '5/26/2019 12:00:00 AM', 26, 28, 0.24, null, null);",
            "INSERT INTO [Arvore]([rowid], [codigo], [tipo], [local], [parcela], [linha], [bloco], [arvore_pos], [codigo_geno], [genitor_fem], [genitor_mas], [data_plantio], [ult_medicao], [dap], [altura], [vol], [procedencia], [historico]) VALUES(8, 'P2085B337A2', 0, 'Viçosa', 2085, 25, 337, 2, 'G1 X UGL', '(E. grandis - Votorantim) x (E. urophylla x E. globulus - Riocell)', 'desconhecido (pol. Livre)', '7/16/2017 12:00:00 AM', '5/27/2019 12:00:00 AM', 27, 29, 0.25, null, null);",
            "INSERT INTO [Arvore]([rowid], [codigo], [tipo], [local], [parcela], [linha], [bloco], [arvore_pos], [codigo_geno], [genitor_fem], [genitor_mas], [data_plantio], [ult_medicao], [dap], [altura], [vol], [procedencia], [historico]) VALUES(9, 'P1342B201A7', 0, 'Viçosa', 1342, 22, 201, 7, 'G1 X UGL', '(E. grandis - Votorantim) x (E. urophylla x E. globulus - Riocell)', 'desconhecido (pol. Livre)', '7/17/2017 12:00:00 AM', '5/28/2019 12:00:00 AM', 28, 30, 0.26, null, null);",
            "INSERT INTO [Arvore]([rowid], [codigo], [tipo], [local], [parcela], [linha], [bloco], [arvore_pos], [codigo_geno], [genitor_fem], [genitor_mas], [data_plantio], [ult_medicao], [dap], [altura], [vol], [procedencia], [historico]) VALUES(10, 'P4899B828A6', 0, 'Viçosa', 4899, 40, 828, 6, 'DG X UGL', '(E. dunnii x E. grandis - Riocell) x (E. urophylla x E. globulus - Riocell)', 'desconhecido (pol. Livre)', '7/18/2017 12:00:00 AM', '5/29/2019 12:00:00 AM', 29, 31, 0.27, null, null);"
        )

        for (element in SCRIPT_DATABASE_CREATE) {
            db.execSQL(element)
        }

        Log.i("BANCO_DADOS", "Criou tabelas do banco e as populou.");

    }


}