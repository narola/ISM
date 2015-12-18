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
Mainly it is using `AppTheme.Base` and it will override this theme in `values-v21` style for so we can use `Material Theme` also 
in 5.0 (Android-Lollipop).

* values                      `support below lollipop`
    * style.xml
* values-v21                 `for lollipop`
    * style.xml

`values`
```xml
<style name="AppTheme" parent="AppTheme.Base"/>
<style name="AppTheme.Base" parent="Theme.AppCompat.Light">
<item..../>
</style>
```
`values-v21`
```xml
<style name="AppTheme" parent="AppTheme.Base">
        <!-- Customize your theme here. -->
</style>
```


**Note:: You will find this structure in commonsource module**






