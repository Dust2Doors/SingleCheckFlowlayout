package com.zym.widget.helper;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by zym on 2017/8/18.
 */

public class SingleSelectFlowLayout extends FlowLayout {

    private int lasteSelectedPosition = -1;
    public SingleSelectFlowLayout(Context context) {
        super(context);
    }

    public SingleSelectFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSelect(int position){
        int childCount = getChildCount();
        if (position >= childCount) {
            return;
        }
        if (lasteSelectedPosition != -1 && lasteSelectedPosition <= childCount) {
            getChildAt(lasteSelectedPosition).setSelected(false);
        }
        getChildAt(position).setSelected(true);
    }

    public String getSelectText(){
        if (lasteSelectedPosition == -1) {
            return "";
        }
        int childCount = getChildCount();
        Button btn=null;
        if (lasteSelectedPosition <= childCount) {
            btn = (Button) getChildAt(lasteSelectedPosition);
        }
        return btn == null ? "" : btn.getText().toString();
    }

    public void setSelect(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        int childCount = getChildCount();
        if (lasteSelectedPosition != -1 && lasteSelectedPosition <= childCount) {
            getChildAt(lasteSelectedPosition).setSelected(false);
        }
        for(int i = 0 ; i < childCount; i++) {
            Button btn = (Button) getChildAt(i);
            if (text.equals(btn.getText().toString())) {
                btn.setSelected(true);
                lasteSelectedPosition = i;
                break;
            }
        }

    }
}
