package org.example.pubsub;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.nio.channels.ClosedByInterruptException;
import java.util.concurrent.CompletableFuture;
import java.util.stream.LongStream;

public class CustomPublisher<T> implements Publisher<T> {

    private long dataCount;
    private boolean isCanceled;

    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        CompletableFuture.runAsync(() -> this.publishData(subscriber, this.dataCount));
    }

    public void doSubscribe(long dataCount, Subscriber<? super T> subscriber) {
        this.isCanceled = false;
        this.dataCount = dataCount;
        this.subscribe(subscriber);
    }

    public void cancel(){
        this.isCanceled = true;
    }

    public void publishData(Subscriber<? super T> subscriber, long dataCount) {
        try {
            LongStream.range(0, dataCount).mapToObj(number -> "Value " + number).forEach(value -> {
                try {
                    Thread.sleep(1000);
                    if(isCanceled){
                        throw new ClosedByInterruptException();
                    }
                    subscriber.onNext((T) Data.builder().value(value).threadId(Thread.currentThread().getId()).build());
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                } catch (ClosedByInterruptException e) {
                    throw new UnexpectedCancelOperationException();
                }
            });

        } catch (RuntimeException e){
            subscriber.onError(e);
            return;
        }
        subscriber.onComplete();
    }

}
