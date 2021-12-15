package com.example.VocabularyPractice.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

public class DatabaseManager {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    Context context;
    private static DatabaseManager instance;
    Cursor cmd = null;

    private DatabaseManager(Context context){
        this.context=context;
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseManager getInstance(Context context){

        if(instance==null){
            instance = new DatabaseManager(context);
        }
        return instance;
    }


    // Veritabanı açma-kapama işlemmleri
    public void open(){
        this.db = openHelper.getWritableDatabase();
    }

    public void close(){
        if(db!=null){
            this.db.close();
        }
    }


    //level id si ve status durumuna göre kaç adet veri olduğunu döndüren status counter
    public String get_status_count(int level_id, int status_id){
        
        StringBuffer buffer = new StringBuffer();
        
        this.open();
        cmd = db.rawQuery("SELECT COUNT(*) FROM tbl_words WHERE level_id='"+level_id+"' AND status_id='"+status_id+"'",new String[]{});
        
        
        while(cmd.moveToNext()){
            String example = cmd.getString(0);
            buffer.append(""+example);
        }
        
        this.close();
        return buffer.toString();
    }

    // istediğimiz seviyedeki tüm kelimeleri döndüren fonksiyon.
    public String getAll_LevelCount() {

        String gidecek = "";

        this.open();

        cmd = db.rawQuery("SELECT Count(*) FROM tbl_level ", new String[]{});


        while (cmd.moveToNext()) {
            gidecek  = cmd.getString(0);
        }

        this.close();
        return gidecek;
    }


    // istediğimiz seviyedeki tüm kelimeleri döndüren fonksiyon.
    public String[] getAll_Words(int level_id){

        String[] gidecek = new String[30];
        int i=0;

        this.open();

        cmd = db.rawQuery("SELECT word FROM tbl_words WHERE level_id='"+level_id+"'" ,new String[]{});

        while(cmd.moveToNext()){
            String example = cmd.getString(0);
            gidecek[i] = example;
            i++;
        }

        this.close();
        return gidecek;
    }


    // istediğimiz seviyedeki tüm kelimelerin açıklamalarını döndüren fonksiyon.
    public String[] getAll_Explanation(int level_id){

        String[] gidecek = new String[30];
        int i=0;

        this.open();

        cmd = db.rawQuery("SELECT explanation FROM tbl_words WHERE level_id='"+level_id+"'" ,new String[]{});

        while(cmd.moveToNext()){
            String example = cmd.getString(0);
            gidecek[i] = example;
            i++;
        }

        this.close();
        return gidecek;
    }


    // istediğimiz seviyedeki tüm kelimelerin örneklerini döndüren fonksiyon.
    public String[] getAll_Exampleş(int level_id) {

        String[] gidecek = new String[30];
        int i=0;

        this.open();
        cmd = db.rawQuery("select example from tbl_words where level_id ='" + level_id + "'", new String[]{});

        while (cmd.moveToNext()) {
            String example = cmd.getString(0);
            gidecek[i] = example;
            i++;
        }

        this.close();
        return gidecek;

    }


    // istediğimiz kelimenin durumunu döndüren fonksiyon.
    public String get_Status(String word) {

        String gidecek = "";
        int i=0;

        this.open();
        cmd = db.rawQuery("select status from tbl_status s,tbl_words w where s.status_id= w.status_id AND w.word ='" + word + "'", new String[]{});

        while (cmd.moveToNext()) {
            String example = cmd.getString(0);
            gidecek += example;
            i++;
        }

        this.close();
        return gidecek;

    }


    // kelimenin durumunu güncelleyen fonksiyon.
    public void set_Word_status(String word,String newStatus){
        int newStatus_id=1;

       switch(newStatus) {
            case "Learning":
                newStatus_id=1;
                break;
            case "Reviewing":
                newStatus_id=2;
                break;
            case "Mastered":
                newStatus_id=3;
                break;
            default:
                newStatus_id=3;
        }


        this.open();

       try {
           String data = "UPDATE tbl_words SET status_id= ? WHERE word=?";
           SQLiteStatement statement = db.compileStatement(data);

           statement.bindString(1, String.valueOf(newStatus_id));
           statement.bindString(2, word);
           statement.execute();

           this.close();
       }
           catch(Exception e)
        {
            Toast.makeText(context,"Durum güncellenemedi!!",Toast.LENGTH_LONG).show();
        }
    }



    // çevirisini yaptığımız kelimeyi veritabanına kaydeden fonksiyon
    public void save_in_MyWords(String word_in_eng, String word_in_tr){
            this.open();

        String data = "INSERT INTO tbl_myWord (word_in_eng, word_in_tr) VALUES(?,?)";
        SQLiteStatement statement = db.compileStatement(data);

        statement.bindString(1, word_in_eng);
        statement.bindString(2, word_in_tr);
        statement.execute();

            this.close();

    }


    // kullanıcının kaydettiği ingilizce kelimelerin tamamını döndüren fonksiyon
    public String[] getAll_EngWords(){
        String count="";
        this.open();
        cmd = db.rawQuery("SELECT COUNT(*) FROM tbl_myWord ",new String[]{});

        while(cmd.moveToNext()){
            count  = cmd.getString(0);
        }


        String[] gidecek= new String[Integer.parseInt(count)] ;
        int i=0;

        cmd = db.rawQuery("select word_in_eng from tbl_myWord  ", new String[]{});

        while (cmd.moveToNext()) {
            String example = cmd.getString(0);
            gidecek[i] = example;
            i++;
        }

        this.close();
        return gidecek;
    }


    // kullanıcının kaydettiği türkçe kelimelerin tamamını döndüren fonksiyon
    public String[] getAll_TrWords(){
        String count="";
        this.open();
        cmd = db.rawQuery("SELECT COUNT(*) FROM tbl_myWord ",new String[]{});

        while(cmd.moveToNext()){
            count  = cmd.getString(0);
        }


        String[] gidecek= new String[Integer.parseInt(count)] ;
        int i=0;

        cmd = db.rawQuery("select word_in_tr from tbl_myWord  ", new String[]{});

        while (cmd.moveToNext()) {
            String example = cmd.getString(0);
            gidecek[i] = example;
            i++;
        }

        this.close();
        return gidecek;
    }





}
