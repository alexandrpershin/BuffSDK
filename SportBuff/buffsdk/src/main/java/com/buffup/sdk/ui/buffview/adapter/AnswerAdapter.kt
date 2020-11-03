package com.buffup.sdk.ui.buffview.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.buffup.sdk.R
import com.buffup.sdk.databinding.BuffAnswerBinding
import com.buffup.sdk.model.Answer

/**
 * RecyclerView adapter to display possible answers of the question
 *
 * */

 class AnswerAdapter(
    private val context: Context,
    private val answers: ArrayList<Answer> = arrayListOf()
) : RecyclerView.Adapter<AnswerAdapter.ViewHolder>() {

    var onClickListener: ((Answer) -> Unit)? = null

    var selectedItem: Answer? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = BuffAnswerBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = answers.size

    private fun resetSelectedItem() {
        selectedItem = null
    }

    /**
     * Once app received new data, remove old date, update with new data and reset selected answer
     * */

    fun updateData(answers: List<Answer>) {
        resetSelectedItem()
        this.answers.clear()
        this.answers.addAll(answers)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: BuffAnswerBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(position: Int) {
            val answer = answers[position]

            val backgroundResource =
                if (answer == selectedItem) R.drawable.light_bg_highlighted else R.drawable.light_bg

            binding.root.setBackgroundResource(backgroundResource)
            binding.answerText.text = answer.title

            binding.root.setOnClickListener(this)
        }


        /**
         * Check if answer already have been selected then ignore click
         * */

        override fun onClick(view: View?) {
            val answer = answers[adapterPosition]
            val isAlreadySelected = selectedItem != null

            if (!isAlreadySelected) {
                selectedItem = answer
                onClickListener?.invoke(answer)
                notifyDataSetChanged()
            }
        }

    }
}
