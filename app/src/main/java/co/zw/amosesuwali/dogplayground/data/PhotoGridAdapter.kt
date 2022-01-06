package co.zw.amosesuwali.dogplayground.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.zw.amosesuwali.dogplayground.databinding.BasicPictureItemBinding
import co.zw.amosesuwali.dogplayground.models.Pictures

/**
* This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
* data, including computing diffs between lists.
*/
class PhotoGridAdapter :
    ListAdapter<Pictures, PhotoGridAdapter.MarsPhotosViewHolder>(DiffCallback) {

    /**
     * The MarsPhotosViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [MarsPhoto] information.
     */
    class MarsPhotosViewHolder(
        private var binding: BasicPictureItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(marsPhoto: Pictures) {
            binding.photo = marsPhoto
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of
     * [MarsPhoto] has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Pictures>() {
        override fun areItemsTheSame(oldItem: Pictures, newItem: Pictures): Boolean {
            return oldItem.imageList == newItem.imageList
        }

        override fun areContentsTheSame(oldItem: Pictures, newItem: Pictures): Boolean {
            return oldItem.imageList == newItem.imageList
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarsPhotosViewHolder {
        return MarsPhotosViewHolder(
            BasicPictureItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: MarsPhotosViewHolder, position: Int) {
        val marsPhoto = getItem(position)
        holder.bind(marsPhoto)
    }
}
