package be.tftic.web2023.demo09_fragment.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import be.tftic.web2023.demo09_fragment.R
import be.tftic.web2023.demo09_fragment.databinding.FragmentMovieListBinding
import be.tftic.web2023.demo09_fragment.models.Movie
import be.tftic.web2023.demo09_fragment.services.MovieService


/**
 * A simple [Fragment] subclass.
 * Use the [MovieListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieListFragment private constructor(): Fragment(),
    AdapterView.OnItemClickListener {

    companion object {
        @JvmStatic
        fun newInstance() : MovieListFragment {
            return MovieListFragment()
        }
    }

    // region Listener
    fun interface OnMovieSelectListener {
        fun onMovieSelected(movieId : Long)
    }

    private var onMovieSelectListener : OnMovieSelectListener? = null

    fun setOnMovieSelectListener(listener: OnMovieSelectListener) {
        onMovieSelectListener = listener
    }
    // endregion

    private lateinit var binding : FragmentMovieListBinding
    private lateinit var movieService: MovieService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieService = MovieService()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieListBinding.inflate(inflater, container, false)

        // Populer la list
        val movies = movieService.getAll()

        val adapter = ArrayAdapter<Movie>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            movies
        )
        binding.lvFragMovieList.adapter = adapter
        binding.lvFragMovieList.setOnItemClickListener(this)

        // Return View
        return binding.root
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if(parent == null) {
            return
        }

        val movie = parent.adapter.getItem(position) as Movie
        onMovieSelectListener?.onMovieSelected(movie.Id)
    }

}