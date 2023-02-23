package br.com.empiricus.statusviajante.integration.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object DateSerialize: KSerializer<String> {


    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: String) {
        val date = value.split("-")
        encoder.encodeString("${date[2]}/${date[1]}/${date[0]}")
    }

    override fun deserialize(decoder: Decoder): String = decoder.decodeString().replace("-", "/")
}