package com.michael.easydialog;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by michael on 15/4/15.
 */
public class EasyDialog
{
    private Context context;
    /**
     * 内容在三角形上面
     */
    public static final int GRAVITY_TOP = 0;
    /**
     * 内容在三角形下面
     */
    public static final int GRAVITY_BOTTOM = 1;
    /**
     * 内容在三角形左面
     */
    public static final int GRAVITY_LEFT = 2;
    /**
     * 内容在三角形右面
     */
    public static final int GRAVITY_RIGHT = 3;
    /**
     * 对话框本身
     */
    private Dialog dialog;
    /**
     * 坐标
     */
    private int[] location;
    /**
     * 提醒框位置
     */
    private int gravity;
    /**
     * 外面传递进来的View
     */
    private View contentView;
    /**
     * 三角形
     */
    private ImageView ivTriangle;
    /**
     * 用来放外面传递进来的View
     */
    private LinearLayout llContent;
    /**
     * 触摸外面，是否关闭对话框
     */
    private boolean touchOutsideDismiss;
    /**
     * 提示框所在的容器
     */
    private RelativeLayout rlOutsideBackground;

    public EasyDialog(Context context)
    {
        initDialog(context);
    }

    private void initDialog(final Context context)
    {
        this.context = context;
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.layout_dialog, null);
        ViewTreeObserver viewTreeObserver = dialogView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                //当View可以获取宽高的时候，设置view的位置
                relocation(location);

            }
        });
        rlOutsideBackground = (RelativeLayout) dialogView.findViewById(R.id.rlOutsideBackground);
        setTouchOutsideDismiss(true);
        ivTriangle = (ImageView) dialogView.findViewById(R.id.ivTriangle);
        llContent = (LinearLayout) dialogView.findViewById(R.id.llContent);
        dialog = new Dialog(context, isFullScreen() ? android.R.style.Theme_Translucent_NoTitleBar_Fullscreen : android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(dialogView);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener()
        {
            @Override
            public void onDismiss(DialogInterface dialog)
            {
                if(onEasyDialogDismissed != null)
                {
                    onEasyDialogDismissed.onDismissed();
                }
            }
        });
        dialog.setOnShowListener(new DialogInterface.OnShowListener()
        {
            @Override
            public void onShow(DialogInterface dialog)
            {
                if(onEasyDialogShow != null)
                {
                    onEasyDialogShow.onShow();
                }
            }
        });
        animatorSetForDialogShow = new AnimatorSet();
        animatorSetForDialogDismiss = new AnimatorSet();
        objectAnimatorsForDialogShow = new ArrayList<>();
        objectAnimatorsForDialogDismiss = new ArrayList<>();
        ini();
    }

    final View.OnTouchListener outsideBackgroundListener = new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            if (touchOutsideDismiss && dialog != null)
            {
                onDialogDismiss();
            }
            return false;
        }
    };

    /**
     * The Dialog instance
     * */
    public Dialog getDialog()
    {
        return dialog;
    }

    /**
     * 初始化默认值
     */
    private void ini()
    {
        this.setLocation(new int[]{0, 0})
                .setGravity(GRAVITY_BOTTOM)
                .setTouchOutsideDismiss(true)
                .setOutsideColor(Color.TRANSPARENT)
                .setBackgroundColor(Color.BLUE)
                .setMatchParent(true)
                .setMarginLeftAndRight(24, 24);
    }

    /**
     * 设置提示框中要显示的内容
     */
    public EasyDialog setLayout(View layout)
    {
        if (layout != null)
        {
            this.contentView = layout;
        }
        return this;
    }

    /**
     * 设置提示框中要显示的内容的布局Id
     */
    public EasyDialog setLayoutResourceId(int layoutResourceId)
    {
        View view = ((Activity) context).getLayoutInflater().inflate(layoutResourceId, null);
        setLayout(view);
        return this;
    }

    /**
     * 设置三角形所在的位置
     */
    public EasyDialog setLocation(int[] location)
    {
        this.location = location;
        return this;
    }

    /**
     * 设置三角形所在的位置
     * location.x坐标值为attachedView所在屏幕位置的中心
     * location.y坐标值依据当前的gravity，如果gravity是top，则为控件上方的y值，如果是bottom，则为控件的下方的y值
     *
     * @param attachedView 在哪个View显示提示信息
     */
    public EasyDialog setLocationByAttachedView(View attachedView)
    {
        if (attachedView != null)
        {
            this.attachedView = attachedView;
            int[] attachedViewLocation = new int[2];
            attachedView.getLocationOnScreen(attachedViewLocation);
            switch (gravity)
            {
                case GRAVITY_BOTTOM:
                    attachedViewLocation[0] += attachedView.getWidth() / 2;
                    attachedViewLocation[1] += attachedView.getHeight();
                    break;
                case GRAVITY_TOP:
                    attachedViewLocation[0] += attachedView.getWidth() / 2;
                    break;
                case GRAVITY_LEFT:
                    attachedViewLocation[1] += attachedView.getHeight() / 2;
                    break;
                case GRAVITY_RIGHT:
                    attachedViewLocation[0] += attachedView.getWidth();
                    attachedViewLocation[1] += attachedView.getHeight() / 2;
            }
            setLocation(attachedViewLocation);
        }
        return this;
    }

    /**
     * 对话框所依附的View
     */
    private View attachedView = null;

    public View getAttachedView()
    {
        return this.attachedView;
    }

    /**
     * 设置显示的内容在上方还是下方，如果设置错误，默认是在下方
     */
    public EasyDialog setGravity(int gravity)
    {
        if (gravity != GRAVITY_BOTTOM && gravity != GRAVITY_TOP && gravity != GRAVITY_LEFT && gravity != GRAVITY_RIGHT)
        {
            gravity = GRAVITY_BOTTOM;
        }
        this.gravity = gravity;
        switch (this.gravity)
        {
            case GRAVITY_BOTTOM:
                ivTriangle.setBackgroundResource(R.drawable.triangle_bottom);
                break;
            case GRAVITY_TOP:
                ivTriangle.setBackgroundResource(R.drawable.triangle_top);
                break;
            case GRAVITY_LEFT:
                ivTriangle.setBackgroundResource(R.drawable.triangle_left);
                break;
            case GRAVITY_RIGHT:
                ivTriangle.setBackgroundResource(R.drawable.triangle_right);
                break;
        }
        llContent.setBackgroundResource(R.drawable.round_corner_bg);
        if (attachedView != null)//如果用户调用setGravity()之前就调用过setLocationByAttachedView，需要再调用一次setLocationByAttachedView
        {
            this.setLocationByAttachedView(attachedView);
        }
        this.setBackgroundColor(backgroundColor);
        return this;
    }

    /**
     * 设置是否填充屏幕，如果不填充就适应布局内容的宽度，显示内容的位置会尽量随着三角形的位置居中
     */
    public EasyDialog setMatchParent(boolean matchParent)
    {
        ViewGroup.LayoutParams layoutParams = llContent.getLayoutParams();
        layoutParams.width = matchParent ? ViewGroup.LayoutParams.MATCH_PARENT : ViewGroup.LayoutParams.WRAP_CONTENT;
        llContent.setLayoutParams(layoutParams);
        return this;
    }

    /**
     * 距离屏幕左右的边距
     */
    public EasyDialog setMarginLeftAndRight(int left, int right)
    {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) llContent.getLayoutParams();
        layoutParams.setMargins(left, 0, right, 0);
        llContent.setLayoutParams(layoutParams);
        return this;
    }

    public EasyDialog setMarginTopAndBottom(int top, int bottom)
    {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) llContent.getLayoutParams();
        layoutParams.setMargins(0, top, 0, bottom);
        llContent.setLayoutParams(layoutParams);
        return this;
    }

    /**
     * 设置触摸对话框外面，对话框是否消失
     */
    public EasyDialog setTouchOutsideDismiss(boolean touchOutsideDismiss)
    {
        this.touchOutsideDismiss = touchOutsideDismiss;
        if(touchOutsideDismiss)
        {
            rlOutsideBackground.setOnTouchListener(outsideBackgroundListener);
        }
        else
        {
            rlOutsideBackground.setOnTouchListener(null);
        }
        return this;
    }

    /**
     * 设置提醒框外部区域的颜色
     */
    public EasyDialog setOutsideColor(int color)
    {
        rlOutsideBackground.setBackgroundColor(color);
        return this;
    }

    private int backgroundColor;

    /**
     * 设置对话框的颜色
     * 三角形的图片是layer-list里面嵌套一个RotateDrawable，在设置颜色的时候需要特别处理
     * http://stackoverflow.com/questions/24492000/set-color-of-triangle-on-run-time
     * http://stackoverflow.com/questions/16636412/change-shape-solid-color-at-runtime-inside-drawable-xml-used-as-background
     */
    public EasyDialog setBackgroundColor(int color)
    {
        backgroundColor = color;
        LayerDrawable drawableTriangle = (LayerDrawable) ivTriangle.getBackground();
        GradientDrawable shapeTriangle = (GradientDrawable) (((RotateDrawable) drawableTriangle.findDrawableByLayerId(R.id.shape_id)).getDrawable());
        if (shapeTriangle != null)
        {
            shapeTriangle.setColor(color);
        } else
        {
            Toast.makeText(context, "shape is null", Toast.LENGTH_SHORT).show();
        }
        GradientDrawable drawableRound = (GradientDrawable) llContent.getBackground();
        if (drawableRound != null)
        {
            drawableRound.setColor(color);
        }
        return this;
    }

    /**
     * 显示提示框
     */
    public EasyDialog show()
    {
        if (dialog != null)
        {
            if (contentView == null)
            {
                throw new RuntimeException("您是否未调用setLayout()或者setLayoutResourceId()方法来设置要显示的内容呢？");
            }
            if(llContent.getChildCount() > 0)
            {
                llContent.removeAllViews();
            }
            llContent.addView(contentView);
            dialog.show();
            onDialogShowing();
        }
        return this;
    }

    /**
     * 显示对话框的View的parent，如果想自己写动画，可以获取这个实例来写动画
     */
    public View getTipViewInstance()
    {
        return rlOutsideBackground.findViewById(R.id.rlParentForAnimate);
    }

    /**
     * 横向
     */
    public static final int DIRECTION_X = 0;
    /**
     * 纵向
     */
    public static final int DIRECTION_Y = 1;


    /**
     * 水平动画
     *
     * @param direction 动画的方向
     * @param duration  动画执行的时间长度
     * @param values    动画移动的位置
     */
    public EasyDialog setAnimationTranslationShow(int direction, int duration, float... values)
    {
        return setAnimationTranslation(true, direction, duration, values);
    }

    /**
     * 水平动画
     *
     * @param direction 动画的方向
     * @param duration  动画执行的时间长度
     * @param values    动画移动的位置
     */
    public EasyDialog setAnimationTranslationDismiss(int direction, int duration, float... values)
    {
        return setAnimationTranslation(false, direction, duration, values);
    }

    private EasyDialog setAnimationTranslation(boolean isShow, int direction, int duration, float... values)
    {
        if (direction != DIRECTION_X && direction != DIRECTION_Y)
        {
            direction = DIRECTION_X;
        }
        String propertyName = "";
        switch (direction)
        {
            case DIRECTION_X:
                propertyName = "translationX";
                break;
            case DIRECTION_Y:
                propertyName = "translationY";
                break;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(rlOutsideBackground.findViewById(R.id.rlParentForAnimate), propertyName, values)
                .setDuration(duration);
        if (isShow)
        {
            objectAnimatorsForDialogShow.add(animator);
        } else
        {
            objectAnimatorsForDialogDismiss.add(animator);
        }
        return this;
    }

    /**
     * 对话框出现时候的渐变动画
     *
     * @param duration 动画执行的时间长度
     * @param values   动画移动的位置
     */
    public EasyDialog setAnimationAlphaShow(int duration, float... values)
    {
        return setAnimationAlpha(true, duration, values);
    }

    /**
     * 对话框消失时候的渐变动画
     *
     * @param duration 动画执行的时间长度
     * @param values   动画移动的位置
     */
    public EasyDialog setAnimationAlphaDismiss(int duration, float... values)
    {
        return setAnimationAlpha(false, duration, values);
    }

    private EasyDialog setAnimationAlpha(boolean isShow, int duration, float... values)
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(rlOutsideBackground.findViewById(R.id.rlParentForAnimate), "alpha", values).setDuration(duration);
        if (isShow)
        {
            objectAnimatorsForDialogShow.add(animator);
        } else
        {
            objectAnimatorsForDialogDismiss.add(animator);
        }
        return this;
    }

    private AnimatorSet animatorSetForDialogShow;
    private AnimatorSet animatorSetForDialogDismiss;
    private List<Animator> objectAnimatorsForDialogShow;
    private List<Animator> objectAnimatorsForDialogDismiss;


    private void onDialogShowing()
    {
        if (animatorSetForDialogShow != null && objectAnimatorsForDialogShow != null && objectAnimatorsForDialogShow.size() > 0)
        {
            animatorSetForDialogShow.playTogether(objectAnimatorsForDialogShow);
            animatorSetForDialogShow.start();
        }
        //TODO 缩放的动画效果不好，不能从控件所在的位置开始缩放
//        ObjectAnimator.ofFloat(rlOutsideBackground.findViewById(R.id.rlParentForAnimate), "scaleX", 0.3f, 1.0f).setDuration(500).start();
//        ObjectAnimator.ofFloat(rlOutsideBackground.findViewById(R.id.rlParentForAnimate), "scaleY", 0.3f, 1.0f).setDuration(500).start();
    }

    @SuppressLint("NewApi")
    private void onDialogDismiss()
    {
        if (animatorSetForDialogDismiss.isRunning())
        {
            return;
        }
        if (animatorSetForDialogDismiss != null && objectAnimatorsForDialogDismiss != null && objectAnimatorsForDialogDismiss.size() > 0)
        {
            animatorSetForDialogDismiss.playTogether(objectAnimatorsForDialogDismiss);
            animatorSetForDialogDismiss.start();
            animatorSetForDialogDismiss.addListener(new Animator.AnimatorListener()
            {
                @Override
                public void onAnimationStart(Animator animation)
                {

                }

                @Override
                public void onAnimationEnd(Animator animation)
                {
                    //这里有可能会有bug，当Dialog所依赖的Activity关闭的时候，如果这个时候，用户关闭对话框，由于对话框的动画关闭需要时间，当动画执行完毕后，对话框所依赖的Activity已经被销毁了，执行dismiss()就会报错
                    if (context != null && context instanceof Activity)
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                        {
                            if (!((Activity) context).isDestroyed())
                            {
                                dialog.dismiss();
                            }
                        } else
                        {
                            try
                            {
                                dialog.dismiss();
                            } catch (final IllegalArgumentException e)
                            {

                            } catch (final Exception e)
                            {

                            } finally
                            {
                                dialog = null;
                            }
                        }
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation)
                {

                }

                @Override
                public void onAnimationRepeat(Animator animation)
                {

                }
            });
        } else
        {
            dialog.dismiss();
        }
    }

    /**
     * 关闭提示框
     */
    public void dismiss()
    {
        if (dialog != null && dialog.isShowing())
        {
            onDialogDismiss();
        }
    }

    /**
     * 根据x，y，重新设置控件的位置
     * 因为setX setY为0的时候，都是在状态栏以下的，所以app不是全屏的话，需要扣掉状态栏的高度
     */
    private void relocation(int[] location)
    {
        float statusBarHeight = isFullScreen() ? 0.0f : getStatusBarHeight();

        ivTriangle.setX(location[0] - ivTriangle.getWidth() / 2);
        ivTriangle.setY(location[1] - ivTriangle.getHeight() / 2 - statusBarHeight);
        switch (gravity)
        {
            case GRAVITY_BOTTOM:
                llContent.setY(location[1] - ivTriangle.getHeight() / 2 - statusBarHeight + ivTriangle.getHeight());
                break;
            case GRAVITY_TOP:
                llContent.setY(location[1] - llContent.getHeight() - statusBarHeight - ivTriangle.getHeight() / 2);
                break;
            case GRAVITY_LEFT:
                llContent.setX(location[0] - llContent.getWidth() - ivTriangle.getWidth() / 2);
                break;
            case GRAVITY_RIGHT:
                llContent.setX(location[0] + ivTriangle.getWidth() / 2);
                break;
        }

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) llContent.getLayoutParams();
        switch (gravity)
        {
            case GRAVITY_BOTTOM:
            case GRAVITY_TOP:
                int triangleCenterX = (int) (ivTriangle.getX() + ivTriangle.getWidth() / 2);
                int contentWidth = llContent.getWidth();
                int rightMargin = getScreenWidth() - triangleCenterX;
                int leftMargin = getScreenWidth() - rightMargin;
                int availableLeftMargin = leftMargin - layoutParams.leftMargin;
                int availableRightMargin = rightMargin - layoutParams.rightMargin;
                int x = 0;
                if (contentWidth / 2 <= availableLeftMargin && contentWidth / 2 <= availableRightMargin)
                {
                    x = triangleCenterX - contentWidth / 2;
                } else
                {
                    if (availableLeftMargin <= availableRightMargin)
                    {
                        x = layoutParams.leftMargin;
                    } else
                    {
                        x = getScreenWidth() - (contentWidth + layoutParams.rightMargin);
                    }
                }
                llContent.setX(x);
                break;
            case GRAVITY_LEFT:
            case GRAVITY_RIGHT:
                int triangleCenterY = (int) (ivTriangle.getY() + ivTriangle.getHeight() / 2);
                int contentHeight = llContent.getHeight();
                int topMargin = triangleCenterY;
                int bottomMargin = getScreenHeight() - topMargin;
                int availableTopMargin = topMargin - layoutParams.topMargin;
                int availableBottomMargin = bottomMargin - layoutParams.bottomMargin;
                int y = 0;
                if (contentHeight / 2 <= availableTopMargin && contentHeight / 2 <= availableBottomMargin)
                {
                    y = triangleCenterY - contentHeight / 2;
                } else
                {
                    if (availableTopMargin <= availableBottomMargin)
                    {
                        y = layoutParams.topMargin;
                    } else
                    {
                        y = getScreenHeight() - (contentHeight + layoutParams.topMargin);
                    }
                }
                llContent.setY(y);
                break;
        }
    }

    /**
     * 获取屏幕的宽度
     */
    private int getScreenWidth()
    {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    private int getScreenHeight()
    {
        int statusBarHeight = isFullScreen() ? 0 : getStatusBarHeight();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.heightPixels - statusBarHeight;
    }

    /**
     * 获取状态栏的高度
     */
    private int getStatusBarHeight()
    {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 判断下当前要显示对话框的Activity是否是全屏
     */
    public boolean isFullScreen()
    {
        int flg = ((Activity) context).getWindow().getAttributes().flags;
        boolean flag = false;
        if ((flg & 1024) == 1024)
        {
            flag = true;
        }
        return flag;
    }

    /**
     * 设置是否可以按返回按钮取消
     */
    public EasyDialog setCancelable(boolean cancelable)
    {
        dialog.setCancelable(cancelable);
        return this;
    }

    private OnEasyDialogDismissed onEasyDialogDismissed;

    public EasyDialog setOnEasyDialogDismissed(OnEasyDialogDismissed onEasyDialogDismissed)
    {
        this.onEasyDialogDismissed = onEasyDialogDismissed;
        return this;
    }

    /**
     * Dialog is dismissed
     * */
    public interface OnEasyDialogDismissed
    {
        public void onDismissed();
    }

    private OnEasyDialogShow onEasyDialogShow;

    public EasyDialog setOnEasyDialogShow(OnEasyDialogShow onEasyDialogShow)
    {
        this.onEasyDialogShow = onEasyDialogShow;
        return this;
    }

    /**
     * Dialog is showing
     * */
    public interface OnEasyDialogShow
    {
        public void onShow();
    }
}
