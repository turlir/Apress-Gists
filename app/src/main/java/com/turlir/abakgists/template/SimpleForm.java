package com.turlir.abakgists.template;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.turlir.abakgists.templater.base.Builder;
import com.turlir.abakgists.templater.base.DynamicForm;
import com.turlir.abakgists.templater.base.Interceptor;
import com.turlir.abakgists.templater.base.Template;

class SimpleForm extends DynamicForm<SimpleForm.ComplexValue> {

    private LabeledEditText mFirst;
    private VerticalEditText mSecond;

    SimpleForm(ViewGroup group) {
        super(group);
    }

    @Override
    protected Template createTemplate() {
        return new Builder(getContext())
                .addField("Label 1", "hint 1", new Interceptor<LabeledEditText, String>() {
                    @Override
                    public String bind() {
                        return value().first;
                    }

                    @Override
                    public void add(LabeledEditText view) {
                        super.add(view);
                        mFirst = view;
                    }
                })
                .addVerticalField("Label 2", "hint 2",
                        new Interceptor<VerticalEditText, String>() {
                            @Override
                            public String bind() {
                                return value().second;
                            }

                            @Override
                            public void add(VerticalEditText view) {
                                super.add(view);
                                mSecond = view;
                            }
                })
                .build();
    }

    @Override
    protected void interact() {
        // dynamic widget usage
    }

    @Override
    @NonNull
    public ComplexValue collect() {
        return new ComplexValue(
                mFirst.et.getText().toString(),
                mSecond.et.getText().toString()
        );
    }

    static class ComplexValue implements Parcelable {

        final String first, second;

        ComplexValue(String first, String second) {
            this.first = first;
            this.second = second;
        }

        ComplexValue(Parcel in) {
            first = in.readString();
            second = in.readString();
        }

        public static final Creator<ComplexValue> CREATOR = new Creator<ComplexValue>() {
            @Override
            public ComplexValue createFromParcel(Parcel in) {
                return new ComplexValue(in);
            }

            @Override
            public ComplexValue[] newArray(int size) {
                return new ComplexValue[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(first);
            parcel.writeString(second);
        }
    }
}
