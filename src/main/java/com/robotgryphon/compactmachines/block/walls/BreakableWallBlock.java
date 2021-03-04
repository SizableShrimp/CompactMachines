package com.robotgryphon.compactmachines.block.walls;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BreakableWallBlock extends WallBlock {
    public BreakableWallBlock(Properties props) {
        super(props);
    }

    @Override
    public boolean isBlockProtected(BlockState state, IBlockReader world, BlockPos pos) {
        return false;
    }
}
