package com.example.note;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteAdapter.KegiatanListener {
    private ImageButton mbtnTambah;
    private RecyclerView rvList;

    DataBaseHandler db;
    private List<NoteClass> noteList;  // Menggunakan NoteClass
    private NoteAdapter noteAdapter;  // Menggunakan NoteAdapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup RecyclerView
        rvList = findViewById(R.id.rv_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(layoutManager);

        // Setup Button untuk tambah catatan
        mbtnTambah = findViewById(R.id.btnTambah);
        mbtnTambah.setOnClickListener(v -> showForm(null, "", ""));  // Mode create baru

        db = new DataBaseHandler(this);

        loadData();  // Memuat data setelah aktivitas dibuat
    }

    private void showForm(NoteClass noteClass, String existingTitle, String existingNote) {
        AlertDialog.Builder formBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.form_note, null);
        formBuilder.setView(view);

        AlertDialog popup = formBuilder.create();
        popup.show();

        EditText etJudul = view.findViewById(R.id.etJudul);
        EditText etNote = view.findViewById(R.id.etNote);
        Button btnSave = view.findViewById(R.id.save_data);

        // Set judul dan note untuk mode edit atau create
        if (noteClass != null) {
            etJudul.setText(existingTitle != null ? existingTitle : "");
            etNote.setText(existingNote != null ? existingNote : "");
        } else {
            etJudul.setText("");  // Bersihkan EditText untuk judul
            etNote.setText("");  // Bersihkan EditText untuk note
        }

        btnSave.setOnClickListener(v -> {
            String judulText = etJudul.getText().toString();
            String noteText = etNote.getText().toString();

            if (judulText.isEmpty() || noteText.isEmpty()) {
                Toast.makeText(this, "Judul dan Catatan tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            } else {
                if (noteClass != null) {
                    // Edit mode
                    noteClass.setJudulList(judulText);
                    noteClass.setNameList(noteText);
                    db.EditNote(noteClass);  // Menggunakan method EditNote
                } else {
                    // Create mode
                    NoteClass newNote = new NoteClass();
                    newNote.setJudulList(judulText);
                    newNote.setNameList(noteText);
                    db.AddNote(newNote);  // Menggunakan method AddNote
                }
                loadData();  // Memperbarui data setelah penyimpanan
                popup.dismiss();
            }
        });
    }

    private void loadData() {
        noteList = db.getAllNotes();  // Menggunakan method getAllNotes
        noteAdapter = new NoteAdapter(noteList, this, this);  // Menggunakan adapter yang benar
        rvList.setAdapter(noteAdapter);  // Memasang adapter pada RecyclerView
    }

    @Override
    public void onHapusKegiatan(int position) {
        NoteClass selectedNote = noteList.get(position);
        db.DeleteNote(selectedNote);  // Menggunakan method DeleteNote
        Toast.makeText(this, "Catatan Berhasil Dihapus", Toast.LENGTH_SHORT).show();
        loadData();  // Memperbarui data setelah hapus
    }

    @Override
    public void onUbahKegiatan(int position) {
        NoteClass selectedNote = noteList.get(position);
        String ubahJudul = selectedNote.getJudulList();
        String ubahNote = selectedNote.getNameList();
        showForm(selectedNote, ubahJudul, ubahNote);  // Mode edit
    }
}
