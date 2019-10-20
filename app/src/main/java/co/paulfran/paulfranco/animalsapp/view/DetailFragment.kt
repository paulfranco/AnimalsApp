package co.paulfran.paulfranco.animalsapp.view


import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.palette.graphics.Palette

import co.paulfran.paulfranco.animalsapp.R
import co.paulfran.paulfranco.animalsapp.model.Animal
import co.paulfran.paulfranco.animalsapp.util.getProgressDrawable
import co.paulfran.paulfranco.animalsapp.util.loadImage
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.item_animal.*
import kotlinx.android.synthetic.main.item_animal.animalImage
import kotlinx.android.synthetic.main.item_animal.animalName

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private lateinit var interstitialAd: InterstitialAd

    var animal: Animal? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            animal = DetailFragmentArgs.fromBundle(it).animal
        }

        context?.let {
            animalImage.loadImage(animal?.imageUrl, getProgressDrawable(it))
        }

        showInterstitialAd()

        populate()




    }

    fun populate() {
        animalName.text = animal?.name
        animalLocation.text = animal?.location
        animalLifespan.text = animal?.lifeSpan
        animalDiet.text = animal?.diet

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
                            animalDetailLayout.setBackgroundColor(intColor)
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
