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

    private int mHorizontalPadding;
    private int mVerticalPadding;
    int mHorizontalSpacing;
    int mVerticalSpacing;
    private int  DEFAULT_HORIZONTAL_SPACING=15;
    private  int DEFAULT_VERTICAL_SPACING=15;
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
        } finally {
            a.recycle();
        }
        Log.i( "mHorizontalPadding" , String.valueOf(mHorizontalPadding));
        Log.i( "mVerticalPadding" , String.valueOf(mVerticalPadding));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childLeft = getPaddingLeft();
        int childTop = getPaddingTop();
        List<Integer> list1=new ArrayList<>();
        List<Integer> list2=new ArrayList<>();
        int lineHeight = 0;
        int count1=0;
        // 100 is a dummy number, widthMeasureSpec should always be EXACTLY for FlowLayout
        int myWidth = resolveSize(100, widthMeasureSpec);
        int wantedHeight = 0;
        //Log.i("count", String.valueOf(getChildCount()));
        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;

            }
            // let the child measure itself
            child.measure(
                    getChildMeasureSpec(widthMeasureSpec, 0, child.getLayoutParams().width),
                    getChildMeasureSpec(heightMeasureSpec, 0, child.getLayoutParams().height));
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            System.out.println("childHeight1:"+childHeight);
            // lineheight is the height of current line, should be the height of the heightest view
            lineHeight = Math.max(childHeight, lineHeight);
            Log.i(String.valueOf(i), String.valueOf(lineHeight));
            if (childWidth + childLeft + getPaddingRight() > myWidth) {
                // wrap this line
                childLeft = getPaddingLeft();
                Log.i("Left", String.valueOf(childLeft));

                childTop += mVerticalSpacing + lineHeight;
                list2.add(childTop);

                lineHeight = childHeight;
                System.out.println("childHeight2:"+lineHeight);

                count1++;
            }
            childLeft += childWidth + mHorizontalSpacing;
        }
        wantedHeight += childTop + lineHeight + getPaddingBottom();
        setMeasuredDimension(myWidth, resolveSize(wantedHeight, heightMeasureSpec));
        Log.i("Left", String.valueOf(list1));
        Log.i("Top", String.valueOf(list2));
      //  System.out.println("Left"+list1);
        //System.out.println("Top"+list2);
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childLeft = getPaddingLeft();
        int childTop = getPaddingTop();
        int lineHeight = 0;
        int myWidth = right - left;

        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
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
    }
}