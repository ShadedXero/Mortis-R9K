package com.mortisdevelopment.mortisr9k.r9k.originality;

import java.util.List;

public class OriginalSettings {

    private final boolean enabled;
    private final boolean modifyMsgOriginal;
    private final List<OriginalCommand> commands;
    private final List<String> commandsOnUnOriginal;

    public OriginalSettings(boolean enabled, boolean modifyMsgOriginal, List<OriginalCommand> commands, List<String> commandsOnUnOriginal) {
        this.enabled = enabled;
        this.modifyMsgOriginal = modifyMsgOriginal;
        this.commands = commands;
        this.commandsOnUnOriginal = commandsOnUnOriginal;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isModifyMsgOriginal() {
        return modifyMsgOriginal;
    }

    public List<OriginalCommand> getCommands() {
        return commands;
    }

    public List<String> getCommandsOnUnOriginal() {
        return commandsOnUnOriginal;
    }
}
