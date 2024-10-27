package org.thoughtcrime.securesms.messages.protocol

import org.signal.libsignal.protocol.IdentityKey
import org.signal.libsignal.protocol.IdentityKeyPair
import org.signal.libsignal.protocol.SignalProtocolAddress
import org.signal.libsignal.protocol.groups.state.SenderKeyRecord
import org.signal.libsignal.protocol.state.IdentityKeyStore
import org.signal.libsignal.protocol.state.KyberPreKeyRecord
import org.signal.libsignal.protocol.state.PreKeyRecord
import org.signal.libsignal.protocol.state.SessionRecord
import org.signal.libsignal.protocol.state.SignedPreKeyRecord
import org.thoughtcrime.securesms.keyvalue.SignalStore
import org.whispersystems.signalservice.api.SignalServiceAccountDataStore
import org.whispersystems.signalservice.api.push.DistributionId
import org.whispersystems.signalservice.api.push.ServiceId
import java.util.UUID

/**
 * The wrapper around all of the Buffered protocol stores. Designed to perform operations in memory,
 * then [flushToDisk] at set intervals.
 */
class BufferedSignalServiceAccountDataStore(selfServiceId: ServiceId) : SignalServiceAccountDataStore {

  private val identityStore: BufferedIdentityKeyStore = if (selfServiceId == SignalStore.account.pni) {
    BufferedIdentityKeyStore(selfServiceId, SignalStore.account.pniIdentityKey, SignalStore.account.pniRegistrationId)
  } else {
    BufferedIdentityKeyStore(selfServiceId, SignalStore.account.aciIdentityKey, SignalStore.account.registrationId)
  }

  private val oneTimePreKeyStore: BufferedOneTimePreKeyStore = BufferedOneTimePreKeyStore(selfServiceId)
  private val signedPreKeyStore: BufferedSignedPreKeyStore = BufferedSignedPreKeyStore(selfServiceId)
  private val kyberPreKeyStore: BufferedKyberPreKeyStore = BufferedKyberPreKeyStore(selfServiceId)
  private val sessionStore: BufferedSessionStore = BufferedSessionStore(selfServiceId)
  private val senderKeyStore: BufferedSenderKeyStore = BufferedSenderKeyStore()

  override fun getIdentityKeyPair(): IdentityKeyPair = identityStore.identityKeyPair

  override fun getLocalRegistrationId(): Int = identityStore.localRegistrationId

  override fun saveIdentity(address: SignalProtocolAddress, identityKey: IdentityKey): Boolean = identityStore.saveIdentity(address, identityKey)

  override fun isTrustedIdentity(address: SignalProtocolAddress, identityKey: IdentityKey, direction: IdentityKeyStore.Direction): Boolean = identityStore.isTrustedIdentity(address, identityKey, direction)

  override fun getIdentity(address: SignalProtocolAddress): IdentityKey? = identityStore.getIdentity(address)

  override fun loadPreKey(preKeyId: Int): PreKeyRecord = oneTimePreKeyStore.loadPreKey(preKeyId)

  override fun storePreKey(preKeyId: Int, record: PreKeyRecord) = oneTimePreKeyStore.storePreKey(preKeyId, record)

  override fun containsPreKey(preKeyId: Int): Boolean = oneTimePreKeyStore.containsPreKey(preKeyId)

  override fun removePreKey(preKeyId: Int) {
    oneTimePreKeyStore.removePreKey(preKeyId)
  }

  override fun loadSession(address: SignalProtocolAddress): SessionRecord = sessionStore.loadSession(address)

  override fun loadExistingSessions(addresses: MutableList<SignalProtocolAddress>): List<SessionRecord> = sessionStore.loadExistingSessions(addresses)

  override fun getSubDeviceSessions(name: String): MutableList<Int> = sessionStore.getSubDeviceSessions(name)

  override fun storeSession(address: SignalProtocolAddress, record: SessionRecord) {
    sessionStore.storeSession(address, record)
  }

  override fun containsSession(address: SignalProtocolAddress): Boolean = sessionStore.containsSession(address)

  override fun deleteSession(address: SignalProtocolAddress) = sessionStore.deleteSession(address)

  override fun deleteAllSessions(name: String) {
    sessionStore.deleteAllSessions(name)
  }

  override fun loadSignedPreKey(signedPreKeyId: Int): SignedPreKeyRecord = signedPreKeyStore.loadSignedPreKey(signedPreKeyId)

  override fun loadSignedPreKeys(): List<SignedPreKeyRecord> = signedPreKeyStore.loadSignedPreKeys()

  override fun storeSignedPreKey(signedPreKeyId: Int, record: SignedPreKeyRecord) {
    signedPreKeyStore.storeSignedPreKey(signedPreKeyId, record)
  }

  override fun containsSignedPreKey(signedPreKeyId: Int): Boolean = signedPreKeyStore.containsSignedPreKey(signedPreKeyId)

  override fun removeSignedPreKey(signedPreKeyId: Int) {
    signedPreKeyStore.removeSignedPreKey(signedPreKeyId)
  }

  override fun loadKyberPreKey(kyberPreKeyId: Int): KyberPreKeyRecord = kyberPreKeyStore.loadKyberPreKey(kyberPreKeyId)

  override fun loadKyberPreKeys(): List<KyberPreKeyRecord> = kyberPreKeyStore.loadKyberPreKeys()

  override fun storeKyberPreKey(kyberPreKeyId: Int, record: KyberPreKeyRecord) {
    kyberPreKeyStore.storeKyberPreKey(kyberPreKeyId, record)
  }

  override fun storeLastResortKyberPreKey(kyberPreKeyId: Int, kyberPreKeyRecord: KyberPreKeyRecord) {
    kyberPreKeyStore.storeKyberPreKey(kyberPreKeyId, kyberPreKeyRecord)
  }

  override fun containsKyberPreKey(kyberPreKeyId: Int): Boolean = kyberPreKeyStore.containsKyberPreKey(kyberPreKeyId)

  override fun markKyberPreKeyUsed(kyberPreKeyId: Int) = kyberPreKeyStore.markKyberPreKeyUsed(kyberPreKeyId)

  override fun deleteAllStaleOneTimeEcPreKeys(threshold: Long, minCount: Int) {
    error("Should not happen during the intended usage pattern of this class")
  }

  override fun markAllOneTimeEcPreKeysStaleIfNecessary(staleTime: Long) {
    error("Should not happen during the intended usage pattern of this class")
  }

  override fun removeKyberPreKey(kyberPreKeyId: Int) {
    kyberPreKeyStore.removeKyberPreKey(kyberPreKeyId)
  }

  override fun markAllOneTimeKyberPreKeysStaleIfNecessary(staleTime: Long) {
    kyberPreKeyStore.markAllOneTimeKyberPreKeysStaleIfNecessary(staleTime)
  }

  override fun deleteAllStaleOneTimeKyberPreKeys(threshold: Long, minCount: Int) {
    kyberPreKeyStore.deleteAllStaleOneTimeKyberPreKeys(threshold, minCount)
  }

  override fun loadLastResortKyberPreKeys(): List<KyberPreKeyRecord> = kyberPreKeyStore.loadLastResortKyberPreKeys()

  override fun storeSenderKey(sender: SignalProtocolAddress, distributionId: UUID, record: SenderKeyRecord) {
    senderKeyStore.storeSenderKey(sender, distributionId, record)
  }

  override fun loadSenderKey(sender: SignalProtocolAddress, distributionId: UUID): SenderKeyRecord? = senderKeyStore.loadSenderKey(sender, distributionId)

  override fun archiveSession(address: SignalProtocolAddress?) {
    sessionStore.archiveSession(address)
  }

  override fun getAllAddressesWithActiveSessions(addressNames: MutableList<String>): Map<SignalProtocolAddress, SessionRecord> = sessionStore.getAllAddressesWithActiveSessions(addressNames)

  override fun getSenderKeySharedWith(distributionId: DistributionId?): MutableSet<SignalProtocolAddress> = senderKeyStore.getSenderKeySharedWith(distributionId)

  override fun markSenderKeySharedWith(distributionId: DistributionId, addresses: MutableCollection<SignalProtocolAddress>) {
    senderKeyStore.markSenderKeySharedWith(distributionId, addresses)
  }

  override fun clearSenderKeySharedWith(addresses: MutableCollection<SignalProtocolAddress>) {
    senderKeyStore.clearSenderKeySharedWith(addresses)
  }

  override fun isMultiDevice(): Boolean {
    error("Should not happen during the intended usage pattern of this class")
  }

  fun flushToDisk(persistentStore: SignalServiceAccountDataStore) {
    identityStore.flushToDisk(persistentStore)
    oneTimePreKeyStore.flushToDisk(persistentStore)
    kyberPreKeyStore.flushToDisk(persistentStore)
    signedPreKeyStore.flushToDisk(persistentStore)
    sessionStore.flushToDisk(persistentStore)
    senderKeyStore.flushToDisk(persistentStore)
  }
}
