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
            "CREATE TABLE Arvore (codigo TEXT NOT NULL PRIMARY KEY," +
                    " local TEXT, parcela INTEGER, linha INTEGER, bloco INTEGER, arvore_pos INTEGER, codigo_geno TEXT," +
                    " genitor_fem TEXT, genitor_mas TEXT, data_plantio TEXT, ult_medicao TEXT, dap INTEGER, altura INTEGER, vol REAL);",

            "CREATE TABLE Comentario (arvore_codigo TEXT, comentario TEXT, data TEXT);",

            "INSERT INTO Arvore (codigo, local, parcela, linha, bloco, arvore_pos, codigo_geno, genitor_fem, genitor_mas, data_plantio, ult_medicao, dap, altura, vol)"+
                    " VALUES ('P587B87A3', 'Viçosa', 587, 99, 87, 3, 'DG X U2', '(E. dunnii x E. grandis - Riocell) x (E. urophylla 2 - Cenibra)', 'Desconhecido (pol. Livre)', '18/02/2007', '20/05/2019', 20, 22, 0.18);",
            "INSERT INTO Arvore (codigo, local, parcela, linha, bloco, arvore_pos, codigo_geno, genitor_fem, genitor_mas, data_plantio, ult_medicao, dap, altura, vol)"+
                    " VALUES ('P2089B338A1', 'Viçosa', 2089, 14, 338, 1, 'DG X U2', '(E. dunnii x E. grandis - Riocell) x (E. urophylla 2 - Cenibra)', 'Desconhecido (pol. Livre)', '27/08/2011', '21/05/2019', 21, 23, 0.19);",
            "INSERT INTO Arvore (codigo, local, parcela, linha, bloco, arvore_pos, codigo_geno, genitor_fem, genitor_mas, data_plantio, ult_medicao, dap, altura, vol)"+
                    " VALUES ('P4361B729A5', 'Viçosa', 4361, 37, 729, 5, 'U1 X U2', '(E. urophylla - IP) x (E. urophylla - Cenibra)', 'Desconhecido (pol. Livre)', '20/03/2008', '22/05/2019', 22, 24, 0.20);",
            "INSERT INTO Arvore (codigo, local, parcela, linha, bloco, arvore_pos, codigo_geno, genitor_fem, genitor_mas, data_plantio, ult_medicao, dap, altura, vol)"+
                    " VALUES ('P6722B1200A61', 'Viçosa', 6722, 49, 1200, 61, 'U1 X U2', '(E. urophylla - IP) x (E. urophylla - Cenibra)', 'Desconhecido (pol. Livre)', '24/08/2003', '23/05/2019', 23, 25, 0.21);",
            "INSERT INTO Arvore (codigo, local, parcela, linha, bloco, arvore_pos, codigo_geno, genitor_fem, genitor_mas, data_plantio, ult_medicao, dap, altura, vol)"+
                    " VALUES ('P1040B156A103', 'Viçosa', 1040, 20, 156, 103, 'DG X U2', '(E. dunnii x E. grandis - Riocell) x (E. urophylla 2 - Cenibra)', 'Desconhecido (pol. Livre)', '13/07/2017', '24/05/2019', 24, 26, 0.22);",
            "INSERT INTO Arvore (codigo, local, parcela, linha, bloco, arvore_pos, codigo_geno, genitor_fem, genitor_mas, data_plantio, ult_medicao, dap, altura, vol)"+
                    " VALUES ('P4769B805A1', 'Viçosa', 4769, 39, 805, 1, 'DG X U2', '(E. dunnii x E. grandis - Riocell) x (E. urophylla 2 - Cenibra)', 'Desconhecido (pol. Livre)', '14/07/2017', '25/05/2019', 25, 27, 0.23);",
            "INSERT INTO Arvore (codigo, local, parcela, linha, bloco, arvore_pos, codigo_geno, genitor_fem, genitor_mas, data_plantio, ult_medicao, dap, altura, vol)"+
                    " VALUES ('P7092B1276A4', 'Viçosa', 7092, 51, 1276, 4, 'DG X C1 ', '(E. dunnii x E. grandis - Riocell) x (E. camaldulensis - Vallourec)', 'Desconhecido (pol. Livre)', '15/07/2017', '26/05/2019', 26, 28, 0.24);",
            "INSERT INTO Arvore (codigo, local, parcela, linha, bloco, arvore_pos, codigo_geno, genitor_fem, genitor_mas, data_plantio, ult_medicao, dap, altura, vol)"+
                    " VALUES ('P2085B337A2', 'Viçosa', 2085, 25, 337, 2, 'G1 X UGL', '(E. grandis - Votorantim) x (E. urophylla x E. globulus - Riocell)', 'Desconhecido (pol. Livre)', '16/07/2017', '27/05/2019', 27, 29, 0.25);",
            "INSERT INTO Arvore (codigo, local, parcela, linha, bloco, arvore_pos, codigo_geno, genitor_fem, genitor_mas, data_plantio, ult_medicao, dap, altura, vol)"+
                    " VALUES ('P1342B201A7', 'Viçosa', 1342, 22, 201, 7, 'G1 X UGL', '(E. grandis - Votorantim) x (E. urophylla x E. globulus - Riocell)', 'Desconhecido (pol. Livre)', '17/07/2017', '28/05/2019', 28, 30, 0.26);",
            "INSERT INTO Arvore (codigo, local, parcela, linha, bloco, arvore_pos, codigo_geno, genitor_fem, genitor_mas, data_plantio, ult_medicao, dap, altura, vol)"+
                    " VALUES ('P4899B828A6', 'Viçosa', 4899, 40, 828, 6, 'DG X UGL', '(E. dunnii x E. grandis - Riocell) x (E. urophylla x E. globulus - Riocell)', 'Desconhecido (pol. Livre)', '18/07/2017', '29/05/2019', 29, 31, 0.27);"
        )

        for (element in SCRIPT_DATABASE_CREATE) {
            db.execSQL(element)
        }

        Log.i("BANCO_DADOS", "Criou tabelas do banco e as populou.");

    }


}