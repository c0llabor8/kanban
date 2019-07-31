package com.c0llabor8.kanban.util;

public class ColorUtil {

  public static int mixTwoColors(int color1, int color2, float amountRatio) {
    // compute percentage RGBA hexadecimal share in blending
    final byte RED_CHANNEL = 16;
    final byte GREEN_CHANNEL = 8;
    final byte BLUE_CHANNEL = 0;
    final byte ALPHA_CHANNEL = 24;

    final float inverseAmountRatio = 1.0f - amountRatio;

    // bit manipulation
    int r = ((int) (((float) (color2 >> RED_CHANNEL & 0xff) * amountRatio) +
        ((float) (color1 >> RED_CHANNEL & 0xff) * inverseAmountRatio))) & 0xff;
    int g = ((int) (((float) (color2 >> GREEN_CHANNEL & 0xff) * amountRatio) +
        ((float) (color1 >> GREEN_CHANNEL & 0xff) * inverseAmountRatio))) & 0xff;
    int b = ((int) (((float) (color2 & 0xff) * amountRatio) +
        ((float) (color1 & 0xff) * inverseAmountRatio))) & 0xff;
    int a = ((int) (((float) (color2 >> ALPHA_CHANNEL & 0xff) * amountRatio) +
        ((float) (color1 >> ALPHA_CHANNEL & 0xff) * inverseAmountRatio))) & 0xff;

    return a << ALPHA_CHANNEL | r << RED_CHANNEL | g << GREEN_CHANNEL | b << BLUE_CHANNEL;
  }
}
