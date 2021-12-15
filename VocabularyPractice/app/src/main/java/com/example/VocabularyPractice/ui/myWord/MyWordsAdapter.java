package com.example.VocabularyPractice.ui.myWord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.VocabularyPractice.R;

public class MyWordsAdapter extends RecyclerView.Adapter<MyWordsAdapter.MyViewHolder>{
    Context context;
    String[] eng_words,tr_words;


    // Kurucu fonksiyon
    public MyWordsAdapter(Context ct,String[] eng_words,String[] tr_words ){
        context = ct;
        this.tr_words=tr_words;
        this.eng_words=eng_words;
    }


    @NonNull
    @Override
    public MyWordsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_list_layout, parent, false);


        return new MyWordsAdapter.MyViewHolder(view);
    }


    // Layout üzerinde bulunacak araçlarla ilgili işlemlerin yapıldığı fonksiyon
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_eng.setText(eng_words[position]);
        holder.txt_tr.setText(tr_words[position]);
    }


    // kaç layout oluşturulacağı belirtiliyor.
    @Override
    public int getItemCount() {return tr_words.length;}


    // Görüntülenecek araçlar tanımlaıyor.
    public class MyViewHolder extends RecyclerView.ViewHolder {

      public   TextView txt_eng,txt_tr;

        public MyViewHolder(@NonNull View ItemView) {
            super(ItemView);
            txt_eng = ItemView.findViewById(R.id.txt_myW_eng);
            txt_tr = ItemView.findViewById(R.id.txt_myW_tr);
        }

    }


}
