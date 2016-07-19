package com.fancytank.gamegen.programming.data;

import com.fancytank.gamegen.programming.blocks.ProgrammingBlock;
import com.fancytank.gamegen.programming.looks.CoreBlock;

import java.io.Serializable;
import java.util.ArrayList;

public class BlockData implements Serializable {
    private static final long serialVersionUID = 1233613063064496932L;
    public BlockShape shape;
    BlockData parent;
    BlockData descendant;
    InputFragment[] inputs;
    transient CoreBlock coreBlock;
    Variable variable;

    public BlockData(InputFragment[] inputs, BlockShape shape) {
        this.shape = shape;
        this.inputs = inputs;
    }

    public BlockData getParent() {
        return parent;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public void setParent(BlockData parent) {
        this.parent = parent;
        this.parent.descendant = this;
    }

    public void removeParent() {
        if (parent.hasDescendant())
            parent.descendant = null;
        parent = null;
    }

    public BlockData getDescendant() {
        return descendant;
    }

    public boolean hasDescendant() {
        return descendant != null;
    }

    public CoreBlock getCoreBlock() {
        return coreBlock;
    }

    public void setCoreBlock(CoreBlock coreBlock) {
        this.coreBlock = coreBlock;
    }

    public InputFragment[] getInputs() {
        if (inputs != null)
            return inputs;
        else return new InputFragment[0];
    }

    public String getDebugLog(String spacing) {
        String output = spacing + "BlockData ";
        if (coreBlock != null)
            output += "ID: " + coreBlock.getProgrammingBlock().getName() + "\n";
        else
            output += "(virtual)\n";
        for (InputFragment inputFragment : inputs)
            output += inputFragment.getDebugLog(spacing + "  ");
        if (hasDescendant())
            output += spacing + "next:\n" + descendant.getDebugLog(spacing + "  ");
        return output;
    }

    @Override
    public String toString() {
        return getDebugLog("");
    }

    public static ArrayList<BlockData> getBlockDataList() {
        ArrayList<BlockData> output = new ArrayList<BlockData>();
        for (ProgrammingBlock programmingBlock : ProgrammingBlock.getBlockList())
            output.add(programmingBlock.coreBlock.data);
        return output;
    }

    public boolean hasValue() {
        return variable != null;
    }

    public Variable getVariable() {
        return variable;
    }

    public String getValue() {
        return variable.getValue();
    }

    public BlockData setVariable(Variable value) {
        this.variable = value;
        return this;
    }

}
