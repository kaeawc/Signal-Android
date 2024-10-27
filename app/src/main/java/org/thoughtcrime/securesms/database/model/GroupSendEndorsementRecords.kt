/*
 * Copyright 2024 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.thoughtcrime.securesms.database.model

import org.signal.libsignal.zkgroup.groupsend.GroupSendEndorsement
import org.thoughtcrime.securesms.recipients.RecipientId

/**
 * Contains the individual group send endorsements for a specific group
 * source from our local db.
 */
data class GroupSendEndorsementRecords(val endorsements: Map<RecipientId, GroupSendEndorsement?>) {
  fun getEndorsement(recipientId: RecipientId): GroupSendEndorsement? = endorsements[recipientId]

  fun isMissingAnyEndorsements(): Boolean = endorsements.values.any { it == null }
}
