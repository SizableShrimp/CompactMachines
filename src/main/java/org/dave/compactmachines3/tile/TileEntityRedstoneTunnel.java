package org.dave.compactmachines3.tile;

import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import org.dave.compactmachines3.utility.DimensionBlockPos;
import org.dave.compactmachines3.world.WorldSavedDataMachines;
import org.dave.compactmachines3.world.data.RedstoneTunnelData;
import org.dave.compactmachines3.world.tools.DimensionTools;
import org.dave.compactmachines3.world.tools.StructureTools;

import java.util.Map;

public class TileEntityRedstoneTunnel extends BaseTileEntityTunnel {

    public int getRedstonePowerInput(EnumFacing facing) {
        int id = StructureTools.getIdForPos(this.getPos());

        WorldSavedDataMachines wsd = WorldSavedDataMachines.getInstance();
        if (wsd == null) {
            return 0;
        }

        Map<Integer, DimensionBlockPos> machinePositions = wsd.machinePositions;
        if (machinePositions == null) {
            return 0;
        }

        DimensionBlockPos dimpos = machinePositions.get(id);
        if (dimpos == null) {
            return 0;
        }

        Map<Integer, Map<EnumFacing, RedstoneTunnelData>> redstoneTunnels = wsd.redstoneTunnels;
        if (redstoneTunnels == null) {
            return 0;
        }

        Map<EnumFacing, RedstoneTunnelData> tunnelMapping = redstoneTunnels.get(id);
        if(tunnelMapping == null) {
            return 0;
        }

        RedstoneTunnelData tunnelData = tunnelMapping.get(facing);
        if(tunnelData == null) {
            return 0;
        }

        if(tunnelData.isOutput) {
            return 0;
        }

        WorldServer realWorld = DimensionTools.getWorldServerForDimension(dimpos.getDimension());
        if(realWorld == null || !(realWorld.getTileEntity(dimpos.getBlockPos()) instanceof TileEntityMachine)) {
            return 0;
        }

        EnumFacing machineSide = this.getMachineSide();
        BlockPos outsetPos = dimpos.getBlockPos().offset(machineSide);

        IBlockState insetBlockState = realWorld.getBlockState(outsetPos);

        int power = 0;
        if(insetBlockState.getBlock() instanceof BlockRedstoneWire) {
            power = insetBlockState.getValue(BlockRedstoneWire.POWER);
        } else {
            power = realWorld.getRedstonePower(outsetPos, machineSide);
        }

        return power;
    }
}
