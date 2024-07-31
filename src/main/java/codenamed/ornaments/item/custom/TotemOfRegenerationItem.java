package codenamed.ornaments.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class TotemOfRegenerationItem extends Item {
    public TotemOfRegenerationItem(Settings settings) {
        super(settings);
    }


    private  int state = 0;

    public static final int OFTEN = 75;
    int timer = 0;
    int timesHealed;

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player && (player.getStackInHand(Hand.MAIN_HAND).getItem() == this)) {
            if (this.timer >=OFTEN) {
                player.heal(1);
                timesHealed++;
                this.timer = 0;

            }
            if (timesHealed == 5) {
                state = 1;
            }
            if (timesHealed == 10) {
                state = 2;
            }
            if (timesHealed == 15) {
                destroyTotem(player, Hand.MAIN_HAND, world);
            }

        }
        if (entity instanceof PlayerEntity player && (player.getStackInHand(Hand.OFF_HAND).getItem() == this)) {
            if (this.timer >=OFTEN && player.getHealth() < player.getMaxHealth()) {
                player.heal(1);
                timesHealed++;
                this.timer = 0;

            }
            if (timesHealed == 25) {
                state = 1;


            }
            if (timesHealed == 50) {
                state = 2;

            }
            if (timesHealed == 75) {
                destroyTotem(player, Hand.OFF_HAND, world);

            }

        }

        this.timer++;

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    public void  destroyTotem(PlayerEntity player, Hand hand, World world) {
        player.getStackInHand(hand).decrement(1);
        world.playSound((PlayerEntity)null, player.getBlockPos(), SoundEvents.ITEM_TOTEM_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);

        player.emitGameEvent(GameEvent.ITEM_INTERACT_FINISH);


    }

    public  int getTotemState() {
        return this.state;
    }


}
