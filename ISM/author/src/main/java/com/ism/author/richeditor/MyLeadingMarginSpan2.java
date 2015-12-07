package com.ism.author.richeditor;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.LeadingMarginSpan;

/**
 * Created by c85 on 30/10/15.
 */
public class MyLeadingMarginSpan2 implements LeadingMarginSpan.LeadingMarginSpan2 {

    private int margin;
    private int lines;

    MyLeadingMarginSpan2(int lines, int margin) {
        this.margin = margin;
        this.lines = lines;
    }

    /**
     * Apply the margin
     *
     * @param first
     * @return
     */
    @Override
    public int getLeadingMargin(boolean first) {
        if (first) {
            return margin;
        } else {
            return 0;
        }
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir,
                                  int top, int baseline, int bottom, CharSequence text,
                                  int start, int end, boolean first, Layout layout) {
    }


    @Override
    public int getLeadingMarginLineCount() {
        return lines;
    }
};