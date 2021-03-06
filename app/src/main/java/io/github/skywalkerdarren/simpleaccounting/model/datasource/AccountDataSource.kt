package io.github.skywalkerdarren.simpleaccounting.model.datasource

import io.github.skywalkerdarren.simpleaccounting.model.entity.Account
import java.math.BigDecimal
import java.util.*

interface AccountDataSource {
    fun getAccount(uuid: UUID, callBack: (Account) -> Unit)
    fun getAccounts(callBack: (List<Account>?) -> Unit)
    fun delAccount(uuid: UUID)
    fun changePosition(a: Account, b: Account)
    fun updateAccountBalance(uuid: UUID, balance: BigDecimal)
}