package org.thoughtcrime.securesms.components.settings.app.notifications.profiles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import org.thoughtcrime.securesms.notifications.profiles.NotificationProfile

class NotificationProfilesViewModel(private val repository: NotificationProfilesRepository) : ViewModel() {

  fun getProfiles(): Flowable<List<NotificationProfile>> = repository.getProfiles()
    .observeOn(AndroidSchedulers.mainThread())

  class Factory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = modelClass.cast(NotificationProfilesViewModel(NotificationProfilesRepository()))!!
  }
}
