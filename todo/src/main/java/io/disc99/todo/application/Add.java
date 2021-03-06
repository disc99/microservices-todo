package io.disc99.todo.application;

import io.disc99.archetype.Command;
import io.disc99.todo.domain.Doing;
import io.disc99.archetype.Identify;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
public class Add implements Command {

    private String doing;

    public Add(String doing) {
        this.doing = doing;
    }
}
