package com.example.VocabularyPractice.ui.myWord;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.VocabularyPractice.R;
import com.example.VocabularyPractice.TranslateActivity;
import com.example.VocabularyPractice.database.DatabaseManager;

public class MyWordFragment extends Fragment {
    MyWordsAdapter myAdapter;
    RecyclerView recyclerView;
    Button btnGo,btnRefresh;
    DatabaseManager db;

    private void create_rycView(){

        myAdapter = new MyWordsAdapter(getContext(),db.getAll_EngWords(),db.getAll_TrWords());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(myAdapter);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_myword, container, false);

        // Database bağlantısı
        db= DatabaseManager.getInstance(getContext());

        // araç tanımlamaları
        btnGo = view.findViewById(R.id.btn_goTranslator);
        btnRefresh =view.findViewById(R.id.btnRefresh);
        recyclerView = view.findViewById(R.id.rycList);

        // Recyclerview oluşturma ve kurma
        create_rycView();


        // Kelime listesini güncelleme
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                create_rycView();

            }
        });


        // Translator activitye gitme
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), TranslateActivity.class);
                getContext().startActivity(intent);
            }
        });


        return view;
    }





}

