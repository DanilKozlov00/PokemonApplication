package com.example.myapplication.fragment

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDayPokemonBinding
import com.example.myapplication.paging.model.PokemonFavoriteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class DayPokemonFragment : DialogFragment(R.layout.fragment_day_pokemon) {

    companion object {
        const val TAG = "PokemonDay"
        var isShow: Boolean = false
    }

    private var viewBinding: FragmentDayPokemonBinding? = null
    private lateinit var viewModel: PokemonFavoriteViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PokemonFavoriteViewModel::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            viewBinding!!.pokemon = viewModel.getPokemon()
            viewBinding!!.viewModel = viewModel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val myView = super.onCreateView(inflater, container, savedInstanceState)!!
        viewBinding = FragmentDayPokemonBinding.bind(myView)
        return myView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setWidthPercent(85)
    }

    private fun DialogFragment.setWidthPercent(percentage: Int) {
        val percent = percentage.toFloat() / 100
        val dm = Resources.getSystem().displayMetrics
        val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
        val percentWidth = rect.width() * percent
        dialog?.window?.setLayout(percentWidth.toInt(), (percentWidth.toInt() * 1.5).toInt())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

}