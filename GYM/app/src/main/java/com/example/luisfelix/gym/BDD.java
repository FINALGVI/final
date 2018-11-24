package com.example.luisfelix.gym;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDD extends SQLiteOpenHelper {

    //String que contiene el Query para crear la BDD
    public String crearTabla = "Create table Rutinas(idRutina integer Primary Key autoincrement, nombreRutina text, rutinaPiernas text, rutinaBrazos text, rutinaPecho text, rutinaEspalda text, rutinaDescripcion)";

    public BDD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Método onCreate de la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(crearTabla);
    }

    //Al actualizar la base de datos (onUpgrade) se ejecuta eese pedazo de codigo (DROP TABLE y posteirormente la crea de nuevo)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if exists Rutinas");
        db.execSQL(crearTabla);

    }
}