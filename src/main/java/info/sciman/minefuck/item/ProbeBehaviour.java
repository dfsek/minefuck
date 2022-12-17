package info.sciman.minefuck.item;

import info.sciman.minefuck.BFSession;
import info.sciman.minefuck.MinefuckMod;
import info.sciman.minefuck.block.InterpreterBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ProbeBehaviour {
    Text getProbeInfo(World world, BlockPos pos, BlockState state);

    static void registerDefaults() {
        // Redstone wire
        RedstoneProbeItem.registerBehavior(Blocks.REDSTONE_WIRE,(world,pos,state) -> {
            int pwr = state.get(RedstoneWireBlock.POWER);
            return Text.translatable("probe.signal_power",pwr);
        });

        // Brainfuck interpreter
        RedstoneProbeItem.registerBehavior(MinefuckMod.INTERPRETER_BLOCK,(world, pos, state) -> {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof InterpreterBlockEntity) {
                InterpreterBlockEntity interpreter = (InterpreterBlockEntity) be;
                BFSession bf = interpreter.getBf();
                if (bf.checkError()) {
                    return Text.translatable("probe.interpreter_bracket_error", bf.getError());
                }else{
                    return Text.translatable("probe.interpreter_debug", bf.getPC(), bf.getPointer(), bf.getTapeValue(bf.getPointer()));
                }
            }else{
                return Text.literal("???");
            }
        });
    }
}
