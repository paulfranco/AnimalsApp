package co.paulfran.paulfranco.animalsapp.view


import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.palette.graphics.Palette

import co.paulfran.paulfranco.animalsapp.R
import co.paulfran.paulfranco.animalsapp.databinding.FragmentDetailBinding
import co.paulfran.paulfranco.animalsapp.model.Animal
import co.paulfran.paulfranco.animalsapp.model.AnimalPalette
import co.paulfran.paulfranco.animalsapp.util.getProgressDrawable
import co.paulfran.paulfranco.animalsapp.util.loadImage
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd



/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private lateinit var interstitialAd: InterstitialAd

    var animal: Animal? = null
    private lateinit var dataBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // data binding
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            animal = DetailFragmentArgs.fromBundle(it).animal
        }


        showInterstitialAd()

        populate()




    }

    fun populate() {

        dataBinding.animal = animal

        // Set up background color with Palette Library
        animal?.imageUrl?.let {
            setUpBackgroundColor(it)
        }
    }

    // Set up background color with Palette Library
    private fun setUpBackgroundColor(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object: CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate() {palette ->
                            val intColor = palette?.lightMutedSwatch?.rgb ?: 0
                            dataBinding.palette = AnimalPalette(intColor)
                        }
                }

            })
    }

    private fun showInterstitialAd() {
        interstitialAd = InterstitialAd(context)
        interstitialAd.adUnitId = getString(R.string.interstitial_ad_id)
        interstitialAd.loadAd(AdRequest.Builder().build())
        interstitialAd.adListener = object: AdListener() {
            override fun onAdLoaded() {
                interstitialAd.show()
            }
        }

    }

}
