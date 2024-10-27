/*
 * Copyright 2023 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.thoughtcrime.securesms.conversation.v2.data

import org.thoughtcrime.securesms.conversation.ConversationMessage
import org.thoughtcrime.securesms.messagerequests.MessageRequestRecipientInfo
import org.thoughtcrime.securesms.util.adapter.mapping.MappingModel

sealed interface ConversationMessageElement {
  val conversationMessage: ConversationMessage
}

data class ConversationUpdate(
  override val conversationMessage: ConversationMessage
) : ConversationMessageElement,
  MappingModel<ConversationUpdate> {
  override fun areItemsTheSame(newItem: ConversationUpdate): Boolean = conversationMessage.messageRecord.id == newItem.conversationMessage.messageRecord.id

  override fun areContentsTheSame(newItem: ConversationUpdate): Boolean = false
}

data class OutgoingTextOnly(
  override val conversationMessage: ConversationMessage
) : ConversationMessageElement,
  MappingModel<OutgoingTextOnly> {
  override fun areItemsTheSame(newItem: OutgoingTextOnly): Boolean = conversationMessage.messageRecord.id == newItem.conversationMessage.messageRecord.id

  override fun areContentsTheSame(newItem: OutgoingTextOnly): Boolean = false
}

data class OutgoingMedia(
  override val conversationMessage: ConversationMessage
) : ConversationMessageElement,
  MappingModel<OutgoingMedia> {
  override fun areItemsTheSame(newItem: OutgoingMedia): Boolean = conversationMessage.messageRecord.id == newItem.conversationMessage.messageRecord.id

  override fun areContentsTheSame(newItem: OutgoingMedia): Boolean = false
}

data class IncomingTextOnly(
  override val conversationMessage: ConversationMessage
) : ConversationMessageElement,
  MappingModel<IncomingTextOnly> {
  override fun areItemsTheSame(newItem: IncomingTextOnly): Boolean = conversationMessage.messageRecord.id == newItem.conversationMessage.messageRecord.id

  override fun areContentsTheSame(newItem: IncomingTextOnly): Boolean = false
}

data class IncomingMedia(
  override val conversationMessage: ConversationMessage
) : ConversationMessageElement,
  MappingModel<IncomingMedia> {
  override fun areItemsTheSame(newItem: IncomingMedia): Boolean = conversationMessage.messageRecord.id == newItem.conversationMessage.messageRecord.id

  override fun areContentsTheSame(newItem: IncomingMedia): Boolean = false
}

data class ThreadHeader(val recipientInfo: MessageRequestRecipientInfo) : MappingModel<ThreadHeader> {
  override fun areItemsTheSame(newItem: ThreadHeader): Boolean = true

  override fun areContentsTheSame(newItem: ThreadHeader): Boolean = false
}
