# InAppFloatView
无需任何权限随便在应用内弹悬浮窗

## 可以用来干嘛
- 1.悬浮窗
- 2.非侵入式角标
- 3.弹对话框、替代PopupWindow（也是可行的）

## 手把手教您使用
- 1.初始化init(context,isMainProcess,[iSynchronization])
    - isMainProcess：是否在主进程中初始化
    - iSynchronization：跨进程同步数据实现。若要跨进程则可以不传第三个参数iSynchronization。
- 2.附加并显示show(layoutRId,tag,params,multiProcess)
    - layoutRId：layout resource ID
    - tag：浮窗唯一标识
    - params：浮窗LayoutParams
    - multiProcess：是否对跨进程有效
- 3.显示隐藏掉的浮窗show(tag)
- 4.隐藏浮窗hide(tag)
- 5.更新浮窗update(tag,newParams)
- 6.移除浮窗remove(tag)
- 7.获取浮窗getView(tag)
- 8.获取LayoutParams getLayoutParams(tag)
- 9.如何兼容api <=14。在baseActivity中对activity生命周期增加回调InAppFloatView.onActivityXXX方法
- 10.自定义跨进程同步方式。实现ISynchronization接口，在init初始化时传入实现类实例


<img src="GIF.gif">