package org.thoughtcrime.securesms.util

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner

/**
 * Simplifies [ViewModel] creation by providing default implementations of [ViewModelProvider.Factory]
 * and a factory producer that call through to a lambda to create the view model instance.
 *
 * Example use:
 *
 * private val viewModel: MyViewModel by viewModels(factoryProducer = ViewModelFactory.factoryProducer { MyViewModel(inputParams) })
 */
class ViewModelFactory<MODEL : ViewModel>(private val create: () -> MODEL) : ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T = create() as T

  companion object {
    fun <MODEL : ViewModel> factoryProducer(create: () -> MODEL): () -> ViewModelProvider.Factory = { ViewModelFactory(create) }
  }
}

class SavedStateViewModelFactory<MODEL : ViewModel>(
  private val create: (SavedStateHandle) -> MODEL,
  registryOwner: SavedStateRegistryOwner
) : AbstractSavedStateViewModelFactory(registryOwner, null) {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T = create(handle) as T

  companion object {
    fun <MODEL : ViewModel> factoryProducer(
      create: (SavedStateHandle) -> MODEL,
      registryOwnerProducer: () -> SavedStateRegistryOwner
    ): () -> ViewModelProvider.Factory = { SavedStateViewModelFactory(create, registryOwnerProducer()) }
  }
}

@MainThread
inline fun <reified VM : ViewModel> ComponentActivity.viewModel(
  noinline create: () -> VM
): Lazy<VM> = viewModels(
  factoryProducer = ViewModelFactory.factoryProducer(create)
)

@MainThread
inline fun <reified VM : ViewModel> Fragment.viewModel(
  noinline create: () -> VM
): Lazy<VM> = viewModels(
  factoryProducer = ViewModelFactory.factoryProducer(create)
)

@MainThread
inline fun <reified VM : ViewModel> Fragment.savedStateViewModel(
  noinline create: (SavedStateHandle) -> VM
): Lazy<VM> = viewModels(
  factoryProducer = SavedStateViewModelFactory.factoryProducer(create) { this }
)

@MainThread
inline fun <reified VM : ViewModel> Fragment.activitySavedStateViewModel(
  noinline create: (SavedStateHandle) -> VM
): Lazy<VM> = activityViewModels(
  factoryProducer = SavedStateViewModelFactory.factoryProducer(create) { requireActivity() }
)

@MainThread
inline fun <reified VM : ViewModel> ComponentActivity.savedStateViewModel(
  noinline create: (SavedStateHandle) -> VM
): Lazy<VM> = viewModels(
  factoryProducer = SavedStateViewModelFactory.factoryProducer(create) { this }
)

@MainThread
inline fun <reified VM : ViewModel> Fragment.activityViewModel(
  noinline create: () -> VM
): Lazy<VM> = activityViewModels(
  factoryProducer = ViewModelFactory.factoryProducer(create)
)

@Suppress("ReplaceGetOrSet")
@MainThread
inline fun <reified VM : ViewModel> Fragment.createActivityViewModel(noinline create: () -> VM): VM = ViewModelProvider(requireActivity().viewModelStore, ViewModelFactory { create() }).get(VM::class.java)
