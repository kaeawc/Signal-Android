package org.thoughtcrime.securesms.keyboard.gif

import org.thoughtcrime.securesms.util.adapter.mapping.MappingModel

data class GifQuickSearch(val gifQuickSearchOption: GifQuickSearchOption, val selected: Boolean) : MappingModel<GifQuickSearch> {
  override fun areItemsTheSame(newItem: GifQuickSearch): Boolean = gifQuickSearchOption == newItem.gifQuickSearchOption

  override fun areContentsTheSame(newItem: GifQuickSearch): Boolean = selected == newItem.selected
}
