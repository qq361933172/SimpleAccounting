package io.github.skywalkerdarren.simpleaccounting.adapter

import android.animation.Animator
import android.view.animation.AccelerateDecelerateInterpolator
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import io.github.skywalkerdarren.simpleaccounting.R
import io.github.skywalkerdarren.simpleaccounting.base.BaseDataBindingAdapter
import io.github.skywalkerdarren.simpleaccounting.databinding.ItemClassifyBinding
import io.github.skywalkerdarren.simpleaccounting.model.entity.TypeAndStats
import java.math.BigDecimal

/**
 * 分类页面的统计数据适配器
 *
 * @author darren
 * @date 2018/3/25
 */
class ClassifyAdapter : BaseDataBindingAdapter<TypeAndStats, ItemClassifyBinding>(R.layout.item_classify, null) {
    private var mSum = BigDecimal.ZERO
    fun setNewList(data: List<TypeAndStats>?) {
        setDiffNewData(data?.toMutableList())
        mSum = BigDecimal.ZERO
        data ?: return
        mSum = try {
            data.map { it.typeStats.balance }
                    .reduce { acc, bigDecimal -> acc + bigDecimal }
        } catch (e: Exception) {
            BigDecimal.ZERO
        }
    }

    override fun startAnim(anim: Animator, index: Int) {
        anim.duration = 300
        anim.interpolator = AccelerateDecelerateInterpolator()
        anim.start()
    }

    override fun convert(holder: BaseDataBindingHolder<ItemClassifyBinding>, item: TypeAndStats) {
        val binding = holder.dataBinding
        binding?.stats = item.typeStats
        binding?.sum = mSum
        binding?.type = item.type
    }

}