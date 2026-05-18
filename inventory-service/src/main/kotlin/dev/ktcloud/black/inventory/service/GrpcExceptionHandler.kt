package dev.ktcloud.black.inventory.service

import dev.ktcloud.black.common.exception.CustomException
import io.grpc.Status
import io.grpc.StatusException
import net.devh.boot.grpc.server.advice.GrpcAdvice
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler

@GrpcAdvice
class GrpcExceptionHandler {

    @GrpcExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): StatusException {
        val grpcStatus = when (e.status) {
            404 -> Status.NOT_FOUND
            400 -> Status.INVALID_ARGUMENT
            409 -> Status.ALREADY_EXISTS
            403 -> Status.PERMISSION_DENIED
            401 -> Status.UNAUTHENTICATED
            else -> Status.INTERNAL
        }
        return grpcStatus.withDescription(e.message).asException()
    }
}
