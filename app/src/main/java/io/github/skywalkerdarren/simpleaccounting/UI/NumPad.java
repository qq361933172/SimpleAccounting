package io.github.skywalkerdarren.simpleaccounting.UI;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.math.BigDecimal;

import io.github.skywalkerdarren.simpleaccounting.R;
import io.github.skywalkerdarren.simpleaccounting.model.CalculateUtil;

/**
 * Created by darren on 2018/3/10.
 */

public class NumPad extends LinearLayout {
    private EditText mEditText;


    public NumPad(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.num_pad, this, true);

        KeyboardView keyboardView = findViewById(R.id.num_keyboard);
        Keyboard keyboard = new Keyboard(context, R.xml.keyboard);

        keyboardView.setKeyboard(keyboard);
        keyboardView.setEnabled(true);

        keyboardView.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int i) {

            }

            @Override
            public void onRelease(int i) {

            }

            @Override
            public void onKey(int i, int[] ints) {
                Editable editable = mEditText.getText();
                int position = mEditText.getSelectionStart();

                switch (i) {
                    case Keyboard.KEYCODE_DELETE:
                        if (editable != null && editable.length() > 0 && position > 0) {
                            editable.delete(position - 1, position);
                        }
                        break;
                    case Keyboard.KEYCODE_CANCEL:
                        editable.clear();
                        break;
                    case Keyboard.KEYCODE_DONE:
                        BigDecimal result = CalculateUtil.getResult(() -> processExp());
                        editable.clear();
                        editable.append(result.toString());
                    default:
                        editable.insert(position, String.valueOf((char) i));
                        break;
                }

            }

            @Override
            public void onText(CharSequence charSequence) {

            }

            @Override
            public void swipeLeft() {

            }

            @Override
            public void swipeRight() {

            }

            @Override
            public void swipeDown() {

            }

            @Override
            public void swipeUp() {

            }
        });
    }

    private CharSequence processExp() {
        //TODO 处理表达式
        return null;
    }

    public void setStrReceiver(EditText balanceEditText) {
        mEditText = balanceEditText;
    }
}
