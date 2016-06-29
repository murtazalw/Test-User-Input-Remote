package com.example.testuserinput;

import java.text.DateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.testuserinput.MESSAGE";
	private TextView txtDate;
	EditText editText;
	EditText editText2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		String currentDate = DateFormat.getDateInstance().format(new Date());

		txtDate = (TextView) findViewById(R.id.current_date);
		txtDate.setText(currentDate);
	}

	public void submitTeam(View view) {
		Intent intent = new Intent(this, PlayersActivity.class);

		String message;
		String message2;

		editText = (EditText) findViewById(R.id.team_1);
		editText2 = (EditText) findViewById(R.id.team_2);

		message = editText.getText().toString().trim();
		message2 = editText2.getText().toString().trim();
		if (message.length() > 0 && message2.length() > 0) {
			if (!message.equalsIgnoreCase(message2)) {
				intent.putExtra(EXTRA_MESSAGE,
						new String[] { message, message2 });
				startActivity(intent);
			} else
				showPopUp("Warning", "Team Names should not be same",
						new String[] { "OK", "Cancel" });
		} else
			showPopUp("Warning", "You Must enter team name on both fields",
					new String[] { "OK", "Cancel" });
	}

	public void showPopUp(String popupTitle, String popupMessage,
			String[] popOptions) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set title
		alertDialogBuilder.setTitle(popupTitle);

		// set dialog message
		alertDialogBuilder.setMessage(popupMessage);
		alertDialogBuilder.setCancelable(false);

		// set Options
		for (String option : popOptions) {
			if (option.equalsIgnoreCase("OK"))
				alertDialogBuilder.setPositiveButton(option,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						});
			else {
				alertDialogBuilder.setNegativeButton(option,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
			}
		}

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

}
