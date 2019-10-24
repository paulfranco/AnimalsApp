package co.paulfran.paulfranco.animalsapp.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager

import co.paulfran.paulfranco.animalsapp.R
import co.paulfran.paulfranco.animalsapp.model.Animal
import co.paulfran.paulfranco.animalsapp.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize


/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {

    private lateinit var adView: AdView

    private lateinit var viewModel: ListViewModel
    private val listAdapter = AnimalListAdapter(arrayListOf())

    private val animalListDataObserver = Observer<List<Animal>> { list->
        list?.let {
            animalList.visibility = View.VISIBLE
            listAdapter.updateAnimalList(it)
        }

    }

    private val loadingLiveDataObserver = Observer<Boolean> { isLoading ->
        loadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
        if(isLoading) {
            listError.visibility = View.GONE
            animalList.visibility = View.GONE
        }
    }

    private val errorLiveDataObserver = Observer<Boolean> { isError ->
        listError.visibility = if (isError) View.VISIBLE else View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(co.paulfran.paulfranco.animalsapp.R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adView =  view.findViewById<View>(co.paulfran.paulfranco.animalsapp.R.id.adView) as AdView
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)


        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)

        viewModel.animals.observe(this, animalListDataObserver)
        viewModel.loading.observe(this, loadingLiveDataObserver)
        viewModel.loadError.observe(this, errorLiveDataObserver)

        viewModel.refresh()

        animalList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter
        }

        refreshLayout.setOnRefreshListener {
            animalList.visibility = View.GONE
            listError.visibility - View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.hardRefresh()
            refreshLayout.isRefreshing = false
        }


    }


}
