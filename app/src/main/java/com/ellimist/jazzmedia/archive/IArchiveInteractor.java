package com.ellimist.jazzmedia.archive;

import android.content.Context;

/**
 * Created by Kartik_ch on 12/9/2015.
 */
public interface IArchiveInteractor {
    void retrieveArchiveFromDb(OnArticleRetrievedListener onArticleRetrievedListener, Context context);
}
