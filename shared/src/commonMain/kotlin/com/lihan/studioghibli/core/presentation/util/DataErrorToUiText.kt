package com.lihan.studioghibli.core.presentation.util

import com.lihan.studioghibli.core.domain.util.DataError
import studioghibli.shared.generated.resources.Res
import studioghibli.shared.generated.resources.error_bad_request
import studioghibli.shared.generated.resources.error_conflict
import studioghibli.shared.generated.resources.error_disk_full
import studioghibli.shared.generated.resources.error_forbidden
import studioghibli.shared.generated.resources.error_local_not_found
import studioghibli.shared.generated.resources.error_no_internet
import studioghibli.shared.generated.resources.error_not_found
import studioghibli.shared.generated.resources.error_payload_too_large
import studioghibli.shared.generated.resources.error_request_timeout
import studioghibli.shared.generated.resources.error_serialization
import studioghibli.shared.generated.resources.error_server_error
import studioghibli.shared.generated.resources.error_service_unavailable
import studioghibli.shared.generated.resources.error_too_many_requests
import studioghibli.shared.generated.resources.error_unauthorized
import studioghibli.shared.generated.resources.error_unknown_local
import studioghibli.shared.generated.resources.error_unknown_network

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(Res.string.error_request_timeout)
        DataError.Network.UNAUTHORIZED -> UiText.StringResource(Res.string.error_unauthorized)
        DataError.Network.FORBIDDEN -> UiText.StringResource(Res.string.error_forbidden)
        DataError.Network.NOT_FOUND -> UiText.StringResource(Res.string.error_not_found)
        DataError.Network.CONFLICT -> UiText.StringResource(Res.string.error_conflict)
        DataError.Network.TOO_MANY_REQUESTS -> UiText.StringResource(Res.string.error_too_many_requests)
        DataError.Network.NO_INTERNET -> UiText.StringResource(Res.string.error_no_internet)
        DataError.Network.PAYLOAD_TOO_LARGE -> UiText.StringResource(Res.string.error_payload_too_large)
        DataError.Network.SERVER_ERROR -> UiText.StringResource(Res.string.error_server_error)
        DataError.Network.SERVICE_UNAVAILABLE -> UiText.StringResource(Res.string.error_service_unavailable)
        DataError.Network.SERIALIZATION -> UiText.StringResource(Res.string.error_serialization)
        DataError.Network.BAD_REQUEST -> UiText.StringResource(Res.string.error_bad_request)
        DataError.Network.UNKNOWN -> UiText.StringResource(Res.string.error_unknown_network)
        DataError.Local.DISK_FULL -> UiText.StringResource(Res.string.error_disk_full)
        DataError.Local.NOT_FOUND -> UiText.StringResource(Res.string.error_local_not_found)
        DataError.Local.UNKNOWN -> UiText.StringResource(Res.string.error_unknown_local)
    }
}
