package com.example.testuserinput;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class ClearableEditText extends RelativeLayout {
	LayoutInflater inflater = null;
	EditText edit_text;
	Button btn_clear;

	int visible = RelativeLayout.VISIBLE;
	int invisible = RelativeLayout.INVISIBLE;

	public ClearableEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initViews();
	}

	public ClearableEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initViews();

	}

	public ClearableEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initViews();
	}

	void initViews() {
		inflater = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.clearable_edit_text, this, true);
		/*
		 * int[] edit_text_Arr = new int[] { R.id.clearable_edit_inputPlayer,
		 * R.id.clearable_edit_inputSearch };
		 */
		// for (int editText : edit_text_Arr) {
		edit_text = (EditText) findViewById(R.id.clearable_edit_inputSearch);
		btn_clear = (Button) findViewById(R.id.clearable_button_clear);
		btn_clear.setVisibility(invisible);
		clearText();
		showHideClearButton();
		// }
	}

	void clearText() {
		btn_clear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				edit_text.setText("");
			}
		});
	}

	void showHideClearButton() {
		edit_text.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (s.length() > 0)
					btn_clear.setVisibility(visible);
				else
					btn_clear.setVisibility(invisible);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
	}

	public Editable getText() {
		Editable text = edit_text.getText();
		return text;
	}
}
