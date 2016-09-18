package com.ellimist.jazzmedia.archive;

import com.ellimist.jazzmedia.models.FeedItem;

import java.util.List;

/**
 * Created by Kartik_ch on 12/9/2015.
 */
public interface OnArticleRetrievedListener {
    void onSuccess(List<FeedItem> feedItems);

    void onFailure(String message);
}
