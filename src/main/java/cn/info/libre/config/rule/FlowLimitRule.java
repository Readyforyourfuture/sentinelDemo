package cn.info.libre.config.rule;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class FlowLimitRule {

    String KEY = "queryOrder";

    @PostConstruct
    public void initOrderRules() {
        List<FlowRule> flowRules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource(KEY);
        rule.setLimitApp("default");
        rule.setCount(2);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRules.add(rule);
        FlowRuleManager.loadRules(flowRules);
    }
}
