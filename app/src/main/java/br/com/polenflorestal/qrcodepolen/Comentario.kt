package br.com.polenflorestal.qrcodepolen

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.FieldValue
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class Comentario() : Parcelable {
    var id: String = UUID.randomUUID().toString()
    var dataCriacao: String =
        SimpleDateFormat("dd/MM/yyyy").format(Timestamp(System.currentTimeMillis()))
    var criacaoTimestamp : Long = System.currentTimeMillis()
    var texto : String = DEFAULT_STRING_VALUE
    var arvoreCodigo : String = DEFAULT_STRING_VALUE

    constructor(parcel: Parcel) : this() {
        id = parcel.readString() ?: DEFAULT_STRING_VALUE
        dataCriacao = parcel.readString() ?: DEFAULT_STRING_VALUE
        criacaoTimestamp = parcel.readLong()
        texto = parcel.readString() ?: DEFAULT_STRING_VALUE
        arvoreCodigo = parcel.readString() ?: DEFAULT_STRING_VALUE
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(dataCriacao)
        parcel.writeLong(criacaoTimestamp)
        parcel.writeString(texto)
        parcel.writeString(arvoreCodigo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Comentario> {
        override fun createFromParcel(parcel: Parcel): Comentario {
            return Comentario(parcel)
        }

        override fun newArray(size: Int): Array<Comentario?> {
            return arrayOfNulls(size)
        }
    }
}