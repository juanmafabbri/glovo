package com.glovoapp.backender;

import java.util.List;

import com.glovoapp.backender.ConfigurationParameters;
import com.glovoapp.backender.Rule;
import org.springframework.stereotype.Component;

@Component
public class BoxRule extends Rule {

    private final ConfigurationParameters configurationParameters;
    private List<String> wordsRequireBox;

    public BoxRule(ConfigurationParameters configurationParameters) {
        this.configurationParameters = configurationParameters;
    }

    @Override
    public boolean apply(boolean previousRuleResult) {

        wordsRequireBox = configurationParameters.getWordsRequireBox();

        if (getCourier().getBox()) {
            return true && previousRuleResult;
        }

        for (String rb : wordsRequireBox) {
            if (getOrder().descriptionContainsTheWord(rb)) {
                return false;
            }
        }
        return true && previousRuleResult;
    }
}
