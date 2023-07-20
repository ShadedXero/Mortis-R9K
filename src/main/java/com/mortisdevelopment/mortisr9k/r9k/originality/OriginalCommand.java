package com.mortisdevelopment.mortisr9k.r9k.originality;

public class OriginalCommand {

    private final String command;
    private final int length;

    public OriginalCommand(String command, int length) {
        this.command = command;
        this.length = length;
    }

    public boolean isCommand(String command) {
        String[] arguments = command.split(" ");
        if (arguments.length == 0) {
            return false;
        }
        return this.command.equalsIgnoreCase(arguments[0]);
    }

    public String getMessage(String command) {
        StringBuilder builder = new StringBuilder();
        String[] arguments = command.split(" ");
        if (arguments.length == 0) {
            return null;
        }
        for (int i = 0; i < arguments.length; i++) {
            if (i <= (length - 1)) {
                continue;
            }
            builder.append(arguments[0]);
            if (i != (arguments.length - 1)) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }

    public String getCommand() {
        return command;
    }

    public int getLength() {
        return length;
    }
}
