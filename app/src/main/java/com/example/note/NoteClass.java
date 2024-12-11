package com.example.note;

public class NoteClass {
    private int idList;
    private String nameList;
    private String judulList;

    // Konstruktor default
    public NoteClass() {
        // Tidak perlu panggil super() karena tidak ada konstruktor induk yang harus dipanggil
    }

    // Konstruktor dengan parameter untuk inisialisasi semua atribut
    public NoteClass(int idList, String nameList, String judulList) {
        this.idList = idList;
        this.nameList = nameList;
        this.judulList = judulList;
    }

    // Konstruktor hanya dengan nameListToDo
    public NoteClass(String nameList) {
        this.nameList = nameList;
    }

    // Getter dan Setter untuk idListToDo
    public int getIdList() {
        return idList;
    }

    public void setIdList(int idList) {
        this.idList = idList;
    }

    // Getter dan Setter untuk nameListToDo
    public String getNameList() {
        return nameList;
    }

    public void setNameList(String nameList) {
        this.nameList = nameList;
    }

    // Getter dan Setter untuk judulListToDo
    public String getJudulList() {
        return judulList;
    }

    public void setJudulList(String judulList) {
        this.judulList = judulList;
    }
}

