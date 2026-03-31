package project.hands_2casestudy_2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import project.hands_2casestudy_2.databinding.RecipeItemBinding

class RecipeAdapter(
    private val recipes: List<Recipe>,
    private val onClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(val binding: RecipeItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.binding.recipeName.text = recipe.title
        
        val context = holder.binding.recipeImage.context
        val resourceId = context.resources.getIdentifier(recipe.imageUrl.lowercase(), "drawable", context.packageName)
        
        if (resourceId != 0) {
            Glide.with(context)
                .load(resourceId)
                .into(holder.binding.recipeImage)
        } else {
            Glide.with(context)
                .load(recipe.imageUrl)
                .into(holder.binding.recipeImage)
        }
        
        holder.itemView.setOnClickListener { onClick(recipe) }
    }

    override fun getItemCount() = recipes.size
}
