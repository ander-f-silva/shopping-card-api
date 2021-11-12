package io.sc.checkout.infrastructure.grpc;

import discount.DiscountGrpc;
import io.grpc.ManagedChannel;
import io.micronaut.context.annotation.Factory;
import io.micronaut.grpc.annotation.GrpcChannel;
import jakarta.inject.Singleton;

@Factory
class GrpcServicesFactory {

    @Singleton
    DiscountGrpc.DiscountStub discountStub(@GrpcChannel("discount") ManagedChannel channel) {
        return DiscountGrpc.newStub(
                channel
        );
    }
}
