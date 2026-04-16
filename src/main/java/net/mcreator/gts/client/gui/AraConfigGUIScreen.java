package net.mcreator.gts.client.gui;

import net.minecraftforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.gts.world.inventory.AraConfigGUIMenu;
import net.mcreator.gts.procedures.CheckDisableAraVoreProcedure;
import net.mcreator.gts.procedures.CheckDisableAraTailSwipeProcedure;
import net.mcreator.gts.procedures.CheckDisableAraTailSqueezeProcedure;
import net.mcreator.gts.procedures.CheckDisableAraStepProcedure;
import net.mcreator.gts.procedures.CheckDisableAraMiddleTailSlapProcedure;
import net.mcreator.gts.procedures.CheckDisableAraMiddleGrabProcedure;
import net.mcreator.gts.procedures.CheckDisableAraLowGrabProcedure;
import net.mcreator.gts.procedures.CheckDisableAraChompProcedure;
import net.mcreator.gts.procedures.CheckDisableAraBreachProcedure;
import net.mcreator.gts.procedures.CheckDisableAraAboveAttackProcedure;
import net.mcreator.gts.procedures.CheckAraBreachAggressionProcedure;
import net.mcreator.gts.network.GtsNetwork;
import net.mcreator.gts.network.AraConfigGUIButtonMessage;
import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class AraConfigGUIScreen extends AbstractContainerScreen<AraConfigGUIMenu> {
	private final static HashMap<String, Object> guistate = AraConfigGUIMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	Button button_main_menu;
	Button button_stomp;
	Button button_lowertailswipe;
	Button button_lowertailsqueeze;
	Button button_lowgrab;
	Button button_middletailslap;
	Button button_middlegrab;
	Button button_chomp;
	Button button_aboveattack;
	Button button_breach;
	Button button_eat;
	Button button_empty;
	Button button_empty1;

	public AraConfigGUIScreen(AraConfigGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 400;
		this.imageHeight = 200;
	}

	private static final ResourceLocation texture = new ResourceLocation("gts", "textures/screens/ara_config_gui.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.gts.ara_config_gui.label_aras_attacks"), 6, 22, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableAraStepProcedure.execute(world), 59, 39, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableAraTailSwipeProcedure.execute(world), 202, 39, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableAraTailSqueezeProcedure.execute(world), 353, 39, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableAraLowGrabProcedure.execute(world), 69, 63, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableAraMiddleTailSlapProcedure.execute(world), 211, 63, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableAraMiddleGrabProcedure.execute(world), 332, 63, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableAraChompProcedure.execute(world), 59, 86, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableAraAboveAttackProcedure.execute(world), 185, 86, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableAraBreachProcedure.execute(world), 285, 86, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableAraVoreProcedure.execute(world), 48, 108, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.gts.ara_config_gui.label_breach_aggression"), 7, 127, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckAraBreachAggressionProcedure.execute(world), 104, 127, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		button_main_menu = Button.builder(Component.translatable("gui.gts.ara_config_gui.button_main_menu"), e -> {
			if (true) {
				GtsNetwork.CHANNEL.sendToServer(new AraConfigGUIButtonMessage(0, x, y, z));
				AraConfigGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 317, this.topPos + 10, 72, 20).build();
		guistate.put("button:button_main_menu", button_main_menu);
		this.addRenderableWidget(button_main_menu);
		button_stomp = Button.builder(Component.translatable("gui.gts.ara_config_gui.button_stomp"), e -> {
			if (true) {
				GtsNetwork.CHANNEL.sendToServer(new AraConfigGUIButtonMessage(1, x, y, z));
				AraConfigGUIButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 7, this.topPos + 35, 51, 20).build();
		guistate.put("button:button_stomp", button_stomp);
		this.addRenderableWidget(button_stomp);
		button_lowertailswipe = Button.builder(Component.translatable("gui.gts.ara_config_gui.button_lowertailswipe"), e -> {
			if (true) {
				GtsNetwork.CHANNEL.sendToServer(new AraConfigGUIButtonMessage(2, x, y, z));
				AraConfigGUIButtonMessage.handleButtonAction(entity, 2, x, y, z);
			}
		}).bounds(this.leftPos + 102, this.topPos + 35, 98, 20).build();
		guistate.put("button:button_lowertailswipe", button_lowertailswipe);
		this.addRenderableWidget(button_lowertailswipe);
		button_lowertailsqueeze = Button.builder(Component.translatable("gui.gts.ara_config_gui.button_lowertailsqueeze"), e -> {
			if (true) {
				GtsNetwork.CHANNEL.sendToServer(new AraConfigGUIButtonMessage(3, x, y, z));
				AraConfigGUIButtonMessage.handleButtonAction(entity, 3, x, y, z);
			}
		}).bounds(this.leftPos + 243, this.topPos + 35, 109, 20).build();
		guistate.put("button:button_lowertailsqueeze", button_lowertailsqueeze);
		this.addRenderableWidget(button_lowertailsqueeze);
		button_lowgrab = Button.builder(Component.translatable("gui.gts.ara_config_gui.button_lowgrab"), e -> {
			if (true) {
				GtsNetwork.CHANNEL.sendToServer(new AraConfigGUIButtonMessage(4, x, y, z));
				AraConfigGUIButtonMessage.handleButtonAction(entity, 4, x, y, z);
			}
		}).bounds(this.leftPos + 7, this.topPos + 58, 61, 20).build();
		guistate.put("button:button_lowgrab", button_lowgrab);
		this.addRenderableWidget(button_lowgrab);
		button_middletailslap = Button.builder(Component.translatable("gui.gts.ara_config_gui.button_middletailslap"), e -> {
			if (true) {
				GtsNetwork.CHANNEL.sendToServer(new AraConfigGUIButtonMessage(5, x, y, z));
				AraConfigGUIButtonMessage.handleButtonAction(entity, 5, x, y, z);
			}
		}).bounds(this.leftPos + 111, this.topPos + 58, 98, 20).build();
		guistate.put("button:button_middletailslap", button_middletailslap);
		this.addRenderableWidget(button_middletailslap);
		button_middlegrab = Button.builder(Component.translatable("gui.gts.ara_config_gui.button_middlegrab"), e -> {
			if (true) {
				GtsNetwork.CHANNEL.sendToServer(new AraConfigGUIButtonMessage(6, x, y, z));
				AraConfigGUIButtonMessage.handleButtonAction(entity, 6, x, y, z);
			}
		}).bounds(this.leftPos + 254, this.topPos + 58, 77, 20).build();
		guistate.put("button:button_middlegrab", button_middlegrab);
		this.addRenderableWidget(button_middlegrab);
		button_chomp = Button.builder(Component.translatable("gui.gts.ara_config_gui.button_chomp"), e -> {
			if (true) {
				GtsNetwork.CHANNEL.sendToServer(new AraConfigGUIButtonMessage(7, x, y, z));
				AraConfigGUIButtonMessage.handleButtonAction(entity, 7, x, y, z);
			}
		}).bounds(this.leftPos + 7, this.topPos + 81, 51, 20).build();
		guistate.put("button:button_chomp", button_chomp);
		this.addRenderableWidget(button_chomp);
		button_aboveattack = Button.builder(Component.translatable("gui.gts.ara_config_gui.button_aboveattack"), e -> {
			if (true) {
				GtsNetwork.CHANNEL.sendToServer(new AraConfigGUIButtonMessage(8, x, y, z));
				AraConfigGUIButtonMessage.handleButtonAction(entity, 8, x, y, z);
			}
		}).bounds(this.leftPos + 102, this.topPos + 81, 82, 20).build();
		guistate.put("button:button_aboveattack", button_aboveattack);
		this.addRenderableWidget(button_aboveattack);
		button_breach = Button.builder(Component.translatable("gui.gts.ara_config_gui.button_breach"), e -> {
			if (true) {
				GtsNetwork.CHANNEL.sendToServer(new AraConfigGUIButtonMessage(9, x, y, z));
				AraConfigGUIButtonMessage.handleButtonAction(entity, 9, x, y, z);
			}
		}).bounds(this.leftPos + 228, this.topPos + 81, 56, 20).build();
		guistate.put("button:button_breach", button_breach);
		this.addRenderableWidget(button_breach);
		button_eat = Button.builder(Component.translatable("gui.gts.ara_config_gui.button_eat"), e -> {
			if (true) {
				GtsNetwork.CHANNEL.sendToServer(new AraConfigGUIButtonMessage(10, x, y, z));
				AraConfigGUIButtonMessage.handleButtonAction(entity, 10, x, y, z);
			}
		}).bounds(this.leftPos + 7, this.topPos + 103, 40, 20).build();
		guistate.put("button:button_eat", button_eat);
		this.addRenderableWidget(button_eat);
		button_empty = Button.builder(Component.translatable("gui.gts.ara_config_gui.button_empty"), e -> {
			if (true) {
				GtsNetwork.CHANNEL.sendToServer(new AraConfigGUIButtonMessage(11, x, y, z));
				AraConfigGUIButtonMessage.handleButtonAction(entity, 11, x, y, z);
			}
		}).bounds(this.leftPos + 7, this.topPos + 142, 30, 20).build();
		guistate.put("button:button_empty", button_empty);
		this.addRenderableWidget(button_empty);
		button_empty1 = Button.builder(Component.translatable("gui.gts.ara_config_gui.button_empty1"), e -> {
			if (true) {
				GtsNetwork.CHANNEL.sendToServer(new AraConfigGUIButtonMessage(12, x, y, z));
				AraConfigGUIButtonMessage.handleButtonAction(entity, 12, x, y, z);
			}
		}).bounds(this.leftPos + 42, this.topPos + 142, 30, 20).build();
		guistate.put("button:button_empty1", button_empty1);
		this.addRenderableWidget(button_empty1);
	}
}
