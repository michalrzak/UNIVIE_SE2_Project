package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactionlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;
import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;

public class TransactionListViewHolder extends RecyclerView.ViewHolder {

  private final TextView nameView;
  private final View itemView;

  public TransactionListViewHolder(@NonNull View itemView) {
    super(itemView);
    nameView = itemView.findViewById(R.id.list_item_title);
    this.itemView = itemView;
  }

  static protected TransactionListViewHolder create(ViewGroup parent) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent, false);
    return new TransactionListViewHolder(view);
  }

  public void bind(@NonNull Transaction transaction,
      @NonNull Consumer<Transaction> onClickListener) {
    nameView.setText(transaction.getName());
    itemView.setOnClickListener(view -> onClickListener.accept(transaction));

  }
}
