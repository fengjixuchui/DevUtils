Change Log
==========

Version 1.9.7 *(2020-08-04)*
----------------------------

* `[Add]` ChineseUtils 中文工具类

* `[Add]` QuickHelper 简化链式设置 View Helper 类

* `[Add]` StringUtils#forString

* `[Delete]` PhoneUtils 双卡模块代码

* `[Delete]` AsyncExecutor 异步执行辅助类

Version 1.9.6 *(2020-07-19)*
----------------------------

* `[Add]` ViewHelper#setPaintFlags、setAntiAliasFlag

* `[Add]` ViewUtils#setPaintFlags、setAntiAliasFlag

* `[Add]` DevLogger#setPrint、LogPrintUtils#setPrint 自定义输出接口

* `[Update]` ClickUtils 变量声明顺序

Version 1.9.5 *(2020-06-08)*
----------------------------

* `[Update]` PermissionUtils#isGranted

* `[Add]` ResourceUtils#getDimensionInt

Version 1.9.4 *(2020-05-18)*
----------------------------

* `[Update]` MediaStoreUtils#notifyMediaStore 通知刷新本地资源方法版本处理

* `[Update]` ClassUtils#getGenericSuperclass、getGenericInterfaces 返回类型

Version 1.9.3 *(2020-04-25)*
----------------------------

* `[Add]` LanguageUtils#isEn、isZh、isZhCN、isZhTW、isLanguage、isRegion、getSystemCountry

* `[Add]` DeviceUtils#isTablet

* `[Add]` ScreenUtils#isFullScreen、setFullScreenNoTitle

* `[Update]` BarUtils

* `[Delete]` OSUtils

* `[Add]` ROMUtils

Version 1.9.2 *(2020-03-19)*
----------------------------

* `[Add]` WidgetUtils 控件工具类

* `[Add]` ViewUtils#getClipChildren、setClipChildren、getContentView、getRootParent

* `[Add]` ViewHelper#setClipChildren

* `[Add]` TextViewUtils#reckonTextSizeByWidth

* `[Update]` TextViewUtils#reckonTextSize 方法名为 TextViewUtils#reckonTextSizeByHeight

Version 1.9.1 *(2020-03-11)*
----------------------------

* `[Add]` ViewUtils#isShown、isShowns

* `[Add]` StringUtils#split

* `[Add]` NumberUtils#calculateUnit

* `[Update]` StringUtils#replaceStr、replaceStrToNull 方法名为 StringUtils#replaceAll、replaceAllToNull

* `[Update]` TimerManager#startTimer、closeTimer 返回值为 AbsTimer

Version 1.9.0 *(2020-02-21)*
----------------------------

* `[Add]` StringUtils#getBytes

* `[Add]` FileIOUtils#getFileInputStream、getFileOutputStream

* `[Update]` FileUtils#saveFile、appendFile

* `[Update]` FileRecordUtils、AnalysisRecordUtils 关联引用 saveFile、appendFile 方法处理

Version 1.8.9 *(2020-01-26)*
----------------------------

* `[Add]` TypeUtils 类型工具类

* `[Add]` ClassUtils#getClass、isGenericParamType、getGenericParamType

* `[Add]` ConvertUtils#toBigDecimal、toBigInteger、newString、charAt

* `[Update]` ConvertUtils#toString、toInt、toBoolean、toFloat、toDouble、toLong、toShort、toChar、toByte、toChars、toBytes

Version 1.8.8 *(2020-01-16)*
----------------------------

* `[Add]` BitmapUtils#calculateQuality 计算最佳压缩质量值方法

* `[Add]` FileUtils#listFilesInDirBean、listFilesInDirWithFilterBean 方法, 获取文件目录列表集合 FileList

* `[Fix]` 修复 AppUtils#isInstalledApp 判断是否安装错误情况

* `[Update]` 兼容 Android P 获取 versionCode 处理 ( getLongVersionCode )

Version 1.8.7 *(2020-01-07)*
----------------------------

* `[Update]` PermissionUtils#shouldShowRequestPermissionRationale 方法, 增加可变数组权限传入

* `[Add]` PermissionUtils#getDeniedPermissionStatus 获取拒绝权限询问状态集合方法、PermissionUtils#againRequest 处理拒绝权限操作方法

* `[Update]` ActivityUtils#appExit 为 exitApplication

* `[Add]` ActivityUtils#startActivityForResult 跳转方法, 支持通过接口回调方式通知

Version 1.8.6 *(2019-12-25)*
----------------------------

* `[Feature]` 适配 Android Q 并重构 PathUtils 工具类, 提供适配思路以及增加 MediaStoreUtils 多媒体工具类用于外部存储适配操作

* `[Add]` ViewUtils#toggleFocusable、toggleSelected、toggleEnabled、toggleClickable、toggleLongClickable、getChilds

* `[Add]` AppCommonUtils#getUUIDDevice、NotificationUtils#checkAndIntentSetting、isNotificationListenerEnabled、startNotificationListenSettings

* `[Add]` UriUtils#isUriExists、IntentUtils#getLaunchAppNotificationListenSettingsIntent、getOpenBrowserIntent、getCreateDocumentIntent、getOpenDocumentIntent

* `[Add]` CrashUtils UncaughtException 处理工具类、MediaStoreUtils 多媒体工具类

* `[Change]` 移动 ImageViewUtils 部分方法到 ViewUtils、更新 ContentResolverUtils 工具类代码, 拆分到 UriUtils、MediaStoreUtils

* `[Add]` ColorUtils#getARGB、grayLevel、sortGray、sortHSB 并增加内部类 ColorInfo, 支持颜色排序

* `[Add]` FileIOUtils#copyLarge、DateUtils#yyyyMMdd_HHmmss、CoordinateUtils#getDistance、getAngle、getDirection

* `[Add]` DevCommonUtils、StringUtils#appendsIgnoreLast

* `[Update]` 删除 DevCommonUtils、StringUtils 几个重载方法 appends

* `[Update]` 更新部分工具类、方法注释代码、代码间距等

Version 1.8.5 *(2019-11-25)*
----------------------------

* `[Refactor]` 重构整个项目, 优化代码逻辑判断、代码风格、合并工具类减少包大小等, 并修改 95% 返回值 void 的方法为 boolean 明确获取调用结果
 
* `[Add]` JSONObjectUtils#isJSONObject、isJSONArray、jsonToMap、jsonToList、getJSONObject、getJSONArray、get、opt

* `[Add]` AppCommonUtils#getAppDeviceInfo、refreshAppDeviceInfo

* `[Add]` AnalysisRecordUtils、FileRecordUtils 文件记录结果回调

* `[Add]` BigDecimalUtils#setScale、setRoundingMode、getBigDecimal、toString、toPlainString、toEngineeringString

* `[Add]` ClassUtils#getClass、isPrimitive、isMap

* `[Add]` MapUtils、CollectionUtils 获取泛型数组 toArrayT

* `[Update]` 移动 FileRecordUtils、HtmlUtils 到 Java 模块

Version 1.8.4 *(2019-11-05)*
----------------------------
 
* `[Add]` FileUtils#isImageFormats、isAudioFormats、isVideoFormats、isFileFormats

* `[Add]` ViewUtils#getWidthHeight、getNextFocusUpId、getNextFocusRightId、getNextFocusLeftId、getNextFocusDownId、getNextFocusForwardId、isScrollContainer、getChildCount、getRotation、getRotationX、getRotationY、getScaleX、getScaleY、getTextAlignment、getTextDirection、getPivotX、getPivotY、getTranslationX、getTranslationY、getLayerType、isFocusable、isSelected、isEnabled、isClickable、isLongClickable、findFocus、isFocused、hasFocus、hasFocusable、isFocusableInTouchMode、setFocusableInTouchMode、scrollTo、scrollBy、setScrollX、setScrollY、getScrollX、getScrollY、isHorizontalScrollBarEnabled、setHorizontalScrollBarEnabled、isVerticalScrollBarEnabled、setVerticalScrollBarEnabled、setDescendantFocusability、setOverScrollMode

* `[Add]` TextViewUtils#getTypeface、getLetterSpacing、getLineSpacingExtra、getLineSpacingMultiplier、getTextScaleX、getIncludeFontPadding、getInputType、getImeOptions、getMaxLines、getMinLines、getMaxEms、getMinEms、getEllipsize、getAutoLinkMask、getGravity、clearFocus、requestFocus、requestLayout、getTransformationMethod、setTransformationMethod

* `[Add]` EditTextUtils#isCursorVisible、getInputType、getImeOptions、getTransformationMethod、setTransformationMethod

* `[Add]` AnimationUtils#setAnimationListener

* `[Add]` ListViewUtils - 列表 View 相关工具类 ( 支持快捷滑动到指定索引、指定 x、y 轴坐标、回到顶部、底部等 )

* `[Add]` DevHelper、ViewHelper 快捷链式调用 Helper 类

Version 1.8.3 *(2019-10-31)*
----------------------------
 
* `[Add]` ArrayUtils#getMinimum、getMaximum、getMinimumIndex、getMaximumIndex、sumarray

* `[Add]` CollectionUtils#getMinimum、getMaximum、、getMinimumIndex、getMaximumIndex、sumlist

* `[Add]` AnimationUtils#setAnimation、getAnimation、clearAnimation、startAnimation、cancel

* `[Add]` ViewUtils#setAnimation、getAnimation、clearAnimation、startAnimation、cancel、measureView、setWidthHeight、setWidth、setHeight、addRule、removeRule、getRule、addRules、removeRules

* `[Add]` AppUtils#startActivity、startActivityForResult

* `[Add]` IntentUtils#getLaunchAppInstallPermissionSettingsIntent、getLaunchAppNotificationSettingsIntent

* `[Add]` PermissionUtils#canRequestPackageInstalls

* `[Add]` NotificationUtils#isNotificationEnabled

* `[Add]` CapturePictureUtils 截图工具类 ( 支持 View、Activity、FrameLayout、RelativeLayout、LinearLayout、ListView、GridView、ScrollView、HorizontalScrollView、NestedScrollView、WebView、RecyclerView(GridLayoutManager、LinearLayoutManager、StaggeredGridLayoutManager) )

Version 1.8.2 *(2019-10-18)*
----------------------------
 
 * `[Add]` TextViewUtils#setMinLines、setMaxEms、setMinEms、setEms、setMaxLength、setMaxLengthAndText、setInputType、setImeOptions

 * `[Add]` EditTextUtils#setInputType、setImeOptions

 * `[Add]` JSONObjectUtils#isJSON

 * `[Add]` ViewUtils#setLayerType、setAllCaps、setAlpha、getAlpha、setScrollContainer、setNextFocusForwardId、setNextFocusDownId、setNextFocusLeftId、setNextFocusRightId、setNextFocusUpId、setRotation、setRotationX、setRotationY、setScaleX、setScaleY、setTextAlignment、setTextDirection、setPivotX、setPivotY、setTranslationX、setTranslationY

Version 1.8.1 *(2019-10-13)*
----------------------------
 
 * `[Add]` EditTextUtils#addTextChangedListener、removeTextChangedListener、setTexts

 * `[Add]` TextViewUtils#getHint、getHints、getHintTextColors、setHintTextColor、setHintTextColors、getTextColors、setTextColor、setTextColors、setGravity、setHint、setAutoLinkMask、setEllipsize、setMaxLines、setLines

 * `[Add]` ViewUtils#getMinimumHeight、setMinimumHeight、getMinimumWidth、setMinimumWidth

 * `[Add]` ImageViewUtils#getAdjustViewBounds、setAdjustViewBounds、getMaxHeight、setMaxHeight、getMaxWidth、setMaxWidth

Version 1.8.0 *(2019-10-09)*
----------------------------
 
 * `[Update]` TextViewUtils#calcTextWidth 使用二分法优化处理
 
 * `[Add]` TextViewUtils#calcTextLine、TextViewUtils#getPaint、TextViewUtils#getTextWidth
 
 * `[Add]` DialogUtils#dismiss(DialogFragment)
 
 * `[Add]` ViewUtils#inflate
 
 * `[Add]` NumberUtils#getMultiple、getMultipleI、getMultipleD、getMultipleL、getMultipleF

Version 1.7.9 *(2019-09-19)*
----------------------------
 
 * `[Update]` compileSdkVersion 29 Android Q
 
 * `[Update]` AppCommonUtils#convertSDKVersion
 
 * `[Update]` ImageUtils#getImageType、ImageUtils#isImage modify to isImageFormats
 
 * `[Update]` 修改部分方法 void 返回值 ( 返回当前对象, 方便链式调用 )
 
 * `[Add]` AppCommonUtils#isQ
 
 * `[Add]` BitmapUtils#isImage
 
 * `[Add]` ListenerUtils#setOnLongClicks
 
 * `[Add]` ImageUtils#isICO、ImageUtils#isTIFF

 * `[Add]` ViewUtils#getTag、setTag

Version 1.7.8 *(2019-09-12)*
----------------------------
 
 * `[Add]` ImageViewUtils#setBackgroundResources
 
 * `[Add]` ViewUtils#getParent
 
 * `[Add]` ConvertUtils#convert

 * `[Fix]` DialogUtils#showDialog、closeDialog try catch

Version 1.7.7 *(2019-08-25)*
----------------------------

 * `[New]` Support for AndroidX

 * `[Add]` DevCommonUtils#subSetSymbol

 * `[Add]` ScreenUtils#setWindowSecure
 
 * `[Add]` ViewUtils#set/getMargin、set/getPadding, ViewUtils#set/getLayoutParams
 
 * `[Add]` AndroidManifest.xml FileProvider config
 
 * `[Update]` Update AppUtils、IntentUtils、UriUtils 使用 FileProvider authority 处理
 
 * `[Fix]` Reflect2Utils#getDeclaredFieldParent fieldNumber param 判断处理

Version 1.7.6 *(2019-08-02)*
----------------------------

 * `[New]` SpannableStringUtils

 * `[Add]` ViewUtils#set/getCompoundDrawables、set/getCompoundDrawablePadding
 
 * `[Add]` ImageUtils#setBounds

Version 1.0.0 ~ 1.7.5 *(2019-07-26)*
----------------------------

 * 整个工具类 review code, 并规范代码风格、检测注释、代码间距等
