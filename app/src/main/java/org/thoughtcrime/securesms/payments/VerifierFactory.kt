package org.thoughtcrime.securesms.payments

import com.mobilecoin.lib.Verifier
import com.mobilecoin.lib.exceptions.AttestationException

/**
 * Wraps the given service configurations and provides methods to grab a fully constructed verifier instance.
 * This is to ease the addition of new service configurations moving forward, which simply need a new ServiceConfig object
 * to be added to the given list.
 */
class VerifierFactory(private vararg val serviceConfigs: ServiceConfig) {

  @Throws(AttestationException::class)
  fun createConsensusVerifier(): Verifier = createVerifier(ServiceConfig::consensus)

  @Throws(AttestationException::class)
  fun createLedgerVerifier(): Verifier = createVerifier(ServiceConfig::ledger)

  @Throws(AttestationException::class)
  fun createViewVerifier(): Verifier = createVerifier(ServiceConfig::view)

  @Throws(AttestationException::class)
  fun createReportVerifier(): Verifier = createVerifier(ServiceConfig::report)

  @Throws(AttestationException::class)
  private fun createVerifier(getConfigValue: (ServiceConfig) -> ByteArray): Verifier = serviceConfigs.fold(Verifier()) { verifier, config ->
    verifier.withMrEnclave(getConfigValue(config), null, config.hardeningAdvisories)
  }
}
