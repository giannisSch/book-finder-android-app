package com.example.relativebook.recyclerView;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.relativebook.Book;
import com.example.relativebook.R;


public class CustomViewHolder extends AbstractViewHolder<Object> {
    private TextView title;



    public CustomViewHolder(@NonNull View itemView, ItemClickListener listener) {
        super(itemView);
        setListener(listener);
        title = itemView.findViewById(R.id.title);
    }


    @Override
    public void present(Object data) {
        setData(data);
        if (data instanceof Book) {
            title.setText(((Book) data).getTitle());
        }else{
            //Do something for better User Experience
        }

    }
}
