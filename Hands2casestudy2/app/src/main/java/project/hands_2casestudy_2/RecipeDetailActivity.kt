package project.hands_2casestudy_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import project.hands_2casestudy_2.databinding.ActivityDetailBinding

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipe = intent.getParcelableExtra<Recipe>("recipe")

        recipe?.let {
            binding.detailTitle.text = it.title
            
            val resourceId = resources.getIdentifier(it.imageUrl.lowercase(), "drawable", packageName)
            if (resourceId != 0) {
                Glide.with(this).load(resourceId).into(binding.detailImage)
            } else {
                Glide.with(this).load(it.imageUrl).into(binding.detailImage)
            }

            binding.ingredientsRecyclerView.layoutManager = LinearLayoutManager(this)
            binding.ingredientsRecyclerView.adapter = StringAdapter(it.ingredients)

            binding.instructionsRecyclerView.layoutManager = LinearLayoutManager(this)
            binding.instructionsRecyclerView.adapter = StringAdapter(
                it.instructions.mapIndexed { index, s -> "${index + 1}. $s" }
            )
        }
    }
}
