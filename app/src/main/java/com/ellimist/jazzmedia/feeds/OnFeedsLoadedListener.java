package com.ellimist.jazzmedia.feeds;

import com.ellimist.jazzmedia.models.FeedItem;

import java.util.List;

/**
 * Created by Kartik_ch on 11/5/2015.
 */
public interface OnFeedsLoadedListener {
    void onSuccess(List<FeedItem> feedItems, boolean loadedNewFeeds);

    void onFailure(String message);
}
