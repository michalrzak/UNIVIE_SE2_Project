package at.ac.univie.se2.ws21.team0404.app.ui.accountList;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;

public class AccountListAdapter extends ListAdapter<AppAccount, AccountListViewHolder> {

    public AccountListAdapter(@NonNull DiffUtil.ItemCallback<AppAccount> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public AccountListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return AccountListViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountListViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static final class AppAccountDiff extends DiffUtil.ItemCallback<AppAccount> {

        @Override
        public boolean areItemsTheSame(@NonNull AppAccount oldItem, @NonNull AppAccount newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull AppAccount oldItem, @NonNull AppAccount newItem) {
            return oldItem.equals(newItem);
        }
    }


}
