###**MINIMUM SDK REQUIRED**
******************
####4.4 (Kitkat)
#############
###**DEPENDENCIES**
******************
It has [App-Compact V7 support library](http://developer.android.com/intl/ja/tools/support-library/features.html#v7-appcompat) set up for >=4.4 .
```javascript
dependencies {
    compile 'com.android.support:appcompat-v7:21.+'
}
```
###**STYLE**
******************
Mainly it is using `AppTheme.Base` and it will override this theme in `value-v21` style for so we can use `Material Theme` also 
in 5.0 (Android-Lolipop).

*value > support below lollipop
    *style.xml
*value-v21  > for lollipop
    *style.xml

 






