package com.olayg.onlykats.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.olayg.onlykats.R
import com.olayg.onlykats.databinding.FragmentDetailBinding
import com.olayg.onlykats.model.Breed
import com.olayg.onlykats.viewmodel.KatViewModel

class DetailsFragment : Fragment(R.layout.fragment_detail ) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDetailBinding.inflate(layoutInflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val navi = navArgs<DetailsFragmentArgs>()

        val img = navi.value.parcel?.image?.url
        val openSite = Intent(android.content.Intent.ACTION_VIEW)
        val name = navi.value.parcel?.name
        val description = navi.value.parcel?.description
        val origin = navi.value.parcel?.origin
        val vetStreet = navi.value.parcel?.vetStreetUrl
        val vcaHospital = navi.value.parcel?.vcaHospitalsUrl
        val wiki = navi.value.parcel?.wikipediaUrl
        val adapt = navi.value.parcel?.adaptability
        val affection = navi.value.parcel?.affectionLevel
        val child = navi.value.parcel?.childFriendly
        val dog = navi.value.parcel?.dogFriendly
        val energy = navi.value.parcel?.energyLevel
        val experience = navi.value.parcel?.experimental
        val rare = navi.value.parcel?.rare
        val social = navi.value.parcel?.socialNeeds
        val stranger = navi.value.parcel?.strangerFriendly
        val groom = navi.value.parcel?.grooming
        val hair = navi.value.parcel?.hairless
        val intel = navi.value.parcel?.intelligence
        val shed = navi.value.parcel?.sheddingLevel



        binding.tvName.text = name
        binding.tvDescription.text = description
        binding.tvOrigin.text = origin

        val pumg = Uri.parse(img)

        DownloadImageFromInternet(binding.sivImage).execute(img)

        if (img != null) binding.sivImage.setImageURI(pumg)
        if (stranger != null) binding.ratingBarOne.rating = stranger.toFloat()
        if (social != null) binding.ratingBarTwo.rating = social.toFloat()
        if (rare != null) binding.ratingBarThree.rating = rare.toFloat()
        if (experience != null) binding.ratingBarFour.rating = experience.toFloat()
        if (energy != null) binding.ratingBarFive.rating = energy.toFloat()
        if (dog != null) binding.ratingBarSix.rating = dog.toFloat()
        if (child != null) binding.ratingBarSeven.rating = child.toFloat()
        if (affection != null) binding.ratingBarEight.rating = affection.toFloat()
        if (adapt != null) binding.ratingBarNine.rating = adapt.toFloat()
        if (groom != null) binding.ratingBarTen.rating = groom.toFloat()
        if (hair != null) binding.ratingBarEleven.rating = hair.toFloat()
        if (intel != null) binding.ratingBarTweleve.rating = intel.toFloat()
        if (shed != null) binding.ratingBarThirteen.rating = shed.toFloat()
        binding.wikiUrl.text = wiki
        binding.wikiUrl.setOnClickListener {
            openSite.data = Uri.parse(wiki)
            startActivity(openSite)
        }

        binding.vcaUrl.text = vcaHospital
        binding.vcaUrl.setOnClickListener {
            openSite.data = Uri.parse(vcaHospital)
            startActivity(openSite)
        }

        binding.streetUrl.text = vetStreet
        binding.streetUrl.setOnClickListener {
            openSite.data = Uri.parse(vetStreet)
            startActivity(openSite)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("StaticFieldLeak")
    @Suppress("DEPRECATION")
    private inner class DownloadImageFromInternet(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        init {
            Toast.makeText(context, "Please wait, it may take a few minute...",     Toast.LENGTH_SHORT).show()
        }
        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageURL = urls[0]
            var imagery: Bitmap? = null
            try {
                val img = java.net.URL(imageURL).openStream()
                imagery = BitmapFactory.decodeStream(img)
            }
            catch (e: Exception) {
                Log.e("Error Message", e.message.toString())
                e.printStackTrace()
            }
            return imagery
        }
        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }
    }
}
