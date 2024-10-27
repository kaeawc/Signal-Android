package org.thoughtcrime.securesms.keyboard.emoji

import android.content.Context
import android.graphics.drawable.Drawable
import org.thoughtcrime.securesms.R
import org.thoughtcrime.securesms.components.emoji.RecentEmojiPageModel
import org.thoughtcrime.securesms.emoji.EmojiCategory
import org.thoughtcrime.securesms.keyboard.KeyboardPageCategoryIconMappingModel
import org.thoughtcrime.securesms.util.ThemeUtil

class RecentsMappingModel(override val selected: Boolean) : KeyboardPageCategoryIconMappingModel<RecentsMappingModel> {
  override val key: String = RecentEmojiPageModel.KEY

  override fun getIcon(context: Context): Drawable = requireNotNull(ThemeUtil.getThemedDrawable(context, R.attr.emoji_category_recent))

  override fun areItemsTheSame(newItem: RecentsMappingModel): Boolean = newItem.key == key

  override fun areContentsTheSame(newItem: RecentsMappingModel): Boolean = areItemsTheSame(newItem) && selected == newItem.selected
}

class EmojiCategoryMappingModel(private val emojiCategory: EmojiCategory, override val selected: Boolean) : KeyboardPageCategoryIconMappingModel<EmojiCategoryMappingModel> {
  override val key: String = emojiCategory.key

  override fun getIcon(context: Context): Drawable = requireNotNull(ThemeUtil.getThemedDrawable(context, emojiCategory.icon))

  override fun areItemsTheSame(newItem: EmojiCategoryMappingModel): Boolean = newItem.key == key

  override fun areContentsTheSame(newItem: EmojiCategoryMappingModel): Boolean = areItemsTheSame(newItem) &&
    selected == newItem.selected &&
    newItem.emojiCategory == emojiCategory
}
