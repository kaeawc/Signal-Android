package org.thoughtcrime.securesms.attachments

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize
import org.signal.core.util.DatabaseId

@Parcelize
data class AttachmentId(
  @JsonProperty("rowId")
  @JvmField
  val id: Long
) : Parcelable,
  DatabaseId {

  val isValid: Boolean
    get() = id >= 0

  override fun toString(): String = "AttachmentId::$id"

  override fun serialize(): String = id.toString()
}
