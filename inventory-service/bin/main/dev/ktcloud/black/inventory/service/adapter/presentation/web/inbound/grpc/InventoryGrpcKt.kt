package dev.ktcloud.black.inventory.service.adapter.presentation.web.inbound.grpc

import dev.ktcloud.black.inventory.service.adapter.presentation.web.inbound.grpc.InventoryServiceGrpc.getServiceDescriptor
import io.grpc.CallOptions
import io.grpc.CallOptions.DEFAULT
import io.grpc.Channel
import io.grpc.Metadata
import io.grpc.MethodDescriptor
import io.grpc.ServerServiceDefinition
import io.grpc.ServerServiceDefinition.builder
import io.grpc.ServiceDescriptor
import io.grpc.Status.UNIMPLEMENTED
import io.grpc.StatusException
import io.grpc.kotlin.AbstractCoroutineServerImpl
import io.grpc.kotlin.AbstractCoroutineStub
import io.grpc.kotlin.ClientCalls.unaryRpc
import io.grpc.kotlin.ServerCalls.unaryServerMethodDefinition
import io.grpc.kotlin.StubFor
import kotlin.String
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * Holder for Kotlin coroutine-based client and server APIs for inventory.service.InventoryService.
 */
public object InventoryServiceGrpcKt {
  public const val SERVICE_NAME: String = InventoryServiceGrpc.SERVICE_NAME

  @JvmStatic
  public val serviceDescriptor: ServiceDescriptor
    get() = getServiceDescriptor()

  public val createInventoryMethod: MethodDescriptor<CreateInventoryRequest, InventoryResponseDto>
    @JvmStatic
    get() = InventoryServiceGrpc.getCreateInventoryMethod()

  public val decreaseInventoryMethod:
      MethodDescriptor<DecreaseInventoryRequest, InventoryResponseDto>
    @JvmStatic
    get() = InventoryServiceGrpc.getDecreaseInventoryMethod()

  public val increaseInventoryMethod:
      MethodDescriptor<IncreaseInventoryRequest, InventoryResponseDto>
    @JvmStatic
    get() = InventoryServiceGrpc.getIncreaseInventoryMethod()

  public val fetchInventoryMethod: MethodDescriptor<FetchInventoryRequest, InventoryResponseDto>
    @JvmStatic
    get() = InventoryServiceGrpc.getFetchInventoryMethod()

  public val fetchInventoriesMethod: MethodDescriptor<Empty, FetchInventoriesResponse>
    @JvmStatic
    get() = InventoryServiceGrpc.getFetchInventoriesMethod()

  /**
   * A stub for issuing RPCs to a(n) inventory.service.InventoryService service as suspending
   * coroutines.
   */
  @StubFor(InventoryServiceGrpc::class)
  public class InventoryServiceCoroutineStub @JvmOverloads constructor(
    channel: Channel,
    callOptions: CallOptions = DEFAULT,
  ) : AbstractCoroutineStub<InventoryServiceCoroutineStub>(channel, callOptions) {
    override fun build(channel: Channel, callOptions: CallOptions): InventoryServiceCoroutineStub =
        InventoryServiceCoroutineStub(channel, callOptions)

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun createInventory(request: CreateInventoryRequest, headers: Metadata =
        Metadata()): InventoryResponseDto = unaryRpc(
      channel,
      InventoryServiceGrpc.getCreateInventoryMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun decreaseInventory(request: DecreaseInventoryRequest, headers: Metadata =
        Metadata()): InventoryResponseDto = unaryRpc(
      channel,
      InventoryServiceGrpc.getDecreaseInventoryMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun increaseInventory(request: IncreaseInventoryRequest, headers: Metadata =
        Metadata()): InventoryResponseDto = unaryRpc(
      channel,
      InventoryServiceGrpc.getIncreaseInventoryMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun fetchInventory(request: FetchInventoryRequest, headers: Metadata =
        Metadata()): InventoryResponseDto = unaryRpc(
      channel,
      InventoryServiceGrpc.getFetchInventoryMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun fetchInventories(request: Empty, headers: Metadata = Metadata()):
        FetchInventoriesResponse = unaryRpc(
      channel,
      InventoryServiceGrpc.getFetchInventoriesMethod(),
      request,
      callOptions,
      headers
    )
  }

  /**
   * Skeletal implementation of the inventory.service.InventoryService service based on Kotlin
   * coroutines.
   */
  public abstract class InventoryServiceCoroutineImplBase(
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
  ) : AbstractCoroutineServerImpl(coroutineContext) {
    /**
     * Returns the response to an RPC for inventory.service.InventoryService.CreateInventory.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun createInventory(request: CreateInventoryRequest): InventoryResponseDto =
        throw
        StatusException(UNIMPLEMENTED.withDescription("Method inventory.service.InventoryService.CreateInventory is unimplemented"))

    /**
     * Returns the response to an RPC for inventory.service.InventoryService.DecreaseInventory.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun decreaseInventory(request: DecreaseInventoryRequest):
        InventoryResponseDto = throw
        StatusException(UNIMPLEMENTED.withDescription("Method inventory.service.InventoryService.DecreaseInventory is unimplemented"))

    /**
     * Returns the response to an RPC for inventory.service.InventoryService.IncreaseInventory.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun increaseInventory(request: IncreaseInventoryRequest):
        InventoryResponseDto = throw
        StatusException(UNIMPLEMENTED.withDescription("Method inventory.service.InventoryService.IncreaseInventory is unimplemented"))

    /**
     * Returns the response to an RPC for inventory.service.InventoryService.FetchInventory.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun fetchInventory(request: FetchInventoryRequest): InventoryResponseDto =
        throw
        StatusException(UNIMPLEMENTED.withDescription("Method inventory.service.InventoryService.FetchInventory is unimplemented"))

    /**
     * Returns the response to an RPC for inventory.service.InventoryService.FetchInventories.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun fetchInventories(request: Empty): FetchInventoriesResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method inventory.service.InventoryService.FetchInventories is unimplemented"))

    final override fun bindService(): ServerServiceDefinition = builder(getServiceDescriptor())
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = InventoryServiceGrpc.getCreateInventoryMethod(),
      implementation = ::createInventory
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = InventoryServiceGrpc.getDecreaseInventoryMethod(),
      implementation = ::decreaseInventory
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = InventoryServiceGrpc.getIncreaseInventoryMethod(),
      implementation = ::increaseInventory
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = InventoryServiceGrpc.getFetchInventoryMethod(),
      implementation = ::fetchInventory
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = InventoryServiceGrpc.getFetchInventoriesMethod(),
      implementation = ::fetchInventories
    )).build()
  }
}
