package com.xcqwan.mentionview;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Thanks on 16/10/9.
 */

public class MentionTextView extends TextView {
    private static final String IDENTIFY = "@";
    private static final String DEFAULT_PATTERN = "[\\p{L}0-9_]*";

    private String identify = IDENTIFY;
    private String pattern = DEFAULT_PATTERN;
    private int mentionTextColor = Color.RED;
    private int mentionSelectedColor = Color.MAGENTA;
    private HashMap<String, Mention> dataMap;
    private MentionOnClickListener mentionOnClickListener;

    public MentionTextView(Context context) {
        super(context);
    }

    public MentionTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MentionTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setHighlightColor(int color) {
        super.setHighlightColor(Color.TRANSPARENT);
    }

    public void setText(String text) {
        spannaleText(text);
    }

    public void setMentionText(String text) {
        setMovementMethod(new MentionLinkMovementMethod());
        spannaleText(text);
    }

    private void spannaleText(String text) {
        super.setText("");

        Pattern pattern = Pattern.compile(getRegexPattern());
        Matcher matcher = pattern.matcher(text);

        int index = 0;
        while (matcher.find()) {
            String matchedText = matcher.group();
            int startIndex = text.indexOf(matchedText, index);
            if (startIndex > index) {
                super.append(text.substring(index, startIndex));
            }
            index = startIndex + matchedText.length();
            if (matchedText.length() == identify.length()) {
                super.append(matchedText);
                continue;
            }
            String mentionId = matchedText.split(identify)[1];
            if (dataMap == null) {
                final Mention mention = new Mention(mentionId, mentionId);
                final String showText = matchedText;
                SpannableString spannableString = new SpannableString(showText);
                TouchableSpan span = new TouchableSpan(mentionTextColor, mentionSelectedColor) {
                    @Override
                    public void onClick(View view) {
                        if (mentionOnClickListener != null) {
                            mentionOnClickListener.onMentionTextClick(showText, mention);
                        }
                    }
                };

                spannableString.setSpan(span, 0, showText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                super.append(spannableString);
            } else if (dataMap.containsKey(mentionId)) {
                final Mention mention = dataMap.get(mentionId);
                final String showText = matchedText.replace(mentionId, mention.relName);
                SpannableString spannableString = new SpannableString(showText);
                TouchableSpan span = new TouchableSpan(mentionTextColor, mentionSelectedColor) {
                    @Override
                    public void onClick(View view) {
                        if (mentionOnClickListener != null) {
                            mentionOnClickListener.onMentionTextClick(showText, mention);
                        }
                    }
                };

                spannableString.setSpan(span, 0, showText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                super.append(spannableString);
            } else {
                super.append(matchedText);
            }
        }
        if (text.length() > index) {
            super.append(text.substring(index));
        }
    }

    private String getRegexPattern() {
        return identify + pattern;
    }

    public void setMentionOnClickListener(MentionOnClickListener mentionOnClickListener) {
        this.mentionOnClickListener = mentionOnClickListener;
    }

    public String getIdentify() {
        return identify;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setPatternWithIdLen(int len) {
        if (len <= 0) {
            this.pattern = DEFAULT_PATTERN;
        } else {
            this.pattern = "[\\p{L}0-9]{"+ len + "}";
        }
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public void setMentionTextColor(int mentionTextColor) {
        this.mentionTextColor = mentionTextColor;
    }

    public void setMentionSelectedColor(int mentionSelectedColor) {
        this.mentionSelectedColor = mentionSelectedColor;
    }

    public void setMentionData(ArrayList<Mention> mentionData) {
        if (mentionData != null) {
            if (dataMap == null) {
                dataMap = new HashMap<>();
            } else {
                dataMap.clear();
            }
            for (Mention mention : mentionData) {
                dataMap.put(mention.relId, mention);
            }
        } else {
            dataMap = null;
        }
    }
}
