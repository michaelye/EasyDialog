package com.michael.easydialogdemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.michael.easydialog.EasyDialog;


/**
 * This class shows how to use YNM3KDialog
 *
 * Created by michael on 15/4/15.
 * */
public class MainActivity extends ActionBarActivity implements View.OnClickListener
{
    private RelativeLayout rlBackground;
    private Button btnTopLeft;
    private Button btnTopRight;
    private Button btnMiddleTop;
    private Button btnMiddleBottom;
    private Button btnBottomLeft;
    private Button btnBottomRight;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniComponent();
    }

    private void iniComponent()
    {
        rlBackground = (RelativeLayout)findViewById(R.id.rlBackground);
        btnTopLeft = (Button) findViewById(R.id.btnTopLeft);
        btnTopRight = (Button) findViewById(R.id.btnTopRight);
        btnMiddleTop = (Button) findViewById(R.id.btnMiddleTop);
        btnMiddleBottom = (Button) findViewById(R.id.btnMiddleBottom);
        btnBottomLeft = (Button) findViewById(R.id.btnBottomLeft);
        btnBottomRight = (Button) findViewById(R.id.btnBottomRight);

        btnTopLeft.setOnClickListener(this);
        btnTopRight.setOnClickListener(this);
        btnMiddleTop.setOnClickListener(this);
        btnMiddleBottom.setOnClickListener(this);
        btnBottomLeft.setOnClickListener(this);
        btnBottomRight.setOnClickListener(this);
        rlBackground.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                int[] location = new int[2];
                location[0] = (int)event.getX();
                location[1] = (int)event.getY();
                location[1] = location[1] + getActionBarHeight() + getStatusBarHeight();
                Toast.makeText(MainActivity.this, "x:" + location[0] + " y:" + location[1], Toast.LENGTH_SHORT).show();
                new EasyDialog(MainActivity.this)
                        .setLayoutResourceId(R.layout.layout_tip_content_horizontal)
                        .setBackgroundColor(MainActivity.this.getResources().getColor(R.color.background_color_black))
                        .setLocation(location)
                        .setGravity(EasyDialog.GRAVITY_TOP)
                        .setTouchOutsideDismiss(true)
                        .setMatchParent(false)
                        .setMarginLeftAndRight(24, 24)
                        .setOutsideColor(MainActivity.this.getResources().getColor(R.color.outside_color_gray))
                        .show();

                return false;
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnTopLeft:
                View view = this.getLayoutInflater().inflate(R.layout.layout_tip_content_horizontal, null);
                new EasyDialog(MainActivity.this)
                        .setLayout(view)
                        .setBackgroundColor(MainActivity.this.getResources().getColor(R.color.background_color_black))
                        .setLocationByAttachedView(btnTopLeft)
                        .setGravity(EasyDialog.GRAVITY_BOTTOM)
                        .setAnimationTranslationShow(EasyDialog.DIRECTION_X, 1000, -600, 100, -50, 50, 0)
                        .setAnimationAlphaShow(1000, 0.3f, 1.0f)
                        .setAnimationTranslationDismiss(EasyDialog.DIRECTION_X, 500, -50, 800)
                        .setAnimationAlphaDismiss(500, 1.0f, 0.0f)
                        .setTouchOutsideDismiss(true)
                        .setMatchParent(true)
                        .setMarginLeftAndRight(24, 24)
                        .setOutsideColor(MainActivity.this.getResources().getColor(R.color.outside_color_trans))
                        .show();
                break;

            case R.id.btnTopRight:
                new EasyDialog(MainActivity.this)
                        .setLayoutResourceId(R.layout.layout_tip_image_text)
                        .setGravity(EasyDialog.GRAVITY_BOTTOM)
                        .setBackgroundColor(MainActivity.this.getResources().getColor(R.color.background_color_black))
                        .setLocationByAttachedView(btnTopRight)
                        .setAnimationTranslationShow(EasyDialog.DIRECTION_X, 350, 400, 0)
                        .setAnimationTranslationDismiss(EasyDialog.DIRECTION_X, 350, 0, 400)
                        .setTouchOutsideDismiss(true)
                        .setMatchParent(false)
                        .setMarginLeftAndRight(24, 24)
                        .setOutsideColor(MainActivity.this.getResources().getColor(R.color.outside_color_trans))
                        .show();
                break;
            case R.id.btnMiddleTop:
                new EasyDialog(MainActivity.this)
                        .setLayoutResourceId(R.layout.layout_tip_content_horizontal)
                        .setBackgroundColor(MainActivity.this.getResources().getColor(R.color.background_color_blue))
                        .setLocationByAttachedView(btnMiddleTop)
                        .setAnimationTranslationShow(EasyDialog.DIRECTION_Y, 1000, -800, 100, -50, 50, 0)
                        .setAnimationTranslationDismiss(EasyDialog.DIRECTION_Y, 500, 0, -800)
                        .setGravity(EasyDialog.GRAVITY_TOP)
                        .setTouchOutsideDismiss(true)
                        .setMatchParent(false)
                        .setMarginLeftAndRight(24, 24)
                        .setOutsideColor(MainActivity.this.getResources().getColor(R.color.outside_color_pink))
                        .show();
                break;
            case R.id.btnMiddleBottom:
                new EasyDialog(MainActivity.this)
                        .setLayoutResourceId(R.layout.layout_tip_content_horizontal)
                        .setGravity(EasyDialog.GRAVITY_BOTTOM)
                        .setBackgroundColor(MainActivity.this.getResources().getColor(R.color.background_color_brown))
                        .setLocationByAttachedView(btnMiddleBottom)
                        .setAnimationTranslationShow(EasyDialog.DIRECTION_Y, 1000, 800, -100, -50, 50, 0)
                        .setAnimationTranslationDismiss(EasyDialog.DIRECTION_Y, 500, 0, 800)
                        .setAnimationAlphaShow(1000, 0.3f, 1.0f)
                        .setTouchOutsideDismiss(true)
                        .setMatchParent(true)
                        .setMarginLeftAndRight(24, 24)
                        .setOutsideColor(MainActivity.this.getResources().getColor(R.color.outside_color_gray))
                        .show();
                break;
            case R.id.btnBottomLeft:
                new EasyDialog(MainActivity.this)
                        .setLayoutResourceId(R.layout.layout_tip_text)
                        .setBackgroundColor(MainActivity.this.getResources().getColor(R.color.background_color_pink))
                        .setLocationByAttachedView(btnBottomLeft)
                        .setGravity(EasyDialog.GRAVITY_TOP)
                        .setAnimationAlphaShow(600, 0.0f, 1.0f)
                        .setAnimationAlphaDismiss(600, 1.0f, 0.0f)
                        .setTouchOutsideDismiss(true)
                        .setMatchParent(false)
                        .setMarginLeftAndRight(24, 24)
                        .setOutsideColor(MainActivity.this.getResources().getColor(R.color.outside_color_trans))
                        .show();
                break;
            case R.id.btnBottomRight:
                new EasyDialog(MainActivity.this)
                        .setLayoutResourceId(R.layout.layout_tip_image_text)
                        .setBackgroundColor(MainActivity.this.getResources().getColor(R.color.background_color_yellow))
                        .setLocationByAttachedView(btnBottomRight)
                        .setGravity(EasyDialog.GRAVITY_TOP)
                        .setAnimationTranslationShow(EasyDialog.DIRECTION_X, 300, 400, 0)
                        .setAnimationTranslationShow(EasyDialog.DIRECTION_Y, 300, 400, 0)
                        .setAnimationTranslationDismiss(EasyDialog.DIRECTION_X, 300, 0, 400)
                        .setAnimationTranslationDismiss(EasyDialog.DIRECTION_Y, 300, 0, 400)
                        .setTouchOutsideDismiss(true)
                        .setMatchParent(false)
                        .setMarginLeftAndRight(24, 24)
                        .setOutsideColor(MainActivity.this.getResources().getColor(R.color.outside_color_trans))
                        .show();
                break;
        }
    }

    private int getStatusBarHeight()
    {
        int result = 0;
        int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = this.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private int getActionBarHeight()
    {
        return this.getSupportActionBar().getHeight();
    }
}

