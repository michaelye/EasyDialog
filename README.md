# EasyDialogDemo
A lightweight, flexible tip dialog in Android

![](http://ww2.sinaimg.cn/large/97dd5cddjw1erfk00pua1g20hs0qou0y.gif)


A lightweight, flexible tip dialog in Android.You can custom the dialog style easily，set the TipView location, background,animations,just only one line code!!!
This Project and Demo is open source in github.

你可以只用一行代码就实现对话框，包括设置对话框的位置，背景和动画等等。项目的源码开源在github

## How to use（如何使用）

    dependencies {
        compile 'com.github.michaelye.easydialog:easydialog:1.0'
    }

    View view = this.getLayoutInflater().inflate(R.layout.layout_tip_content_horizontal, null);
    new EasyDialog(MainActivity.this)
//                        .setLayoutResourceId(R.layout.layout_tip_content_horizontal)//layout resource id
            .setLayout(view)
            .setBackgroundColor(MainActivity.this.getResources().getColor(R.color.background_color_black))
//                        .setLocation(new location[])//point in screen
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







