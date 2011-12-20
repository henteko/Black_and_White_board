package com.henteko.teres.android.BaWboard;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

public class White_penView extends View {

	ArrayList<Path> draw_list = new ArrayList<Path>();
	ArrayList<Path> draw_list_reando = new ArrayList<Path>();
	ArrayList<Paint> draw_paint = new ArrayList<Paint>();
	ArrayList<Paint> draw_paint_reando = new ArrayList<Paint>();
	private float posx = 0f;
	private float posy = 0f;
	private Path path = null;

	private boolean Touch_flag = false;

	private Paint paint = new Paint();

	private int color_flag = 0;

	public float pen_num = 5;

	private int public_board_color = Color.BLACK;

	private int BOARD_COLOR = Color.WHITE;

	private int BLACK_BOARD_PEN_COLOR_RED = Color.RED;
	private int BLACK_BOARD_PEN_COLOR_BLUE = Color.BLUE;
	private int BLACK_BOARD_PEN_COLOR_GREEN = Color.GREEN;
	private int BLACK_BOARD_PEN_COLOR_YELLOW = Color.YELLOW;

	public White_penView(Context context) {
		super(context);

		// View�̔w�i�F������
		setBackgroundColor(BOARD_COLOR);

		paint.setColor(public_board_color);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);
		// ���̑���������
		paint.setStrokeWidth(pen_num);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeJoin(Paint.Join.ROUND);
	}

	public void change_Color(int num) {
		if (num == 0) {
			color_flag = 0;
			paint.setColor(public_board_color);
			// pen_num = 5;
			paint.setStrokeWidth(pen_num);
		} else if (num == 1) {
			color_flag = 1;
			paint.setColor(BLACK_BOARD_PEN_COLOR_RED);
			// pen_num = 5;
			paint.setStrokeWidth(pen_num);
		} else if (num == 2) {
			color_flag = 2;
			paint.setColor(BLACK_BOARD_PEN_COLOR_BLUE);
			// pen_num = 5;
			paint.setStrokeWidth(pen_num);
		} else if (num == 3) {
			color_flag = 3;
			paint.setColor(BLACK_BOARD_PEN_COLOR_GREEN);
			// pen_num = 5;
			paint.setStrokeWidth(pen_num);
		} else if (num == 4) {
			color_flag = 4;
			paint.setColor(BLACK_BOARD_PEN_COLOR_YELLOW);
			// pen_num = 5;
			paint.setStrokeWidth(pen_num);
		} else if (num == 5) {
			color_flag = 5;
			paint.setColor(BOARD_COLOR);
			// pen_num = 20;
			paint.setStrokeWidth(pen_num);
		}
	}

	public void changing_Pen() {
		Paint tmp_paint = new Paint();
		if (color_flag == 0) {
			tmp_paint.setColor(public_board_color);
			tmp_paint.setStrokeWidth(pen_num);
		} else if (color_flag == 1) {
			tmp_paint.setColor(BLACK_BOARD_PEN_COLOR_RED);
			tmp_paint.setStrokeWidth(pen_num);
		} else if (color_flag == 2) {
			tmp_paint.setColor(BLACK_BOARD_PEN_COLOR_BLUE);
			tmp_paint.setStrokeWidth(pen_num);
		} else if (color_flag == 3) {
			tmp_paint.setColor(BLACK_BOARD_PEN_COLOR_GREEN);
			tmp_paint.setStrokeWidth(pen_num);
		} else if (color_flag == 4) {
			tmp_paint.setColor(BLACK_BOARD_PEN_COLOR_YELLOW);
			tmp_paint.setStrokeWidth(pen_num);
		} else if (color_flag == 5) {
			tmp_paint.setColor(BOARD_COLOR);
			tmp_paint.setStrokeWidth(pen_num);
		}
		tmp_paint.setAntiAlias(true);
		tmp_paint.setStyle(Paint.Style.STROKE);
		tmp_paint.setStrokeCap(Paint.Cap.ROUND);
		tmp_paint.setStrokeJoin(Paint.Join.ROUND);
		draw_paint.add(tmp_paint);
	}

	public void ando_Pen() {
		// ���O�̍폜
		if (draw_list.size() != 0) {
			draw_list_reando.add(draw_list.get(draw_list.size() - 1));
			draw_list.remove(draw_list.size() - 1);
			draw_paint_reando.add(draw_paint.get(draw_paint.size() - 1));
			draw_paint.remove(draw_paint.size() - 1);
			invalidate();
		}
	}

	public void change_Pen_num(float num) {
		// �y���̑����ύX
		pen_num = num;
		paint.setStrokeWidth(pen_num);
	}

	public void reando_Pen() {
		// ���O�̍폜�̍폜
		if (draw_list_reando.size() != 0) {
			draw_list.add(draw_list_reando.get(draw_list_reando.size() - 1));
			draw_list_reando.remove(draw_list_reando.size() - 1);
			draw_paint.add(draw_paint_reando.get(draw_paint_reando.size() - 1));
			draw_paint_reando.remove(draw_paint_reando.size() - 1);
			invalidate();
		}
	}

	// �S�폜
	public void view_clear() {
		draw_list.clear();
		draw_list_reando.clear();
		draw_paint.clear();
		draw_paint_reando.clear();
		invalidate();
	}

	public void onDraw(Canvas canvas) {
		for (int i = 0; i < draw_list.size(); i++) {
			Path pt = draw_list.get(i);
			Paint pa = draw_paint.get(i);
			canvas.drawPath(pt, pa);
		}
		// current
		if (Touch_flag) {
			canvas.drawPath(path, paint);
		}
	}

	public boolean onTouchEvent(MotionEvent e) {

		int pointerCount = e.getPointerCount();

		if (pointerCount == 1) {
			//�V���O���^�b�`�̎�����
			switch (e.getAction()) {
			case MotionEvent.ACTION_DOWN: // �ŏ��̃|�C���g
				path = new Path();
				posx = e.getX();
				posy = e.getY();
				path.moveTo(e.getX(), e.getY());

				Touch_flag = true;

				break;
			case MotionEvent.ACTION_MOVE: // �r���̃|�C���g
				posx += (e.getX() - posx) / 1.4;
				posy += (e.getY() - posy) / 1.4;
				path.lineTo(posx, posy);
				invalidate();
				break;
			case MotionEvent.ACTION_UP: // �Ō�̃|�C���g
				path.lineTo(e.getX(), e.getY());
				draw_list.add(path);
				changing_Pen();

				Touch_flag = false;

				invalidate();
				break;
			default:
				break;
			}
			return true;
		}else {
			//�}���`�^�b�`��
			//Activity�Ƀ^�b�`�C�x���g��m�点��ׂ�
			return false;
		}
	}

}