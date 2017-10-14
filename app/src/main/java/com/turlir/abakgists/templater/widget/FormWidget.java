package com.turlir.abakgists.templater.widget;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface FormWidget {

    int FIRST = 0, LAST = 1, MIDDLE = 1 << 1;

    void bind(String origin);

    String content();

    void showError(String error);

    void position(@Position int position);

    //

    void setEnabled(boolean state);

    void setVisibility(int visibility);

    @IntDef(flag=true, value={
            FIRST,
            MIDDLE,
            LAST
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface Position {
        //
    }

}
