package com.xcqwan.mentionview;

import android.content.Context;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Thanks on 16/10/10.
 */

public class MentionEditText extends EditText {
    private static final String IDENTIFY = "@";

    private String identify = IDENTIFY;
    private int mentionTextColor = Color.RED;
    private OnMentionListener onMentionListener;

    public MentionEditText(Context context) {
        super(context);
        initView();
    }

    public MentionEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MentionEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setFilters(new InputFilter[] {new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.toString().equals(identify)) {
                    if (onMentionListener != null) {
                        onMentionListener.onMention(start);
                    }
                }
                return source;
            }
        }});
        setClickable(false);
        setLongClickable(false);
    }

    public void appendMention(Mention mention) {
        if (mention == null) {
            return;
        }
        if (getText().toString().endsWith(identify)) {
            getText().delete(getText().length() - identify.length(), getText().length());
        }

        String mentionText = identify + mention.relName;
        SpannableString spannableString = new SpannableString(mentionText);
        MentionDynamicDrawableSpan mdds = new MentionDynamicDrawableSpan(getResources(), mention, identify, mentionTextColor, getTextSize());
        spannableString.setSpan(mdds, 0, mdds.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        super.append(spannableString);
    }

    public String getMentionText() {
        StringBuilder sb = new StringBuilder();
        String text = getText().toString();

        MentionDynamicDrawableSpan[] spans = getText().getSpans(0, text.length(), MentionDynamicDrawableSpan.class);
        int index = 0;
        for (MentionDynamicDrawableSpan span : spans) {
            int start = getText().getSpanStart(span);
            int end = getText().getSpanEnd(span);
            sb.append(text.substring(index, start));
            sb.append(span.getMentionReplaceId());
            index = end;
        }
        if (index < text.length()) {
            sb.append(text.substring(index));
        }

        return sb.toString();
    }

    public ArrayList<Mention> getMentionList() {
        ArrayList<Mention> mentionList = new ArrayList<>();

        String text = getText().toString();
        MentionDynamicDrawableSpan[] spans = getText().getSpans(0, text.length(), MentionDynamicDrawableSpan.class);
        for (MentionDynamicDrawableSpan span : spans) {
            mentionList.add(span.getMention());
        }
        return mentionList;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public int getMentionTextColor() {
        return mentionTextColor;
    }

    public void setMentionTextColor(int mentionTextColor) {
        this.mentionTextColor = mentionTextColor;
    }

    public void setOnMentionListener(OnMentionListener onMentionListener) {
        this.onMentionListener = onMentionListener;
    }

    public interface OnMentionListener {
        void onMention(int start);
    }
}
