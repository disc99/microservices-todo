package io.disc99.todo.application;

import io.disc99.archetype.CommandBus;
import io.disc99.archetype.EventBus;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TodoService {

    private CommandBus commandBus;
    private EventBus eventBus;

    public void add(Add add) {

        commandBus.dispatch(add);
    }

    public void modify(Modify modify) {
        commandBus.dispatch(modify);
    }

    public void doit(Do aDo) {
        commandBus.dispatch(aDo);
    }
}
