package com.example.keepocket2.view.limit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keepocket2.R;
import com.example.keepocket2.data.Category;

import java.util.ArrayList;
import java.util.List;

public class LimitAdapter extends RecyclerView.Adapter<LimitAdapter.LimitViewHolder> {
    private final LimitAdapter.LimitAdapterEventListener eventListener;
    private List<Category> categoryList;

    public LimitAdapter(LimitAdapter.LimitAdapterEventListener eventListener){
        this.eventListener = eventListener;
        this.categoryList = new ArrayList<>();
    }

    @NonNull
    @Override
    public LimitAdapter.LimitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.limit_row,parent, false);
        return new LimitAdapter.LimitViewHolder(layout, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull LimitViewHolder holder, int position) {
        Category category =this.categoryList.get(position);

        holder.setLimitCategoryName(category.getCategoryName());
        holder.setLimitValue(category.getLimit());
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventListener.onLimitClicked(category.getIdCategory());
            }

        });
        holder.rootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                eventListener.onLimitLongClicked(category.getIdCategory());
                return true;
            }
        });
    }

    public int getItemCount(){
        return this.categoryList.size();
    }

    public void updateCategoryList(List<Category> allCategory){
        this.categoryList = allCategory;
        notifyDataSetChanged();
    }

    public static class LimitViewHolder extends RecyclerView.ViewHolder {
        private View rootView;
        private TextView limitValue;
        private TextView limitCategoryName;
        private Context context;

        public LimitViewHolder(@NonNull View rootView, Context context){
            super(rootView);
            this.context = context;
            this.rootView = rootView;
            this.limitCategoryName = itemView.findViewById(R.id.limitCategoryname);
            this.limitValue = itemView.findViewById(R.id.limitValue);

        }
        public void setLimitCategoryName(String limitCategoryName){
            this.limitCategoryName.setText(limitCategoryName);
        }
        public void setLimitValue(int limitValue){
            String stringValueExpenses = Integer.toString(limitValue);
            this.limitValue.setText(stringValueExpenses);
        }

    }
    public interface LimitAdapterEventListener{
        void onLimitClicked(long categoryId);
        void onLimitLongClicked(long chatsId);
    }
}

