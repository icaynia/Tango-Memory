package com.icaynia.tangomemory;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.icaynia.tangomemory.Data.wordManager;
import com.icaynia.tangomemory.Models.word;

public class infActivity extends AppCompatActivity {
    private TextView hiraganavu;
    private TextView wordvu;
    private TextView koreanvu;
    private TextView count;
    private TextView editwordVu;
    private View dialogV;
    private int no;
    private word mword;

    private Toolbar toolbar;

    private wordManager mWordManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf); // 오류 안 뜨는 것 같은데
        this.init();
        Intent intent = getIntent();
        no = intent.getIntExtra("no", 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (no != 0) {
            mword = mWordManager.getWord(no);

            hiraganavu.setText(mword.hiragana);
            koreanvu.setText(mword.korean);
            wordvu.setText(mword.word);
            count.setText(mword.passcount+"/"+mword.showcount+" %");
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_wordinf, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_edit:
                onEditwordDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void init() {
        hiraganavu = (TextView) findViewById(R.id.hiraganavu);
        wordvu = (TextView) findViewById(R.id.wordvu);
        koreanvu = (TextView) findViewById(R.id.koreanvu);
        count = (TextView) findViewById(R.id.count);

        mWordManager = new wordManager(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setActionbar();
    }


    private void setActionbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    @Override
    public void finish() {
        super.finish();
    }

    public void onEditwordDialog() {
        dialogV = getLayoutInflater().inflate(R.layout.dialog_addword, null);
        final AlertDialog.Builder   builder     = new AlertDialog.Builder(this);     // 여기서 this는 Activity의 this

        final EditText et_word = (EditText) dialogV.findViewById(R.id.et_word);
        et_word.setText(mword.word);
        final EditText et_hiragana = (EditText) dialogV.findViewById(R.id.et_hiragana);
        et_hiragana.setText(mword.hiragana);
        final EditText et_korean = (EditText) dialogV.findViewById(R.id.et_korean);
        et_korean.setText(mword.korean);
        final TextView warning = (TextView) dialogV.findViewById(R.id.warning);

        builder.setTitle("단어 편집하기");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!et_word.getText().toString().isEmpty()) {
                    mWordManager.updateWord(mword.id, et_word.getText().toString(), et_hiragana.getText().toString(), et_korean.getText().toString());
                    dialog.dismiss();
                }
            }
        });

        builder.setCancelable(false);
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //((MainActivity)getContext()).makeToast("Scene 작성을 취소하였습니다.");
                dialog.dismiss();
            }
        });

        builder.setView(dialogV);
        //데이터 관련


        final AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();    // 알림창 띄우기

    }


}
