package com.xcqwan.mentionview;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;

/**
 * Created by Thanks on 16/10/9.
 */

public abstract class TouchableSpan extends ClickableSpan {
    private boolean isPressed;
    private int normalTextColor;
    private int pressedTextColor;

    protected TouchableSpan(int normalTextColor, int pressedTextColor) {
        this.normalTextColor = normalTextColor;
        this.pressedTextColor = pressedTextColor;
    }

    void setPressed(boolean isSelected) {
        isPressed = isSelected;
    }

    @Override
    public void updateDrawState(TextPaint textPaint) {
        super.updateDrawState(textPaint);
        textPaint.setColor(isPressed ? pressedTextColor : normalTextColor);
        textPaint.bgColor = Color.TRANSPARENT;
        textPaint.setUnderlineText(false);
    }
}
