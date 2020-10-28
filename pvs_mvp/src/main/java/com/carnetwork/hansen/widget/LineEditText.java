package com.carnetwork.hansen.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.appcompat.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.carnetwork.hansen.R;


/**
 * 创建者 by ${HanSir} on 2018/10/26.
 * 版权所有  WELLTRANS.
 * 说明
 */

public class LineEditText extends AppCompatEditText {

private Paint paint;
    public LineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.grey2));
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0, this.getHeight()-0.1f, this.getWidth()-2, this.getHeight()-0.1f, paint);
    }
}
