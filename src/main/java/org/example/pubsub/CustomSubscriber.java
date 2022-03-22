package org.example.pubsub;

import lombok.SneakyThrows;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.function.Consumer;

public class CustomSubscriber<T> implements Subscriber<T> {

    private Consumer<T> consumer;
    private long dataCount;

    public CustomSubscriber(Consumer<T> consumer, long dataCount){
        this.consumer = consumer;
        this.dataCount = dataCount;
    }

    @SneakyThrows
    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(this.dataCount);
        Thread.sleep(5000);
        subscription.cancel();
    }

    @Override
    public void onNext(T t) {
        this.consumer.accept(t);
    }

    @Override
    public void onError(Throwable t) {
        this.consumer.accept((T) Data.builder().value(t.getClass().getName()).threadId(Thread.currentThread().getId()).build());
    }

    @Override
    public void onComplete() {
        this.consumer.accept((T) Data.builder().value("OnComplete").threadId(Thread.currentThread().getId()).build());
    }
}
