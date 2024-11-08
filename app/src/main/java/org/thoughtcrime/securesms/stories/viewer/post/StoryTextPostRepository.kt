package org.thoughtcrime.securesms.stories.viewer.post

import android.graphics.Typeface
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.signal.core.util.Base64
import org.thoughtcrime.securesms.database.SignalDatabase
import org.thoughtcrime.securesms.database.model.MmsMessageRecord
import org.thoughtcrime.securesms.database.model.databaseprotos.StoryTextPost
import org.thoughtcrime.securesms.dependencies.AppDependencies
import org.thoughtcrime.securesms.fonts.TextFont
import org.thoughtcrime.securesms.fonts.TextToScript
import org.thoughtcrime.securesms.fonts.TypefaceCache

class StoryTextPostRepository {
  fun getRecord(recordId: Long): Single<MmsMessageRecord> = Single.fromCallable {
    SignalDatabase.messages.getMessageRecord(recordId) as MmsMessageRecord
  }.subscribeOn(Schedulers.io())

  fun getTypeface(recordId: Long): Single<Typeface> = getRecord(recordId).flatMap {
    val model = StoryTextPost.ADAPTER.decode(Base64.decode(it.body))
    val textFont = TextFont.fromStyle(model.style)
    val script = TextToScript.guessScript(model.body)

    TypefaceCache.get(AppDependencies.application, textFont, script)
  }
}
