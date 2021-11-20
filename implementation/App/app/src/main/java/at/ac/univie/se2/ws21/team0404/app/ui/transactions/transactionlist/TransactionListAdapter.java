package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactionlist;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;

public class TransactionListAdapter extends ListAdapter<Transaction, TransactionListViewHolder> {

    private Consumer<Transaction> onClickListener;

    public static final DiffUtil.ItemCallback<Transaction> appAccountDiffUtil = new DiffUtil.ItemCallback<Transaction>() {
        @Override
        public boolean areItemsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
            return oldItem.equals(newItem);
        }
    };

    public TransactionListAdapter(@NonNull Consumer<Transaction> onClickListener) {
        super(appAccountDiffUtil);
        this.onClickListener = onClickListener;
    }


    @NonNull
    @Override
    public TransactionListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TransactionListViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionListViewHolder holder, int position) {
        holder.bind(getItem(position), onClickListener);
    }
}
