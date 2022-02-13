package co.zw.amosesuwali.dogplayground.data

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.zw.amosesuwali.dogplayground.databinding.BasicBreedIconItemBinding
import co.zw.amosesuwali.dogplayground.models.BreedDetailModel

/**
* This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
* data, including computing diffs between lists.
*/

class FavBreedIconListAdapter: ListAdapter<BreedDetailModel, FavBreedIconListAdapter.FavBreedIconViewHolder>(DiffCallback) {

     private var selectedBreeds = MutableLiveData<MutableList<String>> ()

    class FavBreedIconViewHolder(
    private var binding: BasicBreedIconItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(breed: BreedDetailModel) {
            binding.breedImageURL = breed.breedImageURL
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()

        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<BreedDetailModel>() {
        override fun areItemsTheSame(oldItem: BreedDetailModel, newItem: BreedDetailModel): Boolean {
            return oldItem.breedName == newItem.breedName
        }

        override fun areContentsTheSame(oldItem: BreedDetailModel, newItem: BreedDetailModel): Boolean {
            return oldItem.breedName == newItem.breedName
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavBreedIconViewHolder {
        selectedBreeds.value= mutableListOf()
        val viewHolder= FavBreedIconViewHolder(
            BasicBreedIconItemBinding.inflate(LayoutInflater.from(parent.context))
        )

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition

        }

        return viewHolder
    }



    override fun onBindViewHolder(holder: FavBreedIconViewHolder, position: Int) {
        val breedItem = getItem(holder.adapterPosition)
        holder.itemView.setOnClickListener {

        }
        holder.bind(breedItem)

    }


    private var context: Context? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }
}
