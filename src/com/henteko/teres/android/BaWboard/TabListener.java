package com.henteko.teres.android.BaWboard;

import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ViewFlipper;

//黒板
class Black_TabListener implements android.app.ActionBar.TabListener {

	private Black_Fragment mFragment;
	private ViewFlipper mFlipper;
	private mainActivity act;
	private add_TabListener add_tabLis;

	public Black_TabListener(ViewFlipper Flipper, mainActivity act,
			Black_Fragment mFragment, add_TabListener add_tabLis) {
		mFlipper = Flipper;
		this.act = act;
		this.mFragment = mFragment;
		this.add_tabLis = add_tabLis;

		act.show_Fragment(mFragment, null);
	}

	/** 現在押されていて、もう一度押された時に呼ばれる処理 **/
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		Log.d("aaaaaaaa", "2回目だお");
		// 削除したいけどむりっぽい？
		// act.remove_Fragment(mFragment, null);
		// act.remove_tab(tab.getPosition());
	}

	/** タブが選択されたときの処理 */
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		
		Log.d("childcount:","" + mFlipper.getChildCount());

		Log.d("aaaaaaaa", "" + "tab_p:" + tab.getPosition() + " F_id:"
				+ mFlipper.getDisplayedChild());

		if (tab.getPosition() > mFlipper.getDisplayedChild()) {
			int n = tab.getPosition() - mFlipper.getDisplayedChild();
			for (int i = 0; i < n; i++) {
				mFlipper.showNext();
			}
		} else {
			int n = mFlipper.getDisplayedChild() - tab.getPosition();
			for (int i = 0; i < n; i++) {
				mFlipper.showPrevious();
			}
		}

		add_tabLis.set_back_tab(tab);

		act.board_flag = 2;
		act.show_Fragment(mFragment, null);

		Log.d("aaaaaaaa", "Black_board");
	}

	/** タブの選択が移ったときの処理 */
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		Log.d("aaaa", "Black not selevt!!");
		act.hide_Fragment(mFragment, null);
	}
}

// ホワイトボード
class White_TabListener implements android.app.ActionBar.TabListener {

	private White_Fragment mFragment;
	private ViewFlipper mFlipper;
	private mainActivity act;
	private add_TabListener add_tabLis;

	public White_TabListener(ViewFlipper Flipper, mainActivity act,
			White_Fragment mFragment, add_TabListener add_tabLis) {
		mFlipper = Flipper;
		this.act = act;
		this.mFragment = mFragment;
		this.add_tabLis = add_tabLis;

		// act.hide_Fragment(null, mFragment);
	}

	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// 削除したいけどむりっぽい？
		// act.remove_Fragment(null, mFragment);
		// act.remove_tab(tab.getPosition());
	}

	/** タブが選択されたときの処理 */
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		
		Log.d("childcount:","" + mFlipper.getChildCount());

		Log.d("aaaaaaaa", "" + "tab_p:" + tab.getPosition() + " F_id:"
				+ mFlipper.getDisplayedChild());

		if (tab.getPosition() > mFlipper.getDisplayedChild()) {
			int n = tab.getPosition() - mFlipper.getDisplayedChild();
			for (int i = 0; i < n; i++) {
				mFlipper.showNext();
			}
		} else {
			int n = mFlipper.getDisplayedChild() - tab.getPosition();
			for (int i = 0; i < n; i++) {
				mFlipper.showPrevious();
			}
		}

		add_tabLis.set_back_tab(tab);

		act.board_flag = 1;
		act.show_Fragment(null, mFragment);
		Log.d("aaaaaaaa", "White_board");
	}

	/** タブの選択が移ったときの処理 */
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		Log.d("aaaa", "white not selevt!!");
		act.hide_Fragment(null, mFragment);
	}
}

// タブを追加するタブ
class add_TabListener implements android.app.ActionBar.TabListener {

	private mainActivity act;
	private ViewFlipper mFlipper;
	private Tab back_tab;

	private boolean select_flag = false;

	public add_TabListener(ViewFlipper mFlipper, mainActivity act) {
		this.act = act;
		this.mFlipper = mFlipper;
	}

	public void set_back_tab(Tab tab) {
		this.back_tab = tab;
	}

	/** 現在押されていて、もう一度押された時に呼ばれる処理 **/
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	/** タブが選択されたときの処理 */
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

		// tabを追加しませう
		// trueが黒板
		// falseがホワイトボード
		final EditText editText = new EditText(act);
		editText.setInputType(InputType.TYPE_CLASS_TEXT);
		editText.setHint("board name");

		final CharSequence[] items = { act.getString(R.string.Black_board), act.getString(R.string.White_board) };

		final AlertDialog alertDialog = new AlertDialog.Builder(act)
				.setTitle(R.string.Add_tab)
				.setSingleChoiceItems(items, 1,
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int item) {
								// TODO Auto-generated method stub
								if (item == 0) {
									select_flag = true;
								} else {
									select_flag = false;
								}
							}

						})
				.setView(editText)
				.setPositiveButton(R.string.OK,
						new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								SpannableStringBuilder sbb = (SpannableStringBuilder) editText
										.getText();
								String tmp = sbb.toString();
								act.tab_add_Fragment(select_flag, tmp);
								select_flag = false;

							}
						})
				.setNegativeButton(R.string.CANCEL,
						new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								select_flag = false;
								
								back_tab.select();
							}
						}).create();

		editText.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					alertDialog
							.getWindow()
							.setSoftInputMode(
									WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				}
			}
		});

		alertDialog.show();
	}

	/** タブの選択が移ったときの処理 */
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}
}