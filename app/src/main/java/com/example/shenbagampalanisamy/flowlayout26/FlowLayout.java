package com.example.shenbagampalanisamy.flowlayout26;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
public class FlowLayout extends ViewGroup {
    View child;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private int orientation = FlowLayout.HORIZONTAL;
    public static final int LAYOUT_DIRECTION_LTR = 0;
    public static final int LAYOUT_DIRECTION_RTL = 1;
    private int mHorizontalPadding;
    private int mVerticalPadding;
    int mHorizontalSpacing;
    int mVerticalSpacing;
    private int DEFAULT_HORIZONTAL_SPACING = 15;
    private int DEFAULT_VERTICAL_SPACING = 15;


    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        try {
            mHorizontalPadding = a.getDimensionPixelSize(R.styleable.FlowLayout_FlowLayout_horizontalPadding, 0);
            mVerticalPadding = a.getDimensionPixelSize(R.styleable.FlowLayout_FlowLayout_verticalPadding, 0);
            mHorizontalSpacing = a.getDimensionPixelSize(
                    R.styleable.FlowLayout_FlowLayout_horizontal_spacing, DEFAULT_HORIZONTAL_SPACING);
            mVerticalSpacing = a.getDimensionPixelSize(
                    R.styleable.FlowLayout_FlowLayout_vertical_spacing, DEFAULT_VERTICAL_SPACING);

            this.setOrientation(a.getInteger(R.styleable.FlowLayout_android_orientation, FlowLayout.HORIZONTAL));

        } finally {
            a.recycle();
        }
    }

    public void setOrientation(int orientation) {
        if (orientation == FlowLayout.VERTICAL) {
            this.orientation = orientation;
        } else {
            this.orientation = FlowLayout.HORIZONTAL;
        }
    }

    public int getOrientation() {
        return orientation;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int val = this.getOrientation();
        int val_h = FlowLayout.HORIZONTAL;
        final int sizeHeight = MeasureSpec.getSize(heightMeasureSpec) - this.getPaddingTop() - this.getPaddingBottom();
        Log.i("size", String.valueOf(sizeHeight));
        //For Horizontal
        int childLeft = getPaddingLeft();

        int childTop = getPaddingTop();
        int lineHeight = 0;
        int myWidth = resolveSize(100, widthMeasureSpec);
        int wantedHeight = 0;

        //For Vertical
        int childRight = getPaddingRight();
        int childBottom = getPaddingBottom();
        int lineWidth = 0;
        int myHeight = resolveSize(100, heightMeasureSpec);
        int wantedWidth = 0;


        if (val == val_h) {
            for (int i = 0; i < getChildCount(); i++) {
                final View child = getChildAt(i);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                child.measure(
                        getChildMeasureSpec(widthMeasureSpec, 0, child.getLayoutParams().width),
                        getChildMeasureSpec(heightMeasureSpec, 0, child.getLayoutParams().height));
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                Log.i("chileLeft", String.valueOf(childWidth));
                lineHeight = Math.max(childHeight, lineHeight);
                if (childWidth + childLeft + getPaddingRight() > myWidth) {
                    // wrap this line
                    childLeft = getPaddingLeft();
                    childTop += mVerticalSpacing + lineHeight;
                    lineHeight = childHeight;
                }
                childLeft += childWidth + mHorizontalSpacing;
            }
            wantedHeight += childTop + lineHeight + getPaddingBottom();
            setMeasuredDimension(myWidth, resolveSize(wantedHeight, heightMeasureSpec));
            int a=resolveSize(wantedHeight, heightMeasureSpec);
            System.out.println(a);
            //Log.i("hi", String.valueOf(a));
        } else {

            for (int i = 0; i < getChildCount(); i++) {
                final View child = getChildAt(i);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                child.measure(
                        getChildMeasureSpec(widthMeasureSpec, 0, child.getLayoutParams().width),
                        getChildMeasureSpec(heightMeasureSpec, 0, child.getLayoutParams().height));
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();

                lineWidth = Math.max(childWidth, lineWidth);

                if ( childHeight +childTop + getPaddingBottom() >myHeight) {
                    childTop=getPaddingTop();
                    childLeft+= mHorizontalSpacing+lineWidth;
                    lineWidth=childWidth;

                }
                childLeft += childWidth + mHorizontalSpacing;
            }
            wantedWidth+= childLeft + lineWidth + getPaddingRight();
            setMeasuredDimension(resolveSize(wantedWidth, widthMeasureSpec),myHeight );
        }
    }

        @Override
        protected void onLayout ( boolean changed, int left, int top, int right, int bottom){
            int childLeft = getPaddingLeft();
            int childTop = getPaddingTop();
            int lineHeight = 0;
            int myWidth = right - left;
            int myHeight=bottom-top;
            int lineWidth=0;

            int orient = this.getOrientation();
            int hori = FlowLayout.HORIZONTAL;

            if (orient == hori) {
                for (int i = 0; i < getChildCount(); i++) {
                    child = getChildAt(i);
                    if (child.getVisibility() == View.GONE) {
                        continue;
                    }
                    int childWidth = child.getMeasuredWidth();
                    int childHeight = child.getMeasuredHeight();
                    lineHeight = Math.max(childHeight, lineHeight);
                    if (childWidth + childLeft + getPaddingRight() > myWidth) {
                        childLeft = getPaddingLeft();
                        childTop += mVerticalSpacing + lineHeight;
                        lineHeight = childHeight;
                    }
                    child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
                    childLeft += childWidth + mHorizontalSpacing;
                }
            } else {
                for (int i = 0; i < getChildCount(); i++) {
                    child = getChildAt(i);
                    if (child.getVisibility() == View.GONE) {
                        continue;
                    }
                    int childWidth = child.getMeasuredWidth();
                    int childHeight = child.getMeasuredHeight();

                    lineWidth = Math.max(childWidth, lineWidth);
                    if (childHeight + childTop + getPaddingBottom() > myHeight) {
                        childTop=getPaddingTop();
                        childLeft+= mHorizontalSpacing+lineWidth;
                        lineWidth=childWidth;
                    }

                    child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
                    childTop += childHeight + mVerticalSpacing;
                }
            }

        }



    }


