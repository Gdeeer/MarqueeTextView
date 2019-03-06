# MarqueeTextView
A TextView support custom gap.

# 效果
普通 TextView 的跑马灯效果：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190306212033798.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0dkZWVy,size_16,color_FFFFFF,t_70)

MarqueeTextView 的跑马灯效果：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190306212121322.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0dkZWVy,size_16,color_FFFFFF,t_70)

# 使用
复制 MarqueeTextView 至自己项目即可。

* app:useCustomGap
  是否使用自定义 Gap
* app:customGap
  自定义 Gap 的大小

示例代码：

```xml
<com.gdeer.marqueetextview.MarqueeTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_margin="20dp"
    android:singleLine="true"
    android:ellipsize="marquee"
    android:marqueeRepeatLimit="marquee_forever"
    app:useCustomGap="true"
    app:customGap="10dp"
    android:focusable="true"
    android:focusableInTouchMode="true"
    android:text="白日放歌须纵酒,青春作伴好还乡。即从巴峡穿巫峡,便下襄阳向洛阳。"/>
```
