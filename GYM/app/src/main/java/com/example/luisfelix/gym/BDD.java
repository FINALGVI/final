package com.example.luisfelix.gym;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class BDD extends SQLiteOpenHelper {

    //String que contiene el Query para crear la BDD
    public String crearTabla = "Create table Rutinas(idRutina integer Primary Key autoincrement, nombreRutina text, rutinaPiernas text, rutinaBrazos text, rutinaPecho text, rutinaEspalda text, rutinaDescripcion)";

    public String crearTabla2 = "Create table Salud(id integer primary key autoincrement, agua text, peso text, dormir text, fecha text)";

    public String crearTabla3 = "Create table Nombre(id integer primary key autoincrement, nombre text)";

    public BDD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    //Método onCreate de la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(crearTabla);
        db.execSQL(crearTabla2);
        db.execSQL(crearTabla3);
    }

    //Al actualizar la base de datos (onUpgrade) se ejecuta eese pedazo de codigo (DROP TABLE y posteirormente la crea de nuevo)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if exists Rutinas");
        db.execSQL(crearTabla);

        db.execSQL("DROP TABLE if exists Salud");
        db.execSQL(crearTabla2);

        db.execSQL("DROP TABLE if exists Nombre");
        db.execSQL(crearTabla3);
    }

    public ArrayList llenar_lv() {
        ArrayList<String> lista = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT * FROM Salud";
        Cursor registros = database.rawQuery(q, null);
        if (registros.moveToFirst()) {
            do {
                lista.add(registros.getString(4)+"\n"+ registros.getString(1)+" vasos de agua"
                        + "\t, "+registros.getString(2)+" libras." + "\n" + registros.getString(3));
            } while (registros.moveToNext());
        }
        return lista;
    }
}