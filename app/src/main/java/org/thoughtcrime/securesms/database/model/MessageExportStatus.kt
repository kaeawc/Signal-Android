package org.thoughtcrime.securesms.database.model

import org.signal.core.util.DatabaseId
import org.signal.core.util.IntSerializer

/**
 * Export status for a message.
 */
enum class MessageExportStatus(val code: Int) : DatabaseId {
  UNEXPORTED(0),
  EXPORTED(1),
  ERROR(-1);

  override fun serialize(): String = Serializer.serialize(this).toString()

  companion object Serializer : IntSerializer<MessageExportStatus> {
    override fun serialize(data: MessageExportStatus): Int = data.code

    override fun deserialize(data: Int): MessageExportStatus = when (data) {
      UNEXPORTED.code -> UNEXPORTED
      EXPORTED.code -> EXPORTED
      ERROR.code -> ERROR
      else -> throw AssertionError("Unknown message export status: $data")
    }
  }
}
