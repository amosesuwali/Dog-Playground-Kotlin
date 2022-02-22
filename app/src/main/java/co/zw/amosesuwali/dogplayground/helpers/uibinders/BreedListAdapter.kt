package co.zw.amosesuwali.dogplayground.helpers.uibinders

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.zw.amosesuwali.dogplayground.R
import co.zw.amosesuwali.dogplayground.databinding.BasicBreedDetailItemBinding
import co.zw.amosesuwali.dogplayground.models.BreedDetailModel

/**
* This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
* data, including computing diffs between lists.
*/

class BreedListAdapter(): ListAdapter<BreedDetailModel, BreedListAdapter.BreedDetailViewHolder>(
    DiffCallback
) {

     var selectedBreeds = MutableLiveData<MutableList<BreedDetailModel>> ()
     var selectedBreedsCount = MutableLiveData<String> ("0")
    val isSelectedListNotEmpty= MutableLiveData(false)
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
    ): BreedDetailViewHolder {
        selectedBreeds.value= mutableListOf()
        var viewHolder= BreedDetailViewHolder(
            BasicBreedDetailItemBinding.inflate(LayoutInflater.from(parent.context))
        )

        
        viewHolder.itemView.setOnClickListener {
            val breedItem = getItem(viewHolder.adapterPosition)
            val selectedBreedDecoration = context!!.resources.getDrawable(R.drawable.breed_selected_item_border,context!!.theme)
            val unselectedBreedDecoration = context!!.resources.getDrawable(R.drawable.breed_item_border,context!!.theme)

            isSelectedListNotEmpty.value = selectedBreedsCount.value?.equals(0) ?: (false)

            if (selectedBreeds.value?.contains(breedItem) == false) {
                selectedBreeds.value?.add(breedItem)
                it.background=selectedBreedDecoration
                it.visibility = View.VISIBLE
            }else{
                selectedBreeds.value?.remove(breedItem)
                it.background=unselectedBreedDecoration
            }

            selectedBreedsCount.value =  selectedBreeds.value?.size.toString()
            Log.d("selectedBreedSize",selectedBreeds.value?.size.toString())
        }
        return viewHolder
    }



    override fun onBindViewHolder(holder: BreedDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
        isSelectedListNotEmpty.value = selectedBreedsCount.value?.equals(0) ?: (false)

    }


    private var context: Context? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }
}
