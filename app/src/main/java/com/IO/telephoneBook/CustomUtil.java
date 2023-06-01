package com.IO.telephoneBook;
import android.app.*;
import android.content.*;
import android.graphics.drawable.*;
import android.net.*;
import android.util.*;
import android.view.*;
import android.view.inputmethod.*;
import android.widget.*;

import java.io.*;
import java.util.*;


public class CustomUtil {

	public static int TOP = 1;
	public static int CENTER = 2;
	public static int BOTTOM = 3;

	public static void CustomToast(Context _context, String _message, int _textColor, int _textSize, int _bgColor, int _radius, int _gravity) {
		Toast _toast = Toast.makeText(_context, _message, Toast.LENGTH_SHORT);
		View _view = _toast.getView();
		TextView _textView = _view.findViewById(android.R.id.message);
		_textView.setTextSize(_textSize);
		_textView.setTextColor(_textColor);
		_textView.setGravity(Gravity.CENTER);
		
		GradientDrawable _gradientDrawable = new GradientDrawable();
		_gradientDrawable.setColor(_bgColor);
		_gradientDrawable.setCornerRadius(_radius);
		_view.setBackgroundDrawable(_gradientDrawable);
		_view.setPadding(15, 10, 15, 10);
		_view.setElevation(10);

		switch (_gravity) {
			case 1:
				_toast.setGravity(Gravity.TOP, 0, 150);
				break;

			case 2:
				_toast.setGravity(Gravity.CENTER, 0, 0);
				break;

			case 3:
				_toast.setGravity(Gravity.BOTTOM, 0, 150);
				break;
		}
		_toast.show();
	}

	public static void CustomToastWithIcon(Context _context, String _message, int _textColor, int _textSize, int _bgColor, int _radius, int _gravity, int _icon) {
		Toast _toast = Toast.makeText(_context, _message, Toast.LENGTH_SHORT);
		View _view = _toast.getView();
		TextView _textView = (TextView) _view.findViewById(android.R.id.message);
		_textView.setTextSize(_textSize);
		_textView.setTextColor(_textColor);
		_textView.setCompoundDrawablesWithIntrinsicBounds(_icon, 0, 0, 0);
		_textView.setGravity(Gravity.CENTER);
		_textView.setCompoundDrawablePadding(10);
		
		GradientDrawable _gradientDrawable = new GradientDrawable();
		_gradientDrawable.setColor(_bgColor);
		_gradientDrawable.setCornerRadius(_radius);
		_view.setBackgroundDrawable(_gradientDrawable);
		_view.setPadding(10, 10, 10, 10);
		_view.setElevation(10);

		switch (_gravity) {
			case 1:
				_toast.setGravity(Gravity.TOP, 0, 150);
				break;

			case 2:
				_toast.setGravity(Gravity.CENTER, 0, 0);
				break;

			case 3:
				_toast.setGravity(Gravity.BOTTOM, 0, 150);
				break;
		}
		_toast.show();
	}

	public static String copyFromInputStream(InputStream _inputStream) {
		ByteArrayOutputStream _outputStream = new ByteArrayOutputStream();
		byte[] _buf = new byte[1024];
		int _i;
		try {
			while ((_i = _inputStream.read(_buf)) != -1){
				_outputStream.write(_buf, 0, _i);
			}
			_outputStream.close();
			_inputStream.close();
		} catch (IOException _e) {
		}
		
		return _outputStream.toString();
	}

	public static void hideKeyboard(Context _context) {
		InputMethodManager _inputMethodManager = (InputMethodManager) _context.getSystemService(Context.INPUT_METHOD_SERVICE);
		_inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
	}
	
	public static void showKeyboard(Context _context) {
		InputMethodManager _inputMethodManager = (InputMethodManager) _context.getSystemService(Context.INPUT_METHOD_SERVICE);
		_inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}
	
	public static void showMessage(Context _context, String _s) {
		Toast.makeText(_context, _s, Toast.LENGTH_SHORT).show();
	}

	public static int getLocationX(View _view) {
		int _location[] = new int[2];
		_view.getLocationInWindow(_location);
		return _location[0];
	}

	public static int getLocationY(View _view) {
		int _location[] = new int[2];
		_view.getLocationInWindow(_location);
		return _location[1];
	}

	public static int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}

	public static float getDip(Context _context, int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, _context.getResources().getDisplayMetrics());
	}

	public static int getDisplayWidthPixels(Context _context) {
		return _context.getResources().getDisplayMetrics().widthPixels;
	}

	public static int getDisplayHeightPixels(Context _context) {
		return _context.getResources().getDisplayMetrics().heightPixels;
	}
}
