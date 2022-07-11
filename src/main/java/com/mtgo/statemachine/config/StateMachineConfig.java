package com.mtgo.statemachine.config;

import com.mtgo.statemachine.logging.StateMachineLogListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineModelConfigurer;
import org.springframework.statemachine.config.model.StateMachineModelFactory;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.uml.UmlStateMachineModelFactory;

import org.springframework.util.StringUtils;

import java.util.Map;

@Configuration
public class StateMachineConfig {

  @Bean
  public StateMachineLogListener stateMachineLogListener() {
    return new StateMachineLogListener();
  }

  @Configuration
  @EnableStateMachineFactory
  public static class Config extends StateMachineConfigurerAdapter<String, String> {

    @Autowired
    private StateMachineLogListener stateMachineLogListener;

    @Override
    public void configure(StateMachineConfigurationConfigurer<String, String> config)
      throws Exception {
      config
        .withConfiguration()
        .autoStartup(true)
        .listener(stateMachineLogListener);
    }

    @Override
    public void configure(StateMachineModelConfigurer<String, String> model)
      throws Exception {
      model
        .withModel()
        .factory(modelFactory());
    }

    @Bean
    public StateMachineModelFactory<String, String> modelFactory() {
      return new UmlStateMachineModelFactory("classpath:ordershipping.uml");
    }

    @Bean
    public Action<String, String> entryReceiveOrder() {
      return (context) -> {
        String customer = context.getMessageHeaders().get("customer", String.class);
        if (StringUtils.hasText(customer)) {
          context.getExtendedState().getVariables().put("customer", customer);
        }
        String order = context.getMessageHeaders().get("order", String.class);
        if (StringUtils.hasText(order)) {
          context.getExtendedState().getVariables().put("order", order);
        }
      };
    }

    @Bean
    public Action<String, String> entrySendReminder() {
      return (context) -> {
        System.out.println("REMIND");
      };
    }

    @Bean
    public Action<String, String> entryHandleOrder() {
      return (context) -> {
        if (context.getMessageHeaders().containsKey("makeProdPlan")) {
          context.getExtendedState().getVariables().put("makeProdPlan", true);
        }
        if (context.getMessageHeaders().containsKey("produce")) {
          context.getExtendedState().getVariables().put("produce", true);
        }
      };
    }

    @Bean
    public Guard<String, String> orderOk() {
      return (context) -> {
        Map<Object, Object> variables = context.getExtendedState().getVariables();
        return variables.containsKey("customer") && variables.containsKey("order");
      };
    }

    @Bean
    public Guard<String, String> paymentOk() {
      return (context) -> {
        return context.getMessageHeaders().containsKey("payment");
      };
    }

    @Bean
    public Guard<String, String> makeProdPlan() {
      return (context) -> {
        return context.getExtendedState().getVariables().containsKey("makeProdPlan");
      };
    }

    @Bean
    public Guard<String, String> produce() {
      return (context) -> {
        return context.getExtendedState().getVariables().containsKey("produce");
      };
    }
  }
}
