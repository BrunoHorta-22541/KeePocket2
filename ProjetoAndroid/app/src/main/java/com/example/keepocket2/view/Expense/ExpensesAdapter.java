package com.example.keepocket2.view.Expense;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keepocket2.R;
import com.example.keepocket2.data.Category;
import com.example.keepocket2.data.Movement;
import com.example.keepocket2.data.localDatabase.Database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ExpensesViewHolder> {
    private final ExpensesAdapter.ExpensesAdapterEventListener eventListener;
    private List<Movement> movementList;

    public ExpensesAdapter(ExpensesAdapter.ExpensesAdapterEventListener eventListener){
        this.eventListener = eventListener;
        this.movementList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ExpensesAdapter.ExpensesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.expenses_row, parent, false);
        return new ExpensesAdapter.ExpensesViewHolder(layout, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ExpensesAdapter.ExpensesViewHolder holder, int position) {
        Movement movements =this.movementList.get(position);

        holder.setDescription(movements.getDescription());
        holder.setMovementsDate(movements.getMovementsDate());
        holder.setMovementsvalue(Integer.parseInt(movements.getValue()));
        holder.setMovementsCategoryId(Integer.parseInt(movements.getIdCategory()));

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventListener.onExpenseClicked(movements.getIdMovement());
            }

        });
        holder.rootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                eventListener.onExpenseLongClicked(movements.getIdMovement());
                return true;
            }
        });
    }

    public int getItemCount(){
        return this.movementList.size();
    }
    public void updateExpenseList(List<Movement> allMovements){
        this.movementList = allMovements;
        notifyDataSetChanged();
    }

    public static class ExpensesViewHolder extends RecyclerView.ViewHolder{
        private View rootView;
        private TextView description;
        private TextView categoryName;
        private TextView movementsDate;
        private TextView movementsvalue;
        private Context context;
        private static SimpleDateFormat dates= new SimpleDateFormat("dd/MM/yyyy");
        public ExpensesViewHolder(@NonNull View rootView, Context context){
            super(rootView);
            this.context = context;
            this.rootView = rootView;
            this.description = itemView.findViewById(R.id.expensesDescription);
            this.movementsDate = itemView.findViewById(R.id.dateExpenses);
            this.movementsvalue = itemView.findViewById(R.id.expensesPrice);
            this.categoryName = itemView.findViewById(R.id.categoryExpenses);

        }
        public void setDescription(String description){
            this.description.setText(description);
        }
        public void setMovementsDate(String movementsDate) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            String formattedDate = dateFormat.format(date);
            this.movementsDate.setText(formattedDate);

        }
        public void setMovementsvalue(int movementsvalue){
            String stringValueExpenses = Integer.toString(movementsvalue);
            this.movementsvalue.setText(stringValueExpenses+"€");
        }

        public void setMovementsCategoryId(long categoryId){
            Category category = Database.getInstance(context).getcategoryDAO().getById(categoryId);
            this.categoryName.setText(category.getCategoryName());
        }
    }


    public interface ExpensesAdapterEventListener{
        void onExpenseClicked(long movementsId);
        void onExpenseLongClicked(long movementsId);
    }
}
