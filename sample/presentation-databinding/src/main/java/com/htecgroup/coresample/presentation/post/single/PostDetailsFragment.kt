/*
 * Copyright 2023 HTEC Group Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.htecgroup.coresample.presentation.post.single

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.htecgroup.androidcore.presentation.extension.observe
import com.htecgroup.coresample.domain.service.analytics.Analytics
import com.htecgroup.coresample.presentation.R
import com.htecgroup.coresample.presentation.base.BaseToolbarFragment
import com.htecgroup.coresample.presentation.databinding.FragmentPostDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostDetailsFragment :
    BaseToolbarFragment<FragmentPostDetailsBinding, PostViewModel, PostDetailsRoutes>() {

    @Inject
    lateinit var analytics: Analytics

    private var currentNativeAd: NativeAd? = null

    private val args: PostDetailsFragmentArgs by navArgs()

    override fun provideLayoutId(): Int = R.layout.fragment_post_details

    override fun provideViewModelClass(): Class<PostViewModel> = PostViewModel::class.java

    override fun provideToolbarTitleId(): Int = R.string.title_post_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initPost(args.postView.id)
        observeAction()
        initAdds()
    }

    private fun observeAction() {
        observe(viewModel.getAction()) {
            viewModel.postLive.value?.let { postView ->
                when (it.code) {
                    PostViewModel.PostActionCode.CLICK_EDIT_POST ->
                        navigation?.navigateToEditPost(postView)
                    PostViewModel.PostActionCode.CLICK_DELETE_POST -> navigation?.navigateBack()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentNativeAd?.destroy()
    }

    /**
     * AdMob example added just as a proof of concept, not implemented the right/clean way.
     */
    private fun initAdds() {
        logger.d("AdMob", "initAdds()")
        AdLoader.Builder(requireContext(), "ca-app-pub-3940256099942544/2247696110") // test ID
            .forNativeAd {
                logger.d("AdMob", "forUnifiedNativeAd")
                renderNativeAdd(it)
            }.withAdListener(object : AdListener() {
                override fun onAdImpression() {
                    logger.d("AdMob", "onAdImpression")
                }

                override fun onAdClicked() {
                    logger.d("AdMob", "onAdClicked")
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    logger.d("AdMob", "onAdFailedToLoad")
                }
            })
            .withNativeAdOptions(NativeAdOptions.Builder().build())
            .build()
            .loadAd(AdRequest.Builder().build())
    }

    /**
     * Available fields for native content ads:
     * headline (required), image, body, logo, call to action, advertiser.
     *
     * Ad attribution either badge or text.
     */
    private fun renderNativeAdd(nativeAd: NativeAd) {
        currentNativeAd?.destroy()
        currentNativeAd = nativeAd

        val unifiedNativeAdView = binding.adView.apply {
            headlineView = binding.txtAdHeadline
            bodyView = binding.txtAdBody
            advertiserView = binding.txtAdAdvertiser
            priceView = binding.txtAdPrice
            storeView = binding.txtAdPrice
            iconView = binding.imgAdIcon
            mediaView = binding.viewAdMedia
            starRatingView = binding.ratingAdStars
            callToActionView = binding.btnAdAction
        }
        binding.run {
            viewAdMedia.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            txtAdHeadline.text = nativeAd.headline
            txtAdBody.text = nativeAd.body ?: ""
            txtAdAdvertiser.text = nativeAd.advertiser ?: ""
            txtAdPrice.text = nativeAd.price ?: ""
            txtAdStore.text = nativeAd.store ?: ""
            // img_ad_icon.setImageDrawable(nativeAd.icon.drawable)
            viewAdMedia.mediaContent = nativeAd.mediaContent
            ratingAdStars.rating = nativeAd.starRating?.toFloat() ?: 0f
            btnAdAction.text = nativeAd.callToAction ?: ""
        }
        unifiedNativeAdView.setNativeAd(nativeAd)
    }
}
