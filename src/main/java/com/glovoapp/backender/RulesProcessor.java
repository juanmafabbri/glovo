package com.glovoapp.backender;

import java.util.List;

public class RulesProcessor {
    private List<Rule> rules;

    public RulesProcessor withRules(List<Rule> rules) {
        this.rules = rules;
        return this;
    }

    public boolean apply() {
        boolean apply = true;
        for (Rule rule : rules) {
            apply = rule.apply(apply);
        }
        return apply;
    }
}
