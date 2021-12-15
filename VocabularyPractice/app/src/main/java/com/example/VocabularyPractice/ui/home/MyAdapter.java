package com.example.VocabularyPractice.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.VocabularyPractice.PracticeActivity;
import com.example.VocabularyPractice.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    Context context;
    String[] levelTitles,progress_Texts;
    int[] progress;
    String cardCount;


    // Kurucu fonksiyon
    public MyAdapter(Context ct, String[] progress_Texts, int[] progress, String cardCount){
        context = ct;
        this.progress_Texts = progress_Texts;
        this.progress = progress;
        this.cardCount= cardCount;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_view, parent, false);
        return new MyViewHolder(view);
    }


    // Kartların üzerinde bulunacak araçlarla ilgili işlemlerin yapıldığı fonksiyon
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(levelTitles[position]);
        holder.prBar_text.setText(progress_Texts[position]);
        holder.pBar.setProgress(progress[position]);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PracticeActivity.class);
                intent.putExtra("level_title", levelTitles[position]);
                context.startActivity(intent);
            }
        });



    }


    // kaç kart oluşturulacağı belirtiliyor.
    @Override
    public int getItemCount() {return Integer.parseInt(cardCount);}



    // Görüntülenecek araçlar tanımlaıyor.
    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textView,prBar_text;
        ProgressBar pBar;
        public MyViewHolder(@NonNull View ItemView) {
            super(ItemView);
            cardView = itemView.findViewById(R.id.card_view);
            textView = itemView.findViewById(R.id.text_holder);
            levelTitles = ItemView.getResources().getStringArray(R.array.Level_title);
            prBar_text = ItemView.findViewById(R.id.txtV_Progress_onCard);
            pBar =ItemView.findViewById(R.id.progressBar_onCardMenu);
        }

    }
}