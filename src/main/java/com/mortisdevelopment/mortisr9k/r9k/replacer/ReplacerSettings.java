package com.mortisdevelopment.mortisr9k.r9k.replacer;

import java.util.Map;
import java.util.regex.Pattern;

public class ReplacerSettings {

    private final boolean notifyOnBlacklist;
    private final boolean modifyMsg;
    private final Map<String, String> replacements;
    private final Pattern pattern;
    private final Pattern blacklistPattern;

    public ReplacerSettings(boolean notifyOnBlacklist, boolean modifyMsg, Map<String, String> replacements, Pattern pattern, Pattern blacklistPattern) {
        this.notifyOnBlacklist = notifyOnBlacklist;
        this.modifyMsg = modifyMsg;
        this.replacements = replacements;
        this.pattern = pattern;
        this.blacklistPattern = blacklistPattern;
    }

    public boolean isNotifyOnBlacklist() {
        return notifyOnBlacklist;
    }

    public boolean isModifyMsg() {
        return modifyMsg;
    }

    public Map<String, String> getReplacements() {
        return replacements;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public Pattern getBlacklistPattern() {
        return blacklistPattern;
    }
}
