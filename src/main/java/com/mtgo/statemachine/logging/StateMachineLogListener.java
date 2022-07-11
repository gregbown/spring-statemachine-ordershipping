package com.mtgo.statemachine.logging;

import java.util.LinkedList;
import java.util.List;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateContext.Stage;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

public class StateMachineLogListener extends StateMachineListenerAdapter<String, String> {

  private final LinkedList<String> messages = new LinkedList<String>();

  public List<String> getMessages() {
    return messages;
  }

  public void resetMessages() {
    messages.clear();
  }

  @Override
  public void stateContext(StateContext<String, String> stateContext) {
    if (stateContext.getStage() == Stage.STATE_ENTRY) {
      messages.addFirst(stateContext.getStateMachine().getId() + " enter " + stateContext.getTarget().getId());
    } else if (stateContext.getStage() == Stage.STATE_EXIT) {
      messages.addFirst(stateContext.getStateMachine().getId() + " exit " + stateContext.getSource().getId());
    }
  }
}
