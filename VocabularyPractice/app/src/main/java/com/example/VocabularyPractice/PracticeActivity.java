package com.example.VocabularyPractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.VocabularyPractice.database.DatabaseManager;


public class PracticeActivity extends AppCompatActivity {
    private TextView txtWord, txtTapToSee,txtExplanation, txtExample, txtIKnew, txtIdidintKnow, txtMastered, txtReviewing, txtLearning, txtWordStatus;
    private ProgressBar pBarMastered, pBarReviewing, pBarLearning;
    String [] words, explanations, examples;
    String status;
    int level_id, sayi, eskisayi;







    // araçlar tanımlanıyor
    private void getTools(){

        txtWord = findViewById(R.id.txtWord);
        txtTapToSee = findViewById(R.id.txtTapToSee);
        txtExplanation = findViewById(R.id.txtExplanation);
        txtExample = findViewById(R.id.txtExample);
        txtIKnew = findViewById(R.id.txtIKnow);
        txtIdidintKnow = findViewById(R.id.txtIDontKnow);
        txtWordStatus = findViewById(R.id.txtWordStatus);
        pBarMastered = findViewById(R.id.pBarMastered);
        txtMastered = findViewById(R.id.txttP_mastered);
        pBarReviewing = findViewById(R.id.pBarReviewing);
        txtReviewing = findViewById(R.id.txtP_Reviewing);
        pBarLearning = findViewById(R.id.pBarLearning);
        txtLearning = findViewById(R.id.txtP_Learning);



    }

    // Kart-kelime detayı gösteriliyor
    private void show_CardDetails() {
        txtTapToSee.setVisibility(View.GONE);
        txtExplanation.setVisibility(View.VISIBLE);
        txtExample.setVisibility(View.VISIBLE);
        txtIKnew.setVisibility(View.VISIBLE);
        txtIdidintKnow.setVisibility(View.VISIBLE);

    }

    // Kart-kelime detayı gizleniyor
    private void hide_CardDetails(){
        txtTapToSee.setVisibility(View.VISIBLE);
        txtExplanation.setVisibility(View.GONE);
        txtExample.setVisibility(View.GONE);
        txtIKnew.setVisibility(View.GONE);
        txtIdidintKnow.setVisibility(View.GONE);

    }

    // kelimeler-açıklmalar-örnekler tek seferde veritabanından çekilip dizilere kaydediliyor
    private void get_Card_Datas(DatabaseManager db){
            words = db.getAll_Words(level_id);
            explanations = db.getAll_Explanation(level_id);
            examples = db.getAll_Exampleş(level_id);
    }

    // kelime durumuna göre textin yazısı,şekli ve rengi tanımlanıyor
    private void set_txtWordStatus(String status){

        if(status.equals("Mastered"))
        {
            txtWordStatus.setBackground(getResources().getDrawable(R.drawable.my_iknew));
            txtWordStatus.setTextColor(getResources().getColor(R.color.green));
        }
        else if(status.equals("Reviewing"))
        {
            txtWordStatus.setBackground(getResources().getDrawable(R.drawable.my_reviewing));
            txtWordStatus.setTextColor(getResources().getColor(R.color.orange));
        }
        else
        {
            txtWordStatus.setBackground(getResources().getDrawable(R.drawable.my_idontknow));
            txtWordStatus.setTextColor(getResources().getColor(R.color.red));
        }
            txtWordStatus.setText(status);

    }

    // yeni kelimeye geçerken yapılacak işlemler
    private void newWord(DatabaseManager db){

        do{
            sayi = (int)(Math.random()*30);}
        while (sayi == eskisayi);

        txtWord.setText(words[sayi]);
        txtExample.setText(examples[sayi]);
        txtExplanation.setText(explanations[sayi]);
        status = db.get_Status(words[sayi]);

        set_txtWordStatus(status);
        eskisayi = sayi;
    }

    //  hangi durumda kaçar kelimenin bulunduğu textlere yazılıyor
    private void setPbar_Texts(DatabaseManager db){

        txtMastered.setText("You have mastered "+db.get_status_count(level_id,3)+" of 30 words");
        txtReviewing.setText("You are reviewing "+db.get_status_count(level_id,2)+" of 30 words");
        txtLearning.setText("You are learning "+db.get_status_count(level_id,1)+" of 30 words");

    }

    // her durumdaki kelimenin sayısına göre progresslar ayarlanıyor
    private void setPbar_progress(DatabaseManager db){
        pBarMastered.setProgress(Integer.parseInt(db.get_status_count(level_id,3)));
        pBarReviewing.setProgress(Integer.parseInt(db.get_status_count(level_id,2)));
        pBarLearning.setProgress(Integer.parseInt(db.get_status_count(level_id,1)));

    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        // en üstte yazacak başlık ayarlaıyor
        String title = getIntent().getStringExtra("level_title");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(title);


        // başlığa göre (daha sonra kullanılmak) level_id belirleniyor.
        switch(title) {
            case "Common Words":
                level_id=1;
                break;
            case "Easy Words":
                level_id=2;
                break;
            case "Medium Words":
                level_id=3;
                break;
            case "Hard Words":
                level_id=4;
                break;
            default:
                level_id=1;
        }

        DatabaseManager db = DatabaseManager.getInstance(this);
        get_Card_Datas(db);
        getTools();
        newWord(db);
        setPbar_Texts(db);
        setPbar_progress(db);



        txtTapToSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                show_CardDetails();
            }
        });

        // kelime durumu güncelleniyor ve yeni kelimeye geçiliyor
        txtIKnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            if(String.valueOf(txtWordStatus.getText()).equals("Learning"))
            {
                db.set_Word_status(String.valueOf(txtWord.getText()),"Reviewing");
            }

            else if (String.valueOf(txtWordStatus.getText()).equals("Reviewing"))
            {
                db.set_Word_status(String.valueOf(txtWord.getText()),"Mastered");
            }

            else;

                setPbar_Texts(db);
                setPbar_progress(db);
                hide_CardDetails();
                newWord(db);
            }
        });

        // kelime durumu güncelleniyor ve yeni kelimeye geçiliyor
        txtIdidintKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(String.valueOf(txtWordStatus.getText()).equals("Mastered"))
                {
                    db.set_Word_status(String.valueOf(txtWord.getText()),"Reviewing");
                }

                else if (String.valueOf(txtWordStatus.getText()).equals("Reviewing"))
                {
                    db.set_Word_status(String.valueOf(txtWord.getText()),"Learning");
                }
                else;

                setPbar_Texts(db);
                setPbar_progress(db);
                hide_CardDetails();
                newWord(db);
            }
        });


    }


}