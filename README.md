# Inappfloatview
[中文](README-chs.md)

Do not require float window permissions apply to floating window controls within the app
## features
- 1.float window
- 2.superscript((non-invasive)
- 3.instead of Dialog or Popupwindow

## how to use
- 1.Initialization .init(Context,isMainprocess,[isynchronization])
	- isMainprocess: Whether to initialize in the main process
	- isynchronization: Synchronize data implementations across processes.

- 2.Attach and display -- show (LayoutRid,tag,params,multiprocess)
    - LayoutRid:layout Resource ID
    - tag: Floating window unique logo
    - Params: Floating window Layoutparams
	- multiprocess: Is it valid for cross-process
- 3.Display hidden floating window-- show(tag)
- 4.Hide Floating window --hide(tag)
- 5.Update Floating window --update(tag,newparams)
- 6.Remove Floating window --remove(tag)
- 7.Get floating window --getView(tag)
- 8.Get layoutparams --getLayoutparams (tag)
- 9.How to compatible with API <=14.Inappfloatview.onactivityxxx method for increasing callbacks to activity lifecycle in baseactivity
- 10.Customize how you synchronize across processes.Implement the Isynchronization interface to pass in an implementation class instance when Init is initialized

<img src="GIF.gif">
