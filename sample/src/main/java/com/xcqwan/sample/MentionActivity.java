package com.xcqwan.sample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.xcqwan.mentionview.Mention;
import com.xcqwan.mentionview.MentionEditText;
import com.xcqwan.mentionview.MentionOnClickListener;
import com.xcqwan.mentionview.MentionTextView;

import java.util.ArrayList;

/**
 * Created by Thanks on 2016/11/8.
 */

public class MentionActivity extends Activity implements MentionOnClickListener {
    private MentionEditText met_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mention);

        MentionTextView mtv_1 = (MentionTextView) findViewById(R.id.mtv_1);
        MentionTextView mtv_2 = (MentionTextView) findViewById(R.id.mtv_2);
        MentionTextView mtv_3 = (MentionTextView) findViewById(R.id.mtv_3);
        MentionTextView mtv_4 = (MentionTextView) findViewById(R.id.mtv_4);
        met_content = (MentionEditText) findViewById(R.id.met_content);

        mtv_1.setText("normal text view");

        mtv_2.setText("text with @name");

        mtv_3.setMentionText("text with @click");
        mtv_3.setMentionOnClickListener(this);

        ArrayList<Mention> mentions = new ArrayList<>();
        mentions.add(new Mention("123", "replace name"));
        mtv_4.setMentionData(mentions);
        mtv_4.setPatternWithIdLen(3);
        mtv_4.setMentionTextColor(Color.BLUE);
        mtv_4.setMentionSelectedColor(Color.CYAN);
        mtv_4.setMentionText("text with @123");
        mtv_4.setMentionOnClickListener(this);

        met_content.setOnMentionListener(new MentionEditText.OnMentionListener() {
            @Override
            public void onMention(int start) {
                Intent intent = new Intent(MentionActivity.this, MentionListActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public void onMentionTextClick(String matchedText, Mention mention) {
        Toast.makeText(MentionActivity.this, mention.relName, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    Mention mention = data.getParcelableExtra("mention");
                    met_content.appendMention(mention);
                    met_content.append(" ");
                    met_content.setSelection(met_content.getText().length());
                    break;
            }
        }
    }
}
