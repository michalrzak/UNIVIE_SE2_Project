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
import java.util.Locale;
import org.w3c.dom.Text;

/**
 * The view holder to be used with {@link TransactionList}
 */
public class TransactionListViewHolder extends RecyclerView.ViewHolder {

  private final TextView nameView;
  private final TextView amountView;
  private final TextView signView;
  //private final TextView categoryView;
  private final View itemView;

  public TransactionListViewHolder(@NonNull View itemView) {
    super(itemView);
    nameView = itemView.findViewById(R.id.transaction_item_name);
    amountView = itemView.findViewById(R.id.transaction_item_amount);
    signView = itemView.findViewById(R.id.transaction_item_sign);
    //categoryView = itemView.findViewById(R.id.transaction_item_category);

    this.itemView = itemView;
  }

  static protected TransactionListViewHolder create(ViewGroup parent) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_listitem, parent, false);
    return new TransactionListViewHolder(view);
  }

  public void bind(@NonNull Transaction transaction,
      @NonNull Consumer<Transaction> onClickListener) {
    nameView.setText(transaction.getName());
    amountView.setText(String.format(Locale.GERMANY, "%d",transaction.getAmount()));
    signView.setText(transaction.getType().getSign() == -1 ? "-" : "+");
    /*if (!transaction.getCategory().isPresent()) {
      categoryView.setText(R.string.no_category);
    } else {
      categoryView.setText(transaction.getCategory().get().getName());
    }*/

    itemView.setOnClickListener(view -> onClickListener.accept(transaction));

  }
}
