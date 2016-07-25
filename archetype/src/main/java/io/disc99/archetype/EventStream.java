package io.disc99.archetype;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class EventStream {
    long version;
    List<DomainEvent> events;
}