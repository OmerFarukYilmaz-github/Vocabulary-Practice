package com.example.VocabularyPractice.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.VocabularyPractice.R;
import com.example.VocabularyPractice.database.DatabaseManager;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ProgressBar progressBar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        progressBar = view.findViewById(R.id.progressBar_onCardMenu);
        recyclerView = view.findViewById(R.id.recycler_view);

        //Database oluşturuluyor/ bağlantısı kuruluyor.
        DatabaseManager db= DatabaseManager.getInstance(getContext());

        // RecyclerView içindeki kartların textlerinin ayarlanabilmesi için textlerin tutulduğu dizi
        String[] progress_Texts = {
                "You have mastered "+db.get_status_count(1,3)+" of 30 words",
                "You have mastered "+db.get_status_count(2,3)+" of 30 words",
                "You have mastered "+db.get_status_count(3,3)+" of 30 words",
                "You have mastered "+db.get_status_count(4,3)+" of 30 words"};

        // RecyclerView içindeki progressların ayarlanabilmesi için progressların tutulduğu dizi
        int[] progress={
                Integer.parseInt(db.get_status_count(1,3)),
                Integer.parseInt(db.get_status_count(2,3)),
                Integer.parseInt(db.get_status_count(3,3)),
                Integer.parseInt(db.get_status_count(4,3))};


        myAdapter = new MyAdapter(getContext(),progress_Texts,progress,db.getAll_LevelCount());            //RecyclerView için adapter sınıf oluşturuluyor
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));  //RecyclerView kuruluyor,oluşturuluyor..
        recyclerView.setAdapter(myAdapter);
        return view;
    }



}