package com.fancytank.gamegen.game;

public class Constant {
    public static int BLOCK_SIZE;
    public static int MAP_PADDING;

    private static int BLOCKS_IN_ROW = 20;
    private static int BLOCK_SCALE;

    public static void setUpBlockConstants(int gameWidth) {
        BLOCK_SCALE = (gameWidth / BLOCKS_IN_ROW) ;
        BLOCK_SIZE = BLOCK_SCALE;
        MAP_PADDING = (gameWidth - BLOCKS_IN_ROW * BLOCK_SIZE) / 2;
    }
}