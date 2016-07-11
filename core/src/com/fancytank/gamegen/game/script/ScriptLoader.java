package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.BlockShape;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;

public class ScriptLoader {
    public static void load(ProgrammingBlockSavedInstance[] data) {
        System.out.println("loadBlocks debug");
        for (ProgrammingBlockSavedInstance a : data)
            System.out.println(a);
        loadListeners(data);
    }

    private static void loadListeners(ProgrammingBlockSavedInstance[] data) {
        for (ProgrammingBlockSavedInstance savedBlock : data)
            if (savedBlock.data.shape == BlockShape.ACTION_LISTENER) {
                //todo pierwszy input - najpewniej bedzie wyznacznikiem tego, co robi metoda
                InputFragment classNameInput = savedBlock.data.getInputs()[0];
                InputFragment methodSocketInput = savedBlock.data.getInputs()[2]; // todo or not todo?
                if (hasValidConnection(classNameInput) && hasValidConnection(methodSocketInput)) {
                    ExecutableProducer executableProducer = convertToExecutableProducer(methodSocketInput.connectedTo);
                    if (executableProducer.getInstance() != null)
                        ActorInitializer.addActionListener(classNameInput.connectedTo.getValue(), executableProducer);
                    else
                        System.out.println("Class " + classNameInput.connectedTo.getValue() + " have unparseable executable.");
                }
            }
    }

    private static boolean hasValidConnection(InputFragment inputFragment) {
        return inputFragment != null && inputFragment.connectedTo != null;//&& inputFragment.connectedTo.hasValue();
    }

    private static ExecutableProducer convertToExecutableProducer(BlockData methodBlock) {
        return new ExecutableProducer(methodBlock, ExecutableProducer.ActionListenerType.ON_PRESS);
    }

}
