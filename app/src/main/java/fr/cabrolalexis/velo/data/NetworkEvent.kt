package fr.cabrolalexis.velo.data

sealed class NetworkEvent {
    data class Error(val error: Throwable) : NetworkEvent()
    object InProgress : NetworkEvent()
    object Success : NetworkEvent()
    object None : NetworkEvent()
}