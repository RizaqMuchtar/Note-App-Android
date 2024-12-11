package com.example.note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ListViewHolder> {
    private final List<NoteClass> noteList;  // Menggunakan NoteClass
    private final KegiatanListener hapusListener;
    private final KegiatanListener ubahListener;

    // Konstruktor dengan parameter yang tepat
    public NoteAdapter(List<NoteClass> noteList, KegiatanListener hapusListener, KegiatanListener ubahListener) {
        this.noteList = noteList;
        this.hapusListener = hapusListener;
        this.ubahListener = ubahListener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        NoteClass note = noteList.get(position);  // Menggunakan NoteClass
        holder.mtvJudulKegiatan.setText(note.getJudulList());  // Mengambil judul dari NoteClass
        holder.mtvNamaKegiatan.setText(note.getNameList());  // Mengambil catatan dari NoteClass
    }

    @Override
    public int getItemCount() {
        return noteList.size();  // Menggunakan noteList
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private final TextView mtvNamaKegiatan;
        private final TextView mtvJudulKegiatan;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            mtvNamaKegiatan = itemView.findViewById(R.id.tvNamaKegiatan);
            mtvJudulKegiatan = itemView.findViewById(R.id.tvJudulKegiatan);

            ImageButton mbtnUbahKegiatan = itemView.findViewById(R.id.btnUbahKegiatan);
            ImageButton mbtnHapusKegiatan = itemView.findViewById(R.id.btnHapuskegiatan);

            // Menghubungkan listener pada tombol
            mbtnUbahKegiatan.setOnClickListener(v -> ubahListener.onUbahKegiatan(getAdapterPosition()));
            mbtnHapusKegiatan.setOnClickListener(v -> hapusListener.onHapusKegiatan(getAdapterPosition()));
        }
    }

    public interface KegiatanListener {
        void onHapusKegiatan(int position);
        void onUbahKegiatan(int position);
    }
}
