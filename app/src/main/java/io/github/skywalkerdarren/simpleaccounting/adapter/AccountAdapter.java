package io.github.skywalkerdarren.simpleaccounting.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.listener.OnItemDragListener;

import org.joda.time.DateTime;

import java.util.List;

import io.github.skywalkerdarren.simpleaccounting.R;
import io.github.skywalkerdarren.simpleaccounting.base.BaseDraggableDataBindingAdapter;
import io.github.skywalkerdarren.simpleaccounting.databinding.ItemAccountBinding;
import io.github.skywalkerdarren.simpleaccounting.model.AppRepository;
import io.github.skywalkerdarren.simpleaccounting.model.entity.Account;
import io.github.skywalkerdarren.simpleaccounting.util.AppExecutors;

/**
 * @author darren
 * @date 2018/3/24
 */

public class AccountAdapter extends BaseDraggableDataBindingAdapter<Account, ItemAccountBinding> {
    private final AppRepository mRepository;

    public AccountAdapter(Context context) {
        super(R.layout.item_account, null);
        mRepository = AppRepository.getInstance(new AppExecutors(), context);
        setOnItemDragListener(new OnItemDragListener() {

            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                float st = viewHolder.itemView.getElevation();
                itemRaiseAnimator(viewHolder.itemView, st, true);
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
                mRepository.changePosition(getItem(from), getItem(to));
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                float ed = viewHolder.itemView.getElevation();
                itemRaiseAnimator(viewHolder.itemView, ed, false);
            }
        });
    }

    private boolean checkCache(List<Account> accounts) {
        if (accounts == null) {
            return true;
        }
        if (mData.size() != accounts.size()) {
            mData.clear();
            mData.addAll(accounts);
            return true;
        }
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            Account cache = mData.get(i);
            if (!account.equals(cache)) {
                mData.clear();
                mData.addAll(accounts);
                return true;
            }
        }
        return false;
    }

    @Override
    public void setNewData(@Nullable List<Account> data) {
        if (checkCache(data)) {
            super.setNewData(data);
        }
    }

    private void itemRaiseAnimator(View view, final float start, boolean raise) {
        final float end = start * 2;
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "elevation",
                raise ? start : end, raise ? end : start);
        animator.setDuration(50);
        animator.start();
    }

    @Override
    protected void convert(ItemAccountBinding binding, Account item) {
        binding.setAccount(item);
        mRepository.getAccountStats(item.getUuid(), new DateTime(0), DateTime.now(), accountStats -> {
            item.setBalance(accountStats.getSum());
            binding.setAccount(item);
        });
    }
}
