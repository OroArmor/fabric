/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.fabric.mixin.elytra;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import net.fabricmc.fabric.api.item.v1.elytra.FabricElytraExtensions;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Redirect(method = "initAi", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"))
	private Item redirectGetItem(ItemStack stack) {
		return stack.getItem() instanceof FabricElytraExtensions ? Items.ELYTRA : stack.getItem();
	}

	@Redirect(method = "initAi", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ElytraItem;isUsable(Lnet/minecraft/item/ItemStack;)Z"))
	private boolean redirectIsUsable(ItemStack stack) {
		return stack.getItem() instanceof FabricElytraExtensions ? ((FabricElytraExtensions) stack.getItem()).isUsable(stack, (LivingEntity) (Object) this) : ElytraItem.isUsable(stack);
	}
}
