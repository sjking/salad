package com.github.kliewkliew.salad.api.sync

import com.github.kliewkliew.salad.serde.Serde
import com.lambdaworks.redis.api.sync.RedisKeyCommands

/**
  * Wrap the lettuce API to provide an idiomatic Scala API.
  * @tparam EK The key storage encoding.
  * @tparam EV The value storage encoding.
  * @tparam API The lettuce API to wrap.
  */
trait SaladKeySyncCommands[EK,EV,API] {
  def underlying: API with RedisKeyCommands[EK,EV]

  /**
    * Delete a key-value pair.
    * @param key The key to delete.
    * @param keySerde The serde to encode the key.
    * @tparam DK The unencoded key type.
    * @return A Boolean indicating success.
    */
  def del[DK](key: DK)
             (implicit keySerde: Serde[DK,EK])
  : Boolean =
  underlying.del(keySerde.serialize(key)) == 1

  /**
    * Set a key's TTL in seconds.
    * @param key The key to expire.
    * @param ex The TTL in seconds.
    * @param keySerde The serde to encode the key.
    * @tparam DK The unencoded key type.
    * @return A Boolean indicating success.
    */
  def expire[DK](key: DK, ex: Long)
                (implicit keySerde: Serde[DK,EK])
  : Boolean =
  underlying.expire(keySerde.serialize(key), ex)

  /**
    * Set a key's TTL in milliseconds.
    * @param key The key to expire.
    * @param px The TTL in milliseconds.
    * @param keySerde The serde to encode the key.
    * @tparam DK The unencoded key type.
    * @return A Boolean indicating success.
    */
  def pexpire[DK](key: DK, px: Long)
                 (implicit keySerde: Serde[DK,EK])
  : Boolean =
  underlying.pexpire(keySerde.serialize(key), px)

  /**
    * Remove the expiry from a key.
    * @param key The key for which to unset expiry.
    * @param keySerde The serde to encode the key.
    * @tparam DK The unencoded key type.
    * @return A Boolean indicating success.
    */
  def persist[DK](key: DK)
                 (implicit keySerde: Serde[DK,EK])
  : Boolean =
  underlying.persist(keySerde.serialize(key))

}