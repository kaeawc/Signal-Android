package org.thoughtcrime.securesms.conversation.colors.ui

import org.thoughtcrime.securesms.util.adapter.mapping.MappingModel

class CustomColorMappingModel : MappingModel<CustomColorMappingModel> {
  override fun areItemsTheSame(newItem: CustomColorMappingModel): Boolean = true

  override fun areContentsTheSame(newItem: CustomColorMappingModel): Boolean = true
}
