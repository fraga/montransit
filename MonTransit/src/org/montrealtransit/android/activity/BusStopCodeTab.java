package org.montrealtransit.android.activity;

import java.util.List;

import org.montrealtransit.android.MyLog;
import org.montrealtransit.android.R;
import org.montrealtransit.android.Utils;
import org.montrealtransit.android.dialog.BusLineSelectDirection;
import org.montrealtransit.android.provider.DataManager;
import org.montrealtransit.android.provider.DataStore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * This activity display a search text box for entering bus stop code. The user can also enter a bus line number. In the future, this activity will have the
 * same functionalities as a search box for almost everything.
 * @author Mathieu Méa
 */
public class BusStopCodeTab extends Activity implements OnKeyListener, OnClickListener, OnItemClickListener {

	/**
	 * The log tag.
	 */
	private static final String TAG = BusStopCodeTab.class.getSimpleName();

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyLog.v(TAG, "onCreate()");
		super.onCreate(savedInstanceState);
		// set the UI
		setContentView(R.layout.bus_stop_code_tab);
		((AutoCompleteTextView) findViewById(R.id.field)).setOnKeyListener(this);
		((ListView) findViewById(R.id.list)).setEmptyView(findViewById(R.id.list_empty));
		((ListView) findViewById(R.id.list)).setOnItemClickListener(this);
		((ImageButton) findViewById(R.id.ok)).setOnClickListener(this);
		((ListView) findViewById(R.id.list)).setAdapter(getHistoryAdapter());
	}

	/**
	 * Return the auto complete adapter.
	 * @return the auto complete adapter
	 */
	private ArrayAdapter<String> getAutoCompleteAdapter() {
		List<String> objects = DataManager.findAllHistoryList(this.getContentResolver());
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,
		        objects);
		return arrayAdapter;
	}

	/**
	 * Return the history adapter. Since it's created from the cursor, it will be updated automatically.
	 * @return the history adapter.
	 */
	private ListAdapter getHistoryAdapter() {
		SimpleCursorAdapter historyItems = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
		        DataManager.findAllHistory(this.getContentResolver()), new String[] { DataStore.History.VALUE },
		        new int[] { android.R.id.text1 });
		// historyItems.setViewBinder(this);
		return historyItems;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onResume() {
		MyLog.v(TAG, "onResume()");
		super.onResume();
		// refresh the auto complete text data
		((AutoCompleteTextView) findViewById(R.id.field)).setAdapter(getAutoCompleteAdapter());
	}

	/**
	 * Search for a match for the search text. Could redirect the user to a bus stop or a bus line for now.
	 * @param search the search text.
	 */
	private void searchFor(String search) {
		if (search == null || search.length() == 0) {
			// please enter a number
			Utils.notifyTheUser(this, getResources().getString(R.string.please_enter_a_stop_code));
		} else {
			if (search.length() <= 3) {
				// search for a bus line number
				if (Utils.isBusLineNumberValid(this, search)) {
					addToHistory(search);
					BusLineSelectDirection busLineSelectDirection = new BusLineSelectDirection(this, search);
					busLineSelectDirection.showDialog();
				} else {
					String message = getResources().getString(R.string.wrong_line_number_before) + search
					        + getResources().getString(R.string.wrong_line_number_after);
					Utils.notifyTheUserLong(this, message);
				}
			} else if (search.length() == 5) {
				// search for a bus stop code
				if (Utils.isStopCodeValid(this, search)) {
					addToHistory(search);
					showBusStopInfo(search);
				} else {
					String message = getResources().getString(R.string.wrong_stop_code_before) + search
					        + getResources().getString(R.string.wrong_stop_code_after);
					Utils.notifyTheUserLong(this, message);
				}
			}
		}
	}

	/**
	 * Add a value to the history.
	 * @param search the search string.
	 */
	private void addToHistory(String search) {
		// save to the history
		DataStore.History history = new DataStore.History();
		history.setValue(search);
		DataManager.addHistory(this.getContentResolver(), history);
	}

	/**
	 * Redirect the user to the bus stop info activity.
	 * @param stopCode the bus stop code
	 */
	private void showBusStopInfo(String stopCode) {
		Intent intent = new Intent(this, BusStopInfo.class);
		intent.putExtra(BusStopInfo.EXTRA_STOP_CODE, stopCode);
		startActivity(intent);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
		MyLog.v(TAG, "onItemClick(" + l.getId() + "," + v.getId() + "," + position + "," + id + ")");
		searchFor((((TextView) v).getText()).toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onClick(View v) {
		MyLog.v(TAG, "onItemClick(" + v.getId() + ")");
		searchFor(((EditText) findViewById(R.id.field)).getText().toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// MyTrace.v(TAG, "onKey(" + v.getId() + "," + keyCode + "," + event.getAction() + ")");
		if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
			searchFor(((EditText) findViewById(R.id.field)).getText().toString());
			return true;
		}
		return false;
	}

	/**
	 * Menu item for clearing the history.
	 */
	private static final int MENU_CLEAR_HISTOTY = Menu.FIRST;

	/**
	 * The menu used to show the user preferences.
	 */
	private static final int MENU_PREFERENCES = Menu.FIRST + 1;
	/**
	 * The menu used to show the about screen.
	 */
	private static final int MENU_ABOUT = Menu.FIRST + 2;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem menuClearHistory = menu.add(0, MENU_CLEAR_HISTOTY, Menu.NONE, R.string.clear_history);
		menuClearHistory.setIcon(android.R.drawable.ic_menu_delete);
		MenuItem menuPref = menu.add(0, MENU_PREFERENCES, Menu.NONE, R.string.menu_preferences);
		menuPref.setIcon(android.R.drawable.ic_menu_preferences);
		MenuItem menuAbout = menu.add(0, MENU_ABOUT, Menu.NONE, R.string.menu_about);
		menuAbout.setIcon(android.R.drawable.ic_menu_info_details);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_CLEAR_HISTOTY:
			DataManager.deleteHistory(this.getContentResolver());
			break;
		case MENU_PREFERENCES:
			startActivity(new Intent(this, UserPreferences.class));
			break;
		case MENU_ABOUT:
			Utils.showAboutDialog(this);
			break;
		default:
			MyLog.d(TAG, "Unknow menu action:" + item.getItemId() + ".");
		}
		return false;
	}
}
