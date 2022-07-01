package cn.com.zt

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.com.zt.databinding.ItemBinding
import com.bumptech.glide.Glide

class ImageAdapter(cxt: Context, list: List<String>) : RecyclerView.Adapter<ImageHolder>() {
    private val imageList: List<String> = list;
    private val context = cxt;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val itemBinding = ItemBinding.inflate(LayoutInflater.from(context))

        return ImageHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        Glide.with(context).
        load(imageList[position])
            .centerCrop()
            .into(holder.bind.image)
        holder.bind.text.text = "text $position"
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}


class ImageHolder(binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}