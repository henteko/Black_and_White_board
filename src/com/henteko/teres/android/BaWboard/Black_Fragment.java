package com.henteko.teres.android.BaWboard;

import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class Black_Fragment extends Fragment {

	private static final int MENU_ADD_ID = Menu.FIRST;

	private boolean menu_flag = true;

	Black_penView penview;

	private float tmp_pen_num = 5;

	Activity act;

	private SeekBar seekbar;
	private View seekView;
	private Dialog dialog;

	@Override
	public void onAttach(Activity act) {
		super.onAttach(act);
		this.act = act;
		penview = new Black_penView(act.getApplicationContext());
		ViewFlipper mFlipper = (ViewFlipper) act.findViewById(R.id.viewFlipper1);
		mFlipper.addView(penview);
//		act.setContentView(penview);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// これで、オプションを加えるメソッドを呼び出す
		setHasOptionsMenu(true);

		// 空のlayoutを設定
		View v = inflater.inflate(R.layout.mainfragment, container, false);

		/*********************
		 * ここからはペンの太さ変更
		 */

		View v2 = inflater.inflate(R.layout.seekdialog, container, false);
		seekView = v2.findViewById(R.id.dialog_root);

		seekbar = (SeekBar) seekView.findViewById(R.id.seekbar);
		seekbar.setMax(100);
		seekbar.setProgress((int) penview.pen_num);
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// progressにシークの値が来る
				tmp_pen_num = progress;
			}
		});

		// ここはact(Activity自体)を渡さなければならない
		// 参照：http://d.hatena.ne.jp/ats337/20110322/1300803196
		dialog = new AlertDialog.Builder(act)
				.setTitle(R.string.Change_Pen_num)
				.setView(seekView)
				.setPositiveButton(R.string.Change_OK_Pen_num,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// OKなのでペンの太さを更新
								penview.change_Pen_num(tmp_pen_num);
							}
						})
				.setNegativeButton(R.string.Change_NO_Pen_num,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// キャンセルなので、シークバーの初期設定を元に戻す
								seekbar.setProgress((int) penview.pen_num);
							}
						}).create();

		return v;
	}

//	@Override
//	public void onPrepareOptionsMenu(Menu menu) {
//		super.onCreateOptionsMenu(menu, null);
//
//		if (menu_flag) {
//			// MenuItem add = menu.add(0, MENU_ADD_ID, 0, R.string.Save_as);
//			// if (isHoneycomb()) {
//			// add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//			// }
//			// add.setIcon(R.drawable.disk);
//
//			MenuItem add = menu.add(0, MENU_ADD_ID, 0, R.string.Change_Pen_num);
//			add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//			add.setIcon(R.drawable.record);
//
//			add = menu.add(0, MENU_ADD_ID + 1, 0, R.string.Redo);
//			add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//			add.setIcon(R.drawable.arrowleft);
//
//			add = menu.add(0, MENU_ADD_ID + 2, 0, R.string.Reverse_Redo);
//			add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//			add.setIcon(R.drawable.arrowright);
//
//			add = menu.add(0, MENU_ADD_ID + 3, 0, R.string.Clear);
//			add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//			add.setIcon(R.drawable.delete);
//
//			menu.add(0, MENU_ADD_ID + 4, 0, R.string.White);
//			menu.add(0, MENU_ADD_ID + 5, 0, R.string.Red);
//			menu.add(0, MENU_ADD_ID + 6, 0, R.string.Blue);
//			menu.add(0, MENU_ADD_ID + 7, 0, R.string.Green);
//			menu.add(0, MENU_ADD_ID + 8, 0, R.string.Yellow);
//			menu.add(0, MENU_ADD_ID + 9, 0, R.string.Eraser);
//			menu.add(0, MENU_ADD_ID + 10, 0, R.string.Save_as);
//			menu_flag = false;
//		}
//		
//		
//	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ADD_ID:
			// ペンの太さを変更
			dialog.show();
			return true;
		case MENU_ADD_ID + 1:
			penview.ando_Pen();
			return true;
		case MENU_ADD_ID + 2:
			penview.reando_Pen();
			return true;
		case MENU_ADD_ID + 3:
			new AlertDialog.Builder(act)
					.setMessage(R.string.Clear_OK)
					.setCancelable(false)
					.setPositiveButton(R.string.Yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									penview.view_clear();
								}
							})
					.setNegativeButton(R.string.No,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							}).show();
			return true;
		case MENU_ADD_ID + 4:
			penview.change_Color(0);
			return true;
		case MENU_ADD_ID + 5:
			penview.change_Color(1);
			return true;
		case MENU_ADD_ID + 6:
			penview.change_Color(2);
			return true;
		case MENU_ADD_ID + 7:
			penview.change_Color(3);
			return true;
		case MENU_ADD_ID + 8:
			penview.change_Color(4);
			return true;
		case MENU_ADD_ID + 9:
			penview.change_Color(5);
			return true;
		case MENU_ADD_ID + 10:
			// Optimus padではDCIM/Camera内に保存される
			save(penview);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// 画像を保存する
	public void save(Black_penView v) {

		v.setDrawingCacheEnabled(true);
		Bitmap screenshot = Bitmap.createBitmap(v.getDrawingCache());
		v.setDrawingCacheEnabled(false);

		if (screenshot != null) {
			try {
				String dst = "screen.png";
				FileOutputStream output = act.getApplicationContext()
						.openFileOutput(dst, Context.MODE_WORLD_READABLE);

				screenshot.compress(Bitmap.CompressFormat.PNG, 100, output);
				MediaStore.Images.Media.insertImage(act.getApplicationContext()
						.getContentResolver(), screenshot, "", null);

				Toast.makeText(act, R.string.Save, Toast.LENGTH_SHORT).show();
			} catch (IOException e) {
				e.printStackTrace();
				Toast.makeText(act, R.string.Not_Save, Toast.LENGTH_SHORT)
						.show();
			}

		}

	}

}
