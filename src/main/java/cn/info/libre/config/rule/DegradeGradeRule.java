package cn.info.libre.config.rule;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DegradeGradeRule {

    String KEY = "degradeOrder";

    @PostConstruct
    public void initRules() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource(KEY);
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        rule.setCount(100);
        rule.setTimeWindow(5);
        DegradeRuleManager.loadRules(rules);
    }
}
