# EasyDialog
## A lightweight, flexible tip dialog in Android

Demo can be download in GooglePlay

项目可在GooglePlay下载

<a href="https://play.google.com/store/apps/details?id=com.michael.easydialogdemo">
  <img alt="Android app on Google Play"
       src="https://developer.android.com/images/brand/en_app_rgb_wo_45.png" />
</a>


![](http://ww3.sinaimg.cn/large/97dd5cddgw1erftuccdgkg20990dw7wh.gif)





A lightweight, flexible tip dialog in Android.You can custom the dialog style easily，set the TipView location, background color,animations,just only one line code!!!
This Project and Demo is open source in github.


你可以只用一行代码就实现提示对话框，包括设置对话框的位置，背景颜色和动画等等。




## How to use（如何使用）

    dependencies {
        compile 'com.github.michaelye.easydialog:easydialog:1.0'
    }
<br/>

     View view = this.getLayoutInflater().inflate(R.layout.layout_tip_content_horizontal, null);
     new EasyDialog(MainActivity.this)
     // .setLayoutResourceId(R.layout.layout_tip_content_horizontal)//layout resource id
        .setLayout(view)
        .setBackgroundColor(MainActivity.this.getResources().getColor(R.color.background_color_black))
     // .setLocation(new location[])//point in screen
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







