package com.turlir.abakgists.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.recyclerview.widget.RecyclerView;

public class SpaceDecorator extends RecyclerView.ItemDecoration {

    private final int mLeft, mTop, mRight, mBottom;

    /**
     * @param cnt контекст
     * @param all отступ для всех сторон
     */
    public SpaceDecorator(Context cnt, @DimenRes int all) {
        this(cnt, all, all, all, all);
    }

    /**
     * @param all отступ для всех сторон
     */
    public SpaceDecorator(int all) {
        this(all, all, all, all);
    }

    /**
     * @param cnt контекст
     * @param side отступ с боков
     * @param front отступ сверху и снизу
     */
    public SpaceDecorator(Context cnt, @DimenRes int side, @DimenRes int front) {
        this(cnt, side, front, side, front);
    }

    /**
     * @param cnt контекст
     * @param left отступ слева
     * @param top отступ сверху
     * @param right отступ справа
     * @param bottom отступ снизу
     */
    public SpaceDecorator(Context cnt, @DimenRes int left, @DimenRes int top, @DimenRes int right, @DimenRes int bottom) {
        mLeft = (int) cnt.getResources().getDimension(left);
        mTop = (int) cnt.getResources().getDimension(top);
        mRight = (int) cnt.getResources().getDimension(right);
        mBottom = (int) cnt.getResources().getDimension(bottom);
    }

    /**
     * @param left отступ слева
     * @param top отступ сверху
     * @param right отступ справа
     * @param bottom отступ снизу
     */
    public SpaceDecorator(int left, int top, int right, int bottom) {
        mLeft = left;
        mTop = top;
        mRight = right;
        mBottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int index = parent.getChildLayoutPosition(view);
        if (index != RecyclerView.NO_POSITION) {
            view.setPadding(
                    mLeft,
                    mTop,
                    mRight,
                    mBottom
            );
        }
    }

}
