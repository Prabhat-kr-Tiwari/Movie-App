package com.prabhat.movieapp.data.appSettings

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

//Generic serializer for PersistentList<T>

open class PersistentListSerializer<T>(private val dataSerializer: KSerializer<T>) :
    KSerializer<PersistentList<T>> {
    private val listSerializer = ListSerializer(dataSerializer)

    override val descriptor = listSerializer.descriptor

    override fun serialize(encoder: Encoder, value: PersistentList<T>) {
        listSerializer.serialize(encoder, value.toList())
    }

    override fun deserialize(decoder: Decoder): PersistentList<T> {
        return listSerializer.deserialize(decoder).toPersistentList()
    }
}
object SessionIdPersistentListSerializer:PersistentListSerializer<SessionId>(SessionId.serializer())
@Serializable
data class AppSettings(
    @Serializable(with = SessionIdPersistentListSerializer::class)
    val sessionId: PersistentList<SessionId> = persistentListOf()
)

@Serializable
data class SessionId(val sessionId: String="")
