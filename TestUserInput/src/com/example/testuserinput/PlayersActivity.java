package com.example.testuserinput;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class PlayersActivity extends Activity implements OnClickListener {

	private Button btnAdd;
	private EditText editText;
	private EditText inputSearch;
	private ListView listView;
	ArrayList<String> list = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	int playerNo = 0;
	int i = 0;
	String[] players = null;
	long lastClickTime = 0;
	private static final long DOUBLE_CLICK_TIME_DELTA = 300;
	TreeMap<Integer, String> teamMap = new TreeMap<Integer, String>();
	public View row;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get the message from the intent
		Intent intent = getIntent();
		String[] teamArr = intent
				.getStringArrayExtra(MainActivity.EXTRA_MESSAGE);

		// Set the text view as the activity layout
		setContentView(R.layout.team_players);

		addTeam(teamArr);

	}

	public void addTeam(String[] teamArr) {
		Spinner spinner = (Spinner) findViewById(R.id.teams_spinner);
		List<String> list = new ArrayList<String>();
		for (String team : teamArr)
			list.add(team);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {

				Toast.makeText(getApplicationContext(),
						parent.getItemAtPosition(pos).toString(),
						Toast.LENGTH_LONG).show();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void test(String team) {

	}

	public void addPlayers(String team, int listViewInt, int i) {
		editText = (EditText) findViewById(R.id.edit_text_inputPlayer);
		btnAdd = (Button) findViewById(R.id.button_add_player);
		btnAdd.setOnClickListener(this);

		adapter = new ArrayAdapter<String>(this, i, list);
		listView = (ListView) findViewById(listViewInt);

		listView.setAdapter(adapter);

		adapter.notifyDataSetChanged();

		// inputSearch = (EditText)
		// findViewById(R.id.clearable_edit_inputSearch);
		// inputSearch.setVisibility(LinearLayout.INVISIBLE);

		/**
		 * Enabling Search Filter
		 * */

		// enableSearchFilter(inputSearch);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int playerPosition, long id) {

				long clickTime = System.currentTimeMillis();
				if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
					updatePlayer(playerPosition);
				} else {
					Toast.makeText(getApplicationContext(),
							"Double Click To Update The Player",
							Toast.LENGTH_LONG).show();
				}
				lastClickTime = clickTime;

			}

		});
	}

	public void onClick(View v) {
		String playerName = editText.getText().toString().trim();

		if (playerName.length() > 0) {
			playerNo++;
			if (playerNo < 13) {

				if (playerNo == 12)
					adapter.add(playerNo + "th Man: " + playerName);
				else
					adapter.add(playerName);

				adapter.notifyDataSetChanged();

				if (playerNo == 11)
					showPopUp("Alert",
							"11 Players Complete. Please add 12th Man",
							new String[] { "OK" });

				editText.setText("");
			}
		} else
			showPopUp("Warning", "You Must enter player name", new String[] {
					"OK", "Cancel" });

		if (playerNo == 12) {
			showPopUp("Alert", "Team Complete", new String[] { "OK" });
			editText.setVisibility(LinearLayout.GONE);
			btnAdd.setVisibility(LinearLayout.GONE);
			// inputSearch.setVisibility(LinearLayout.VISIBLE);
		}

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
								// if this button is clicked, close
								// current activity
								dialog.dismiss();
								editText.setText("");

							}
						});
			else {
				alertDialogBuilder.setNegativeButton(option,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, just close
								// the dialog box and do nothing
								dialog.cancel();
								editText.setText("");
							}
						});
			}
		}

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	private void updatePlayer(final int itemIndex) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final EditText input = new EditText(this);
		// ListView Clicked item value
		String playerName = (String) listView.getItemAtPosition(itemIndex);
		int alignCursorRight = playerName.length();

		input.setText(playerName);

		input.setSelection(alignCursorRight);

		alert.setTitle("Update Player");

		alert.setView(input);

		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// if this button is clicked, close
				// current activity
				dialog.dismiss();
				adapter.remove(adapter.getItem(itemIndex));
				adapter.insert(input.getText().toString(), itemIndex);
				adapter.notifyDataSetChanged();
				editText.setText("");
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
						editText.setText("");
					}
				});

		alert.show();

	}

	public void enableSearchFilter(EditText searchQuery) {
		searchQuery.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// When user changed the Text
				PlayersActivity.this.adapter.getFilter().filter(cs);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

}
