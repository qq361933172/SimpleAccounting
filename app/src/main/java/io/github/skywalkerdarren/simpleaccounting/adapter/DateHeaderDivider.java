package io.github.skywalkerdarren.simpleaccounting.adapter;

import org.joda.time.DateTime;

import java.math.BigDecimal;

import io.github.skywalkerdarren.simpleaccounting.util.FormatUtil;

/**
 * 账单分隔符
 *
 * @author darren
 * @date 2018/2/16
 */

class DateHeaderDivider implements HeaderDivider {
    private DateTime mDateTime;
    private int y;
    private int m;
    private int d;
    private BigDecimal mIncome;
    private BigDecimal mExpense;

    DateHeaderDivider(DateTime dateTime, BigDecimal income, BigDecimal expense) {
        y = dateTime.getYear();
        m = dateTime.getMonthOfYear();
        d = dateTime.getDayOfMonth();
        mDateTime = new DateTime(y, m, d, 0, 0);
        mIncome = income;
        mExpense = expense;
    }

    /**
     * 获取分割线日期
     *
     * @return 日期
     */
    DateTime getDate() {
        return mDateTime;
    }

    /**
     * @return 获取账单支出名称
     */
    @Override
    public String getExpense() {
        return FormatUtil.getNumberic(mExpense);
    }

    /**
     * @return 获取账单收入名称
     */
    @Override
    public String getIncome() {
        return FormatUtil.getNumberic(mIncome);
    }
}
