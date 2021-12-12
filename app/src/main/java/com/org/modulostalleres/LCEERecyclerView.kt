package com.org.modulostalleres

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.org.modulostalleres.databinding.LceeRecyclerLayoutBinding
import com.org.modulostalleres.databinding.RecyclerEmptyLayoutBinding
import com.org.modulostalleres.databinding.RecyclerErrorLayoutBinding


class LCEERecyclerView constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)


    private val binding: LceeRecyclerLayoutBinding =
        LceeRecyclerLayoutBinding.inflate(LayoutInflater.from(context), this)

    private val errorBinding: RecyclerErrorLayoutBinding
    private val emptyBinding: RecyclerEmptyLayoutBinding

    // expose the recycler view
    val recyclerView: RecyclerView
        get() = binding.customRecyclerView

    var errorText: String = ""
        set(value) {
            field = value
            errorBinding.errorMsgText.text = value
        }

    var emptyText: String = ""
        set(value) {
            field = value
            emptyBinding.emptyMessage.text = value
        }

    @DrawableRes
    var errorIcon = 0
        set(value) {
            field = value
            errorBinding.errorImage.setImageResource(value)
        }

    @DrawableRes
    var emptyIcon = 0
        set(value) {
            field = value
            emptyBinding.emptyImage.setImageResource(value)
        }

    init {

        // inflate the layout
        errorBinding = binding.customErrorView
        emptyBinding = binding.customEmptyView

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.LCEERecyclerView,
            0,
            0
        ).apply {
            try {
                errorText = getString(R.styleable.LCEERecyclerView_errorText) ?: "Algo ha ido mal!"
                emptyText = getString(R.styleable.LCEERecyclerView_emptyText) ?: "No hay nada que mostrar!"
                errorIcon = getResourceId(
                    R.styleable.LCEERecyclerView_errorIcon,
                    R.drawable.ic_error_loading
                )
                emptyIcon =
                    getResourceId(R.styleable.LCEERecyclerView_emptyIcon, R.drawable.ic_empty_image)
            } finally {
                recycle()
            }
        }
    }

    fun showEmptyView(msg: String? = null) {
        emptyText = msg ?: emptyText
        errorBinding.root.visibility = GONE

        emptyBinding.root.visibility = VISIBLE
    }

    fun showErrorView(msg: String? = null) {
        errorText = msg ?: errorText
        emptyBinding.root.visibility = GONE

        errorBinding.root.visibility = VISIBLE
    }

    fun hideAllViews() {
        errorBinding.root.visibility = GONE
        emptyBinding.root.visibility = GONE
    }

    fun setOnRetryClickListener(callback: () -> Unit) {
        errorBinding.retryButton.setOnClickListener {
            callback()
        }
    }
}