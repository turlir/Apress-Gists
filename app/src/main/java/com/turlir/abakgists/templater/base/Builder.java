package com.turlir.abakgists.templater.base;

import android.content.Context;

import com.turlir.abakgists.template.LabeledEditText;
import com.turlir.abakgists.template.VerticalEditText;

import java.util.ArrayList;
import java.util.List;

public class Builder {

    private final List<WidgetHolder> mHolders;
    private final Context mContext;

    public Builder(Context cnt) {
        mContext = cnt;
        mHolders = new ArrayList<>();
    }

    public Builder addField(String label, String content, Interceptor<LabeledEditText> callback) {
        LabeledEditText widget = new LabeledEditText(mContext);
        widget.setTitle(label);
        WidgetHolder<LabeledEditText, String> h = new WidgetHolder<>(
                widget,
                content,
                new Template.NotEmpty(),
                callback
        );
        mHolders.add(h);
        return this;
    }

    public Builder addVerticalField(String label, String content, Interceptor<VerticalEditText> callback) {
        VerticalEditText widget = new VerticalEditText(mContext);
        widget.setTitle(label);
        WidgetHolder<VerticalEditText, String> h = new WidgetHolder<>(
                widget,
                content,
                new Template.MinLimit(5),
                callback
        );
        mHolders.add(h);
        return this;
    }

    public Template build() {
        return new Template(mHolders);
    }
}
