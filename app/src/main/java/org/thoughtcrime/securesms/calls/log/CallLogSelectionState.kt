package org.thoughtcrime.securesms.calls.log

/**
 * Selection state object for call logs.
 */
sealed interface CallLogSelectionState {
  fun contains(callId: CallLogRow.Id): Boolean
  fun isNotEmpty(totalCount: Int): Boolean

  fun count(totalCount: Int): Int

  fun selected(): Set<CallLogRow.Id>
  fun isExclusionary(): Boolean = this is Excludes

  fun select(callId: CallLogRow.Id): CallLogSelectionState
  fun deselect(callId: CallLogRow.Id): CallLogSelectionState

  fun toggle(callId: CallLogRow.Id): CallLogSelectionState = if (contains(callId)) {
    deselect(callId)
  } else {
    select(callId)
  }

  /**
   * Includes contains an opt-in list of call logs.
   */
  data class Includes(private val includes: Set<CallLogRow.Id>) : CallLogSelectionState {
    override fun contains(callId: CallLogRow.Id): Boolean = includes.contains(callId)

    override fun isNotEmpty(totalCount: Int): Boolean = includes.isNotEmpty()

    override fun count(totalCount: Int): Int = includes.size

    override fun select(callId: CallLogRow.Id): CallLogSelectionState = Includes(includes + callId)

    override fun deselect(callId: CallLogRow.Id): CallLogSelectionState = Includes(includes - callId)

    override fun selected(): Set<CallLogRow.Id> = includes
  }

  /**
   * Excludes contains an opt-out list of call logs.
   */
  data class Excludes(private val excluded: Set<CallLogRow.Id>) : CallLogSelectionState {
    override fun contains(callId: CallLogRow.Id): Boolean = !excluded.contains(callId)
    override fun isNotEmpty(totalCount: Int): Boolean = excluded.size < totalCount

    override fun count(totalCount: Int): Int = totalCount - excluded.size

    override fun select(callId: CallLogRow.Id): CallLogSelectionState = Excludes(excluded - callId)

    override fun deselect(callId: CallLogRow.Id): CallLogSelectionState = Excludes(excluded + callId)

    override fun selected(): Set<CallLogRow.Id> = excluded
  }

  object All : CallLogSelectionState by Excludes(emptySet())

  companion object {
    fun empty(): CallLogSelectionState = Includes(emptySet())
    fun selectAll(): CallLogSelectionState = All
  }
}
