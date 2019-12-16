package io.github.skywalkerdarren.simpleaccounting.view_model;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import org.joda.time.DateTime;

import io.github.skywalkerdarren.simpleaccounting.model.AccountLab;
import io.github.skywalkerdarren.simpleaccounting.model.StatsLab;
import io.github.skywalkerdarren.simpleaccounting.util.FormatUtil;

/**
 * 账户页vm
 *
 * @author darren
 * @date 2018/4/4
 */

public class AccountViewModel extends BaseObservable {
    private AccountLab mAccountLab;
    private StatsLab mStatsLab;
    private StatsLab.BillStats mStats;

    /**
     * 初始化lab 和列表stats
     *
     * @param context 上下文
     */
    public AccountViewModel(Context context) {
        mStatsLab = StatsLab.getInstance(context);
        mAccountLab = AccountLab.getInstance(context);
        mStats = mStatsLab.getStats(new DateTime(0), DateTime.now());
    }

    public void setStats() {
        mStats = mStatsLab.getStats(new DateTime(0), DateTime.now());
        notifyChange();
    }

    /**
     * @return 净资产
     */
    @Bindable
    public String getNav() {
        return FormatUtil.getNumeric(mStats.getSum());
    }

    /**
     * @return 负债
     */
    @Bindable
    public String getLiability() {
        return FormatUtil.getNumeric(mStats.getExpense());
    }

    /**
     * @return 总资产
     */
    @Bindable
    public String getTotalAssets() {
        return FormatUtil.getNumeric(mStats.getIncome());
    }

    /**
     * @return 账户数目
     */
    @Bindable
    public String getAccountSize() {
        return mAccountLab.getAccounts().size() + "";
    }

    public void changePosition(int oldPos, int newPos) {
        mAccountLab.changePosition(oldPos, newPos);
    }
}
