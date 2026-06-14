package com.thecyborgage.client.screens;

import com.thecyborgage.TCACuriosHelper;
import com.thecyborgage.client.TCAKeybinds;
import com.thecyborgage.init.TCAAttachments;
import com.thecyborgage.init.TCAItems;
import com.thecyborgage.network.packets.ToggleValuePayload;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

public class VisorActionsScreen extends Screen {
  private static final float OUTER_RADIUS = 110f;
  private static final float INNER_RADIUS = 50f;
  private static final float TWO_PI = (float) (2 * Math.PI);

  private static final int COLOR_FILL = 0xCC004400;
  private static final int COLOR_FILL_HOVERED = 0xDD006600;
  private static final int COLOR_BORDER = 0xFF00BB00;
  private static final int COLOR_BORDER_HOVERED = 0xFF44FF44;
  private static final int COLOR_FILL_EMPTY = 0xCC333333;
  private static final int COLOR_BORDER_EMPTY = 0xFF888888;

  private final List<CircleAction> actions;
  private int hoveredSegment = -1;

  public VisorActionsScreen(Player player) {
    super(Component.empty());
    this.actions = buildActions(player);
  }

  private static List<CircleAction> buildActions(Player player) {
    List<CircleAction> list = new ArrayList<>();

    if (TCACuriosHelper.getEntityCurioItem(player, TCAItems.CYBORG_JUMP_LEG.get()).isPresent()) {
      list.add(
          new CircleAction(
              Component.translatable("thecyborgage.toggle_circle.toggle_jump_leg"),
              () -> player.getData(TCAAttachments.CYBORG_JUMP_LEG_TOGGLE_STATE),
              () ->
                  PacketDistributor.sendToServer(
                      new ToggleValuePayload(
                          ToggleValuePayload.ToggleValue.CYBORG_JUMP_LEG,
                          !player.getData(TCAAttachments.CYBORG_JUMP_LEG_TOGGLE_STATE)))));
    }

    return list;
  }

  @Override
  public boolean isPauseScreen() {
    return false;
  }

  @Override
  public void tick() {
    if (!TCAKeybinds.isPhysicallyDown(TCAKeybinds.OPEN_TOGGLE_CIRCLE)) {
      if (hoveredSegment >= 0 && hoveredSegment < actions.size()) {
        actions.get(hoveredSegment).onActivate().run();
      }
      this.onClose();
    }
  }

  @Override
  public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
    int cx = this.width / 2;
    int cy = this.height / 2;

    int n = actions.size();
    hoveredSegment = n == 0 ? -1 : getHoveredSegment(mouseX, mouseY, cx, cy);

    if (n == 0) {
      fillArc(graphics, cx, cy, INNER_RADIUS, OUTER_RADIUS, 0, TWO_PI, COLOR_FILL_EMPTY);
      drawCircleBorder(graphics, cx, cy, OUTER_RADIUS, COLOR_BORDER_EMPTY);
      drawCircleBorder(graphics, cx, cy, INNER_RADIUS, COLOR_BORDER_EMPTY);
      graphics.drawCenteredString(
          this.font,
          Component.translatable("thecyborgage.toggle_circle.no_actions"),
          cx,
          cy - this.font.lineHeight / 2,
          0xAAAAAA);
      return;
    }

    float segAngle = TWO_PI / n;

    for (int i = 0; i < n; i++) {
      float nStart = i * segAngle;
      float nEnd = (i + 1) * segAngle;
      int fillColor = (i == hoveredSegment) ? COLOR_FILL_HOVERED : COLOR_FILL;
      fillArc(graphics, cx, cy, INNER_RADIUS, OUTER_RADIUS, nStart, nEnd, fillColor);
    }

    int borderColor = (hoveredSegment >= 0) ? COLOR_BORDER_HOVERED : COLOR_BORDER;
    drawCircleBorder(graphics, cx, cy, OUTER_RADIUS, borderColor);
    drawCircleBorder(graphics, cx, cy, INNER_RADIUS, borderColor);

    for (int i = 0; i < n; i++) {
      float nStart = i * segAngle;
      float nEnd = (i + 1) * segAngle;
      renderSegmentText(graphics, cx, cy, nStart, nEnd, actions.get(i));
    }
  }

  private static void fillArc(
      GuiGraphics graphics,
      int cx,
      int cy,
      float innerR,
      float outerR,
      float nStart,
      float nEnd,
      int color) {
    for (int dy = -(int) outerR; dy <= (int) outerR; dy++) {
      float dyf = dy;
      float outerX2 = outerR * outerR - dyf * dyf;
      if (outerX2 <= 0) continue;
      int outerX = (int) Math.sqrt(outerX2);

      float innerX2 = innerR * innerR - dyf * dyf;
      int innerX = innerX2 > 0 ? (int) Math.sqrt(innerX2) : 0;

      fillSpanClipped(graphics, cx, cy, dy, -outerX, -innerX, nStart, nEnd, color);
      fillSpanClipped(graphics, cx, cy, dy, innerX, outerX, nStart, nEnd, color);
    }
  }

  private static void fillSpanClipped(
      GuiGraphics graphics,
      int cx,
      int cy,
      int dy,
      int dxMin,
      int dxMax,
      float nStart,
      float nEnd,
      int color) {
    if (dxMax <= dxMin) return;

    int runStart = Integer.MIN_VALUE;
    for (int dx = dxMin; dx <= dxMax; dx++) {
      float na = normalizeAngle((float) Math.atan2(dy, dx));
      boolean in = (na >= nStart && na <= nEnd);

      if (in && runStart == Integer.MIN_VALUE) {
        runStart = dx;
      } else if (!in && runStart != Integer.MIN_VALUE) {
        graphics.fill(cx + runStart, cy + dy, cx + dx, cy + dy + 1, color);
        runStart = Integer.MIN_VALUE;
      }
    }
    if (runStart != Integer.MIN_VALUE) {
      graphics.fill(cx + runStart, cy + dy, cx + dxMax + 1, cy + dy + 1, color);
    }
  }

  private static void drawCircleBorder(
      GuiGraphics graphics, int cx, int cy, float radius, int color) {
    for (int dy = -(int) radius - 2; dy <= (int) radius + 2; dy++) {
      float dyf = dy;
      float outerX2 = (radius + 1) * (radius + 1) - dyf * dyf;
      float innerX2 = (radius - 1) * (radius - 1) - dyf * dyf;
      if (outerX2 <= 0) continue;
      int outerX = (int) Math.sqrt(outerX2);
      int innerX = innerX2 > 0 ? (int) Math.sqrt(innerX2) : 0;
      graphics.fill(cx - outerX, cy + dy, cx - innerX, cy + dy + 1, color);
      graphics.fill(cx + innerX, cy + dy, cx + outerX, cy + dy + 1, color);
    }
  }

  private static float normalizeAngle(float angle) {
    angle += (float) (Math.PI / 2);
    return ((angle % TWO_PI) + TWO_PI) % TWO_PI;
  }

  private void renderSegmentText(
      GuiGraphics graphics, float cx, float cy, float nStart, float nEnd, CircleAction action) {
    float midNorm = (nStart + nEnd) / 2;
    float atan2Angle = midNorm - (float) (Math.PI / 2);
    float midRadius = (INNER_RADIUS + OUTER_RADIUS) / 2;
    int textX = (int) (cx + Math.cos(atan2Angle) * midRadius);
    int textY = (int) (cy + Math.sin(atan2Angle) * midRadius);

    boolean on = action.stateGetter().get();
    int lineHeight = this.font.lineHeight + 1;
    int startY = textY - lineHeight;

    graphics.drawCenteredString(this.font, action.label(), textX, startY, 0xFFFFFF);
    graphics.drawCenteredString(
        this.font,
        Component.translatable(
            on ? "thecyborgage.toggle_circle.on" : "thecyborgage.toggle_circle.off"),
        textX,
        startY + lineHeight,
        on ? 0x55FF55 : 0xFF5555);
  }

  private int getHoveredSegment(int mouseX, int mouseY, int cx, int cy) {
    float dx = mouseX - cx;
    float dy = mouseY - cy;
    float distSq = dx * dx + dy * dy;

    if (distSq < INNER_RADIUS * INNER_RADIUS || distSq > OUTER_RADIUS * OUTER_RADIUS) {
      return -1;
    }

    float angle = normalizeAngle((float) Math.atan2(dy, dx));
    int n = actions.size();
    int segment = (int) (angle / (TWO_PI / n));
    return Math.clamp(segment, 0, n - 1);
  }

  @Override
  public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {}

  private record CircleAction(
      Component label, Supplier<Boolean> stateGetter, Runnable onActivate) {}
}
