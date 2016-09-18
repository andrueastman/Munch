package com.ellimist.jazzmedia.curatedfeeds;

import com.ellimist.jazzmedia.models.SourceItem;

import java.util.List;

/**
 * Created by Kartik_ch on 1/3/2016.
 */
public interface ICuratedFeedsView {
    void onFeedsLoaded(List<SourceItem> sourceItems);

    void onFeedsLoadingFailure(String message);
}
