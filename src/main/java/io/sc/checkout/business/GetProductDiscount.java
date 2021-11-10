package io.sc.checkout.business;

import com.google.common.util.concurrent.AtomicDouble;
import discount.DiscountGrpc;
import discount.DiscountOuterClass;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Singleton;

@Singleton
public class GetProductDiscount {
    private DiscountGrpc.DiscountStub gRPCDiscountAsyncService;

    private AtomicDouble percentageDiscount = new AtomicDouble(0);

    public GetProductDiscount(DiscountGrpc.DiscountStub gRPCDiscountService) {
        this.gRPCDiscountAsyncService = gRPCDiscountService;
    }

    public Double getPercent(Integer productId) {
        var request = DiscountOuterClass.
                GetDiscountRequest
                .newBuilder()
                .setProductID(productId)
                .build();

       gRPCDiscountAsyncService.getDiscount(request, buildStreamObserver());

       return percentageDiscount.doubleValue();
    }

    private StreamObserver<DiscountOuterClass.GetDiscountResponse> buildStreamObserver() {
         return new StreamObserver<>() {
             @Override
             public void onNext(DiscountOuterClass.GetDiscountResponse value) {
                 percentageDiscount.addAndGet(value.getPercentage());
             }

             @Override
             public void onError(Throwable throwable) {
                 percentageDiscount.addAndGet(0);
             }

             @Override
             public void onCompleted() {

             }
         };
    }
}
