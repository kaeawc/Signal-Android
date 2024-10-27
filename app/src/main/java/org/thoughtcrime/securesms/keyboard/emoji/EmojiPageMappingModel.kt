package org.thoughtcrime.securesms.keyboard.emoji

import org.thoughtcrime.securesms.components.emoji.EmojiPageModel
import org.thoughtcrime.securesms.util.adapter.mapping.MappingModel

class EmojiPageMappingModel(val key: String, val emojiPageModel: EmojiPageModel) : MappingModel<EmojiPageMappingModel> {
  override fun areItemsTheSame(newItem: EmojiPageMappingModel): Boolean = key == newItem.key

  override fun areContentsTheSame(newItem: EmojiPageMappingModel): Boolean = areItemsTheSame(newItem) &&
    newItem.emojiPageModel.spriteUri == emojiPageModel.spriteUri &&
    newItem.emojiPageModel.iconAttr == emojiPageModel.iconAttr
}
