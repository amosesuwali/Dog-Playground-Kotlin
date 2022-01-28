package co.zw.amosesuwali.dogplayground.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.zw.amosesuwali.dogplayground.databinding.BasicPictureItemBinding
import androidx.recyclerview.widget.StaggeredGridLayoutManager




/**
* This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
* data, including computing diffs between lists.
*/
class PhotoGridAdapter : ListAdapter<String, PhotoGridAdapter.DogCeoViewHolder>(DiffCallback) {

    /**
     * The DogCeoViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full photoURL information.
     */
    class DogCeoViewHolder(
        private var binding: BasicPictureItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photoURL: String) {
            binding.photo = photoURL
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()

        }
    }

//    recyclerView.setLayoutManager(new LinearLayoutManager(this){
//        @Override
//        public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
//            // force height of viewHolder here, this will override layout_height from xml
//            lp.height = getHeight() / 3;
//            return true;
//        }
//    });

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of
     * photoURL has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem:String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DogCeoViewHolder {
        return DogCeoViewHolder(
            BasicPictureItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: DogCeoViewHolder, position: Int) {
        val pictureItem = getItem(position)
        holder.bind(pictureItem)
    }



}
