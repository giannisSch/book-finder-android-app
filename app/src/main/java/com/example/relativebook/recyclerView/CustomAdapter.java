package com.example.relativebook.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.example.relativebook.Book;
import com.example.relativebook.R;

public class CustomAdapter extends ListAdapter<Object, AbstractViewHolder<Object>> {


    private ItemClickListener listener;
    public CustomAdapter(ItemClickListener listener) {
        super(new DiffItemCallbackClass<Object>());
        this.listener = listener;
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        if (viewType == R.layout.book_item_row_holder) {
            return new CustomViewHolder(view, listener);
        }else {
            return new EmptyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull AbstractViewHolder<Object> holder, int position) {
        holder.present(getItem(position));
    }

    @Override
    public int getItemViewType(int position) {
        Object item = getItem(position);
        if (item instanceof Book) {
            return R.layout.book_item_row_holder;
        }else {
            return R.layout.empty_item_row_holder;
        }
    }


}