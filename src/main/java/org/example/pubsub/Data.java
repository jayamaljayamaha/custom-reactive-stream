package org.example.pubsub;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class Data {
    private String value;
    private long threadId;
}
