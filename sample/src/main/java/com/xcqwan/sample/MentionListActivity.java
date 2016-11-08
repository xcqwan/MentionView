package com.xcqwan.sample;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.xcqwan.mentionview.Mention;

/**
 * Created by Thanks on 2016/11/8.
 */

public class MentionListActivity extends ListActivity {
    private MentionAdapter mentionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mentionAdapter = new MentionAdapter();
        mentionAdapter.addData(new Mention("123", "test"));
        mentionAdapter.addData(new Mention("321", "你好 再见"));
        mentionAdapter.addData(new Mention("222", "hello world!"));
        mentionAdapter.addData(new Mention("133", "trump"));
        getListView().setAdapter(mentionAdapter);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("mention", mentionAdapter.getItem(position));
                setResult(RESULT_OK, intent);
                onBackPressed();
            }
        });
    }
}
