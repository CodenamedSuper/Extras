package codenamed.extras.item.custom;

import codenamed.extras.Extras;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

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
        if (entity instanceof PlayerEntity player && (player.getStackInHand(Hand.MAIN_HAND).getItem() == this || player.getStackInHand(Hand.OFF_HAND).getItem() == this)) {
            if (this.timer >=OFTEN) {
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
                state = 3;
            }
        }

        this.timer++;

        checkTotemState();
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    public  void  checkTotemState() {
        if (this.state == 0) {
            
        }
        if (this.state == 1) {

        }
        if (this.state == 2) {

        }
        if (this.state == 3) {

        }
    }


}
