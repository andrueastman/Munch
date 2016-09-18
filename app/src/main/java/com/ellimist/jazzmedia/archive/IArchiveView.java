package com.ellimist.jazzmedia.archive;

import com.ellimist.jazzmedia.models.FeedItem;

import java.util.List;

/**
 * Created by Kartik_ch on 12/9/2015.
 */
public interface IArchiveView {
    void onArchiveRetrieved(List<FeedItem> feedItems);

    void onArchiveRetrievalFailed(String message);
}
