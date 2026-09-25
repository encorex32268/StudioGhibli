package com.lihan.studioghibli.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource

@Immutable
sealed interface UiText {
    data class DynamicString(val value: String) : UiText

    class StringResource(
        val resource: org.jetbrains.compose.resources.StringResource,
        val args: Array<Any> = emptyArray()
    ) : UiText {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is StringResource) return false
            if (resource != other.resource) return false
            if (!args.contentEquals(other.args)) return false
            return true
        }

        override fun hashCode(): Int {
            var result = resource.hashCode()
            result = 31 * result + args.contentHashCode()
            return result
        }
    }

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(resource, *args)
        }
    }

    suspend fun asStringAsync(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> getString(resource, *args)
        }
    }
}
