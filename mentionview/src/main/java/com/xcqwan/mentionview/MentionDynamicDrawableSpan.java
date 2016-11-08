package com.xcqwan.mentionview;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;

/**
 * Created by Thanks on 2016/10/17.
 */

public class MentionDynamicDrawableSpan extends DynamicDrawableSpan {
    private Resources resources;
    private Mention mention;
    private String identify;
    private String drawContent;
    private Bitmap bitmap;

    public MentionDynamicDrawableSpan(Resources resources, Mention mention, String identify, int color, float size) {
        this.resources = resources;
        this.mention = mention;
        this.identify = identify;

        drawContent = identify + mention.relName;

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setTextSize(size);
        Rect rect = new Rect();
        paint.getTextBounds(drawContent, 0, drawContent.length(), rect);

        int width = (int) (paint.measureText(drawContent));
        bitmap = Bitmap.createBitmap(width, rect.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(drawContent, rect.left, rect.height() - rect.bottom, paint);
    }

    @Override
    public Drawable getDrawable() {
        BitmapDrawable drawable = new BitmapDrawable(resources, bitmap);
        drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
        return drawable;
    }

    public int length() {
        return drawContent.length();
    }

    public Mention getMention() {
        return mention;
    }

    public String getMentionReplaceId() {
        return identify + mention.relId;
    }
}
