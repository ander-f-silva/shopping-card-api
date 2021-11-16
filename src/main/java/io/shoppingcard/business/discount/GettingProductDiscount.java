package io.shoppingcard.business.discount;

import com.google.common.util.concurrent.AtomicDouble;
import discount.DiscountGrpc;
import discount.DiscountOuterClass;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Singleton;

//TODO: Add logger to debug
@Singleton
class GettingProductDiscount implements GetProductDiscount {
    private DiscountGrpc.DiscountStub gRPCDiscountAsyncService;

    private AtomicDouble discount = new AtomicDouble(0);

    public GettingProductDiscount(DiscountGrpc.DiscountStub gRPCDiscountService) {
        this.gRPCDiscountAsyncService = gRPCDiscountService;
    }

    public Float getPercent(Integer productId) {
        var request = DiscountOuterClass.
                GetDiscountRequest
                .newBuilder()
                .setProductID(productId)
                .build();

        gRPCDiscountAsyncService.getDiscount(request, getResponseObserver());

        return discount.floatValue();
    }

    private StreamObserver<DiscountOuterClass.GetDiscountResponse> getResponseObserver() {
        return new StreamObserver<>() {
            @Override
            public void onNext(DiscountOuterClass.GetDiscountResponse value) {
                discount.set(value.getPercentage());
            }

            @Override
            public void onError(Throwable t) {
                discount.set(0);
            }

            @Override
            public void onCompleted() {
            }
        };
    }
}
