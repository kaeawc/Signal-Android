package org.thoughtcrime.securesms.jobmanager.persistence

import java.util.Locale

data class DependencySpec(
  val jobId: String,
  val dependsOnJobId: String,
  val isMemoryOnly: Boolean
) {
  override fun toString(): String = String.format(Locale.US, "jobSpecId: JOB::%s | dependsOnJobSpecId: JOB::%s | memoryOnly: %b", jobId, dependsOnJobId, isMemoryOnly)
}
