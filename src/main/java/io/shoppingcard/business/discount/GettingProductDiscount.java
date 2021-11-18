package io.shoppingcard.business.discount;

import com.google.common.util.concurrent.AtomicDouble;
import discount.DiscountGrpc;
import discount.DiscountOuterClass;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
class GettingProductDiscount implements GetProductDiscount {
    private static final Logger logger = LoggerFactory.getLogger(GettingProductDiscount.class);

    private DiscountGrpc.DiscountStub gRPCDiscountAsyncService;

    private AtomicDouble discount = new AtomicDouble(0);

    public GettingProductDiscount(DiscountGrpc.DiscountStub gRPCDiscountService) {
        this.gRPCDiscountAsyncService = gRPCDiscountService;
    }

    public Float getPercent(final Long productId) {
        var request = DiscountOuterClass.
                GetDiscountRequest
                .newBuilder()
                .setProductID(productId.intValue())
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
                logger.warn("[event: get result discount] Message: The discount system is out of order");
                discount.set(0);
            }

            @Override
            public void onCompleted() {
                logger.info("[event: get result discount] Message: The discount system is OK");
            }
        };
    }
}
