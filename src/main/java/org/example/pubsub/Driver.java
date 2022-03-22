package org.example.pubsub;

import org.reactivestreams.Subscription;

import java.util.concurrent.CompletableFuture;

public class Driver {

    public static void main(String[] args) throws InterruptedException {
        CompletableFuture.runAsync(() -> {
            CustomSubscriber<Data> subscriber = new CustomSubscriber<>(System.out::println, 10);
            CustomPublisher<Data> publisher = new CustomPublisher<>();
            Subscription subscription = new CustomSubscription<Data>(subscriber, publisher);
            subscriber.onSubscribe(subscription);
        });
        Thread.sleep(20000);
    }
}
