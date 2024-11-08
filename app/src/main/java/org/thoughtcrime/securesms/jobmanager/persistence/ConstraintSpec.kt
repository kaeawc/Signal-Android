package org.thoughtcrime.securesms.jobmanager.persistence

data class ConstraintSpec(
  val jobSpecId: String,
  val factoryKey: String,
  val isMemoryOnly: Boolean
) {
  override fun toString(): String = "jobSpecId: JOB::$jobSpecId | factoryKey: $factoryKey | memoryOnly: $isMemoryOnly"
}
