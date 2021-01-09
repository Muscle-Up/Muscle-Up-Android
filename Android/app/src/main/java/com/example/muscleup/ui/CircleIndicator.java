package com.example.muscleup.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CircleIndicator extends LinearLayout {

    private final float temp = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 4.5f, getResources().getDisplayMetrics());

    private Context context;

    private int defaultCircle = 0;
    private int selectCircle = 0;
    private ArrayList<ImageView> circleImage = new ArrayList<>();

    public CircleIndicator(Context context) {
        super(context);
        this.context = context;
    }

    public CircleIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void createIndicator(int count, int defaultCircle, int selectCircle, int position) {
        this.removeAllViews();

        this.defaultCircle = defaultCircle;
        this.selectCircle = selectCircle;

        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setPaddingRelative((int) temp, 0, (int) temp, 0);
            circleImage.add(imageView);

            this.addView(circleImage.get(i));
        }
        select(position);
    }

    public void select(int position) {
        for (int i = 0; i < circleImage.size(); i++) {
            if (i == position) circleImage.get(i).setImageResource(selectCircle);
            else circleImage.get(i).setImageResource(defaultCircle);
        }
    }
}
