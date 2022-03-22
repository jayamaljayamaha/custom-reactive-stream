package org.example.pubsub;

import lombok.SneakyThrows;
import org.reactivestreams.Subscription;

public class CustomSubscription<T> implements Subscription {

    CustomSubscriber<T> subscriber;
    CustomPublisher<T> publisher;

    public CustomSubscription(CustomSubscriber<T> subscriber, CustomPublisher<T> publisher) {
        this.subscriber = subscriber;
        this.publisher = publisher;
    }

    @SneakyThrows
    @Override
    public void request(long n) {
        publisher.doSubscribe(n, subscriber);
    }

    @Override
    public void cancel() {
        publisher.cancel();
    }
}
