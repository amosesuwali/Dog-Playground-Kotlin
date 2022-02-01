package co.zw.amosesuwali.dogplayground.data

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
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

     var selectedBreeds = MutableLiveData<MutableList<String>> ()
     var selectedBreedsCount = MutableLiveData<String> ("0")
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
        selectedBreeds.value= mutableListOf()
        return BreedDetailViewHolder(
            BasicBreedDetailItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: BreedDetailViewHolder, position: Int) {
        val breedItem = getItem(holder.adapterPosition)
        Log.d("holder",holder.toString())
        holder.itemView.setOnClickListener {

            if (selectedBreeds.value?.contains(breedItem.breedName) == false) {
                selectedBreeds.value?.add(breedItem.breedName)
                holder.itemView.setBackgroundColor(Color.GREEN)
                holder.itemView.visibility = View.VISIBLE
            }else{
                selectedBreeds.value?.remove(breedItem.breedName)
                holder.itemView.setBackgroundColor(Color.WHITE)
            }

            selectedBreedsCount.value =  selectedBreeds.value?.size.toString()
            Log.d("selectedBreedSize",selectedBreeds.value?.size.toString())
        }
        holder.bind(breedItem)

    }



}
