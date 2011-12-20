package com.henteko.teres.android.BaWboard;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ViewFlipper;

public class mainActivity extends FragmentActivity {

	private ActionBar bar;
	private ViewFlipper mFlipper;

	private add_TabListener add_tabLis;

	private static final int MENU_ADD_ID = Menu.FIRST;

	private boolean menu_flag = true;
	public int board_flag = 2;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		if (isHoneycomb()) {

			Black_Fragment black_board = add_Black_Fragment();

			mFlipper = (ViewFlipper) findViewById(R.id.viewFlipper1);

			add_tabLis = new add_TabListener(mFlipper, this);

			bar = getActionBar();
			bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
			bar.addTab(bar
					.newTab()
					.setText(R.string.defo_black_board_name)
					.setTabListener(
							new Black_TabListener(mFlipper, this, black_board,
									add_tabLis)));
			bar.addTab(bar.newTab().setText("+").setTabListener(add_tabLis));

		} else {
			// ハニカム端末じゃない時
			// Fragmentを動的に加えるのなら下
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			mini_mainFragment mini_mainfragment = new mini_mainFragment();
			ft.add(mini_mainfragment, "mini_mainfragment");
			ft.commit();
		}
	}

	public Black_Fragment add_Black_Fragment() {
		// Fragmentを動的に加えるのなら下
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Black_Fragment black_board = new Black_Fragment();
		ft.add(black_board, "black_board");
		ft.commit();
		//下ので、強制的にcommitをすぐ実行する
		//参考:http://y-anz-m.blogspot.com/2011/05/androidfragment_19.html
		fm.executePendingTransactions();

		return black_board;
	}

	public White_Fragment add_White_Fragment() {
		// Fragmentを動的に加えるのなら下
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		White_Fragment white_board = new White_Fragment();
		ft.add(white_board, "white_board");
		ft.commit();
		//下ので、強制的にcommitをすぐ実行する
		//参考:http://y-anz-m.blogspot.com/2011/05/androidfragment_19.html
		fm.executePendingTransactions();
		

		return white_board;
	}

	public void show_Fragment(Black_Fragment fb, White_Fragment fw) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		if (fw == null) {
			ft.show(fb);
		} else {
			ft.show(fw);
		}

		ft.commit();
	}

	public void hide_Fragment(Black_Fragment fb, White_Fragment fw) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		if (fw == null) {
			ft.hide(fb);
		} else {
			ft.hide(fw);
		}

		ft.commit();
	}

	public void remove_Fragment(Black_Fragment fb, White_Fragment fw) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		if (fw == null) {
			ft.remove(fb);
		} else {
			ft.remove(fw);
		}

		ft.commit();
	}

	public void remove_tab(int num) {
		bar.removeTabAt(num);
	}

	public void tab_add_Fragment(boolean flag, String title) {

		if (flag) {
			Log.d("childcount:","" + mFlipper.getChildCount());
			Black_Fragment black_board = add_Black_Fragment();
			Log.d("childcount:","" + mFlipper.getChildCount());
			Tab tab = bar
					.newTab()
					.setText(title)
					.setTabListener(
							new Black_TabListener(mFlipper, this, black_board,
									add_tabLis));
			bar.addTab(tab, bar.getTabCount() - 1);
			tab.select();
		} else {
			Log.d("childcount:","" + mFlipper.getChildCount());
			White_Fragment white_board = add_White_Fragment();
			Log.d("childcount:","" + mFlipper.getChildCount());
			Tab tab = bar
					.newTab()
					.setText(title)
					.setTabListener(
							new White_TabListener(mFlipper, this, white_board,
									add_tabLis));
			bar.addTab(tab, bar.getTabCount() - 1);
			tab.select();
		}
	}

	// 端末がタブレットかどうか
	// 参考:http://andbrowser.com/development/knowhow/192/1apk-tablet-phone-development-2/
	public static boolean isHoneycomb() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	// メニューの定義
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		if (isHoneycomb()) {
			// ハニカム以上の時限定

			if (menu_flag) {
				// MenuItem add = menu.add(0, MENU_ADD_ID, 0, R.string.Save_as);
				// if (isHoneycomb()) {
				// add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
				// }
				// add.setIcon(R.drawable.disk);

				MenuItem add = menu.add(0, MENU_ADD_ID, 0,
						R.string.Change_Pen_num);
				add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
				add.setIcon(R.drawable.record);

				add = menu.add(0, MENU_ADD_ID + 1, 0, R.string.Redo);
				add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
				add.setIcon(R.drawable.arrowleft);

				add = menu.add(0, MENU_ADD_ID + 2, 0, R.string.Reverse_Redo);
				add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
				add.setIcon(R.drawable.arrowright);

				add = menu.add(0, MENU_ADD_ID + 3, 0, R.string.Clear);
				add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
				add.setIcon(R.drawable.delete);

				menu.add(0, MENU_ADD_ID + 4, 0, R.string.White);
				menu.add(0, MENU_ADD_ID + 5, 0, R.string.Red);
				menu.add(0, MENU_ADD_ID + 6, 0, R.string.Blue);
				menu.add(0, MENU_ADD_ID + 7, 0, R.string.Green);
				menu.add(0, MENU_ADD_ID + 8, 0, R.string.Yellow);
				menu.add(0, MENU_ADD_ID + 9, 0, R.string.Eraser);
				menu.add(0, MENU_ADD_ID + 10, 0, R.string.Save_as);
				menu_flag = false;
			} else if (board_flag == 1) {
				menu.removeItem(MENU_ADD_ID);
				menu.removeItem(MENU_ADD_ID + 1);
				menu.removeItem(MENU_ADD_ID + 2);
				menu.removeItem(MENU_ADD_ID + 3);
				menu.removeItem(MENU_ADD_ID + 4);
				menu.removeItem(MENU_ADD_ID + 5);
				menu.removeItem(MENU_ADD_ID + 6);
				menu.removeItem(MENU_ADD_ID + 7);
				menu.removeItem(MENU_ADD_ID + 8);
				menu.removeItem(MENU_ADD_ID + 9);
				menu.removeItem(MENU_ADD_ID + 10);

				MenuItem add = menu.add(0, MENU_ADD_ID, 0,
						R.string.Change_Pen_num);
				add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
				add.setIcon(R.drawable.record);

				add = menu.add(0, MENU_ADD_ID + 1, 0, R.string.Redo);
				add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
				add.setIcon(R.drawable.arrowleft);

				add = menu.add(0, MENU_ADD_ID + 2, 0, R.string.Reverse_Redo);
				add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
				add.setIcon(R.drawable.arrowright);

				add = menu.add(0, MENU_ADD_ID + 3, 0, R.string.Clear);
				add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
				add.setIcon(R.drawable.delete);

				menu.add(0, MENU_ADD_ID + 4, 0, R.string.Black);
				menu.add(0, MENU_ADD_ID + 5, 0, R.string.Red);
				menu.add(0, MENU_ADD_ID + 6, 0, R.string.Blue);
				menu.add(0, MENU_ADD_ID + 7, 0, R.string.Green);
				menu.add(0, MENU_ADD_ID + 8, 0, R.string.Yellow);
				menu.add(0, MENU_ADD_ID + 9, 0, R.string.Eraser);
				menu.add(0, MENU_ADD_ID + 10, 0, R.string.Save_as);
			} else if (board_flag == 2) {
				menu.removeItem(MENU_ADD_ID);
				menu.removeItem(MENU_ADD_ID + 1);
				menu.removeItem(MENU_ADD_ID + 2);
				menu.removeItem(MENU_ADD_ID + 3);
				menu.removeItem(MENU_ADD_ID + 4);
				menu.removeItem(MENU_ADD_ID + 5);
				menu.removeItem(MENU_ADD_ID + 6);
				menu.removeItem(MENU_ADD_ID + 7);
				menu.removeItem(MENU_ADD_ID + 8);
				menu.removeItem(MENU_ADD_ID + 9);
				menu.removeItem(MENU_ADD_ID + 10);

				MenuItem add = menu.add(0, MENU_ADD_ID, 0,
						R.string.Change_Pen_num);
				add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
				add.setIcon(R.drawable.record);

				add = menu.add(0, MENU_ADD_ID + 1, 0, R.string.Redo);
				add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
				add.setIcon(R.drawable.arrowleft);

				add = menu.add(0, MENU_ADD_ID + 2, 0, R.string.Reverse_Redo);
				add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
				add.setIcon(R.drawable.arrowright);

				add = menu.add(0, MENU_ADD_ID + 3, 0, R.string.Clear);
				add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
				add.setIcon(R.drawable.delete);

				menu.add(0, MENU_ADD_ID + 4, 0, R.string.White);
				menu.add(0, MENU_ADD_ID + 5, 0, R.string.Red);
				menu.add(0, MENU_ADD_ID + 6, 0, R.string.Blue);
				menu.add(0, MENU_ADD_ID + 7, 0, R.string.Green);
				menu.add(0, MENU_ADD_ID + 8, 0, R.string.Yellow);
				menu.add(0, MENU_ADD_ID + 9, 0, R.string.Eraser);
				menu.add(0, MENU_ADD_ID + 10, 0, R.string.Save_as);
			}
		}

		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d("TouchEvent", "X:" + event.getX() + ",Y:" + event.getY());

		int historySize = event.getHistorySize(); // ACTION_MOVEイベントの数
		int pointerCount = event.getPointerCount();

		// if(pointerCount >= 3 && event.getAction() == MotionEvent.ACTION_MOVE)
		// {
		// FragmentManager fm = getSupportFragmentManager();
		//
		// FragmentTransaction ft = fm.beginTransaction();
		// White_Fragment white_board = new White_Fragment();
		// // ft.add(white_board, "white_board");
		// // ft.commit();
		// ft.add(white_board, "");
		// ft.addToBackStack(null);
		// ft.commit();
		// }

		// // イベントの発生時刻
		// Log.d("TouchEvent", "event time: " + event.getEventTime());
		//
		// // ポインタIDの取得、ポインタ座標
		// for (int p = 0; p < pointerCount; p++) {
		// Log.d("TouchEvent", "Pointer ID :" + event.getPointerId(p) + " X "
		// + event.getX(p) + " , " + "Y " + event.getY(p) + " , ");
		// }
		//
		// // イベント履歴
		// for (int h = 0; h < historySize; h++) {
		// Log.d("TouchEventHist",
		// "event time: " + event.getHistoricalEventTime(h));
		// for (int p = 0; p < pointerCount; p++) {
		// Log.d("TouchEventHist", "Pointer ID :" + event.getPointerId(p)
		// + " X " + event.getHistoricalX(p, h) + " , " + "Y "
		// + event.getHistoricalY(p, h) + " , ");
		// }
		// }

		return true;
	}

	// Backキーでのダイアログ生成
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(mainActivity.this)
					.setMessage(R.string.Finish)
					.setCancelable(false)
					.setPositiveButton(R.string.Yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// 終了
									System.exit(RESULT_OK);
								}
							})
					.setNegativeButton(R.string.No,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// 何もしない
								}
							}).show();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	// @Override
	// public void onTabReselected(Tab tab, android.app.FragmentTransaction _ft)
	// {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// // Tabが選択されたときの処理
	// @Override
	// public void onTabSelected(Tab tab, android.app.FragmentTransaction _ft) {
	// // TODO Auto-generated method stub
	// if ((String) tab.getText() == "White board") {
	// if (fragment_List.size() == 1) {
	// FragmentTransaction ft = fm.beginTransaction();
	// mainFragment white_board = new mainFragment();
	// // ft.add(white_board, "white_board");
	// // ft.commit();
	// ft.replace(MODE_PRIVATE, white_board);
	// ft.addToBackStack(null);
	//
	// // Commit the transaction
	// ft.commit();
	// fragment_List.add(white_board);
	// }else {
	// FragmentTransaction ft = fm.beginTransaction();
	// ft.replace(MODE_PRIVATE, fragment_List.get(1));
	// ft.addToBackStack(null);
	//
	// // Commit the transaction
	// ft.commit();
	// }
	// } else {
	// FragmentTransaction ft = fm.beginTransaction();
	// ft.replace(MODE_PRIVATE, fragment_List.get(0));
	// ft.addToBackStack(null);
	//
	// // Commit the transaction
	// ft.commit();
	// }
	// }
	//
	// // タブが選択されなくなった時(他のタブに移った時)
	// @Override
	// public void onTabUnselected(Tab tab, android.app.FragmentTransaction _ft)
	// {
	// // TODO Auto-generated method stub
	//
	// }

}