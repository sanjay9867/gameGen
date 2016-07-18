package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;
import com.fancytank.gamegen.programming.data.ValueType;
import com.fancytank.gamegen.programming.data.VariableList;

public class ScriptLoader {
    public static void load(ProgrammingBlockSavedInstance[] data) {
        for (ProgrammingBlockSavedInstance savedBlock : data)
            switch (savedBlock.data.shape) {
                case ACTION_LISTENER:
                    loadListener(savedBlock.data);
                    break;
                case VARIABLE_DECLARATION:
                    loadVariable(savedBlock);
                    break;
            }
    }

    private static void loadVariable(ProgrammingBlockSavedInstance savedBlock) {
        InputFragment valueInput = savedBlock.data.getInputs()[0];
        if (hasValidConnection(valueInput)) {
            String name = savedBlock.data.getValue();
            ValueType type = savedBlock.data.getVariable().valueType;
            String value = valueInput.connectedTo.getValue();
            VariableList.getInstance().put(name, value, type);
        }
    }

    private static void loadListener(BlockData blockData) {
        InputFragment classNameInput = blockData.getInputs()[0];
        InputFragment methodSocketInput = blockData.getInputs()[2];
        createActionListener(classNameInput, methodSocketInput.connectedTo);
    }

    private static void createActionListener(InputFragment classNameInput, BlockData executableBlock) {
        if (hasValidConnection(classNameInput) && executableBlock != null) {
            ExecutableProducer executableProducer = convertToExecutableProducer(executableBlock);
            if (executableProducer.getInstance() != null)
                ActorInitializer.addActionListener(classNameInput.connectedTo.getValue(), executableProducer);
            else
                System.out.println("Class " + classNameInput.connectedTo.getValue() + " have unparseable executable.");
            if (executableBlock.hasDescendant())
                createActionListener(classNameInput, executableBlock.getDescendant());
        }
    }

    private static boolean hasValidConnection(InputFragment inputFragment) {
        return inputFragment != null && inputFragment.connectedTo != null;
    }

    private static ExecutableProducer convertToExecutableProducer(BlockData methodBlock) {
        return new ExecutableProducer(methodBlock, ExecutableProducer.ActionListenerType.ON_PRESS);
    }

}
