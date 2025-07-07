package com.mindorks.framework.mvvm.ui.feed

import com.mindorks.framework.mvvm.data.repository.DataRepository
import com.mindorks.framework.mvvm.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    dataRepository: DataRepository
) : BaseViewModel(dataRepository)