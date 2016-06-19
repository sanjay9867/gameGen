package com.fancytank.gamegen.programming;

import android.content.Context;

import com.fancytank.gamegen.programming.blocks.BlockActorPattern;

class BlockPatternHolder {
    private BlockActorPattern pattern;
    private SpawnBlockDialog dialogType;

    BlockPatternHolder(BlockActorPattern pattern) {
        this.pattern = pattern;
    }

    BlockPatternHolder(BlockActorPattern pattern, SpawnBlockDialog dialogType) {
        this.pattern = pattern;
        this.dialogType = dialogType;
    }

    public String getName() {
        return pattern.getName();
    }

    public void click(Context context) {
        if (dialogType != null) {
            dialogType.getDialog(context, pattern);
        } else
            pattern.spawn();
    }

}