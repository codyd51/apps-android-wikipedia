package org.wikipedia.feed.news

import android.net.Uri
import kotlinx.serialization.Serializable
import org.wikipedia.Constants
import org.wikipedia.dataclient.WikiSite
import org.wikipedia.dataclient.page.PageSummary
import org.wikipedia.util.ImageUrlUtil

@Serializable
class NewsItem {

    val story: String = ""
    val links: List<PageSummary?> = emptyList()

    fun linkCards(wiki: WikiSite): List<NewsLinkCard> {
        return links.filterNotNull().map { NewsLinkCard(it, wiki) }
    }

    fun thumb(): Uri? {
        return getFirstImageUri(links)?.let {
            Uri.parse(ImageUrlUtil.getUrlForPreferredSize(
                    it.toString(), Constants.PREFERRED_CARD_THUMBNAIL_SIZE))
        }
    }

    private fun getFirstImageUri(links: List<PageSummary?>): Uri? {
        return links.firstOrNull { !it?.thumbnailUrl.isNullOrEmpty() }?.run { Uri.parse(thumbnailUrl) }
    }
}
