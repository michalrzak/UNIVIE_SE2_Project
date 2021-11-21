package at.ac.univie.se2.ws21.team0404.app.ui.account.accountList;

import android.view.ViewGroup;

import androidx.core.util.Consumer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;

public class AccountListAdapter extends ListAdapter<AppAccount, AccountListViewHolder> {

    private Consumer<AppAccount> onClickListener;

    public AccountListAdapter(Consumer<AppAccount> onClickListener) {
        super(AppAccountDiffUtil);
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public AccountListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AccountListViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountListViewHolder holder, int position) {
        holder.bind(getItem(position), onClickListener);
    }

    public static final DiffUtil.ItemCallback<AppAccount> AppAccountDiffUtil = new DiffUtil.ItemCallback<AppAccount>() {
        @Override
        public boolean areItemsTheSame(@NonNull AppAccount oldItem, @NonNull AppAccount newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull AppAccount oldItem, @NonNull AppAccount newItem) {
            return oldItem.equals(newItem);
        }
    };


}
