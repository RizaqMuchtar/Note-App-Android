package com.example.note;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "DB_NOTE";
    private static final int DB_VERSION = 1;

    // Tabel dan Kolom
    private static final String TABLE_NOTE = "NOTE";
    private static final String ID_NOTE = "idNote";
    private static final String JUDUL_NOTE = "judulNote";
    private static final String NAME_NOTE = "nameNote";

    public DataBaseHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_NOTE = "CREATE TABLE " + TABLE_NOTE + " ( "
                + ID_NOTE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + JUDUL_NOTE + " TEXT, "
                + NAME_NOTE + " TEXT )";
        System.out.println("Create Table : " + CREATE_TABLE_NOTE);
        db.execSQL(CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        onCreate(db);
    }

    // Menambahkan data
    public void AddNote(NoteClass noteClass) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(JUDUL_NOTE, noteClass.getJudulList()); // Menyesuaikan nama properti
        contentValues.put(NAME_NOTE, noteClass.getNameList());

        db.insert(TABLE_NOTE, null, contentValues);
        db.close();
    }

    // Menghapus data
    public void DeleteNote(NoteClass noteClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE, ID_NOTE + " = ? ", new String[]{String.valueOf(noteClass.getIdList())});
        db.close();
    }

    // Mengedit data
    public int EditNote(NoteClass noteClass) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(JUDUL_NOTE, noteClass.getJudulList());
        contentValues.put(NAME_NOTE, noteClass.getNameList());

        return db.update(TABLE_NOTE, contentValues, ID_NOTE + " = ? ", new String[]{String.valueOf(noteClass.getIdList())});
    }

    // Menampilkan isi dari tabel
    public List<NoteClass> getAllNotes() {
        List<NoteClass> noteClassList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NOTE;
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                NoteClass noteClass = new NoteClass();
                noteClass.setIdList(Integer.parseInt(cursor.getString(0)));
                noteClass.setJudulList(cursor.getString(1)); // Judul
                noteClass.setNameList(cursor.getString(2)); // Note

                noteClassList.add(noteClass);
            } while (cursor.moveToNext());
        }
        return noteClassList;
    }
}
