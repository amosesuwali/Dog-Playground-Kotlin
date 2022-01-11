package co.zw.amosesuwali.dogplayground.data

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.zw.amosesuwali.dogplayground.databinding.BasicBreedDetailItemBinding
import co.zw.amosesuwali.dogplayground.models.BreedDetailModel

/**
* This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
* data, including computing diffs between lists.
*/
class BreedListAdapter : ListAdapter<BreedDetailModel, BreedListAdapter.BreedDetailViewHolder>(DiffCallback) {

    class BreedDetailViewHolder(
        private var binding: BasicBreedDetailItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(breed: BreedDetailModel) {
            binding.breed = breed
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()

        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<BreedDetailModel>() {
        override fun areItemsTheSame(oldItem:BreedDetailModel, newItem: BreedDetailModel): Boolean {
            return oldItem.breedName == newItem.breedName
        }

        override fun areContentsTheSame(oldItem: BreedDetailModel, newItem: BreedDetailModel): Boolean {
            return oldItem.breedName == newItem.breedName
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BreedDetailViewHolder {
        return BreedDetailViewHolder(
            BasicBreedDetailItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }
     var selectedBreeds : MutableList<String> = mutableListOf()
    override fun onBindViewHolder(holder: BreedDetailViewHolder, position: Int) {
        val breedItem = getItem(position)
        holder.itemView.setOnClickListener {

            if (!selectedBreeds.contains(breedItem.breedName)) {
                selectedBreeds.add(breedItem.breedName)
                holder.itemView.setBackgroundColor(Color.GREEN)
            }else{
                selectedBreeds.remove(breedItem.breedName)
                holder.itemView.setBackgroundColor(Color.WHITE)
            }
            Log.d("selectedBreedSize",selectedBreeds.size.toString())
        }
        holder.bind(breedItem)

    }



}
