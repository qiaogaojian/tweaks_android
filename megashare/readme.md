# MegaShare 海外社会化分享SDK说明

MegaShare是对常见的三种海外社交平台社会化分享的封装,包括Line,Facebook,Twitter,通过封装避免重复性劳动. 使用时仅需要在Moudle中更换Facebook的appid,然后生成依赖库引用即可.

## Line

Line分享是通过Scheme协议跳转到Line应用中实现的分享,包括分享 文字/图片 两种类型, 具体可查看SDK中 LineManager.java

## Facebook

Facebook分享通过接入SDK进行分享, 包括分享 网页/文字/图片/多个图片/本地视频 5种类型, 具体可查看SDK中 FacebookManager.java

注意: Facebook接入时需要先去Facebook开发者中心建立相应应用并替换SDK中的appid, 如果Facebook的appid一样, 引用MegaShare的应用会因为"INSTALL FAILED CONFLICTING PROVIDER"冲突导致不能在手机上共存.
appid在SDK中的 MegaShare.java(line 10) AndroidManifest.xml(line 9/13/25) 这四个位置.

## Twitter

Twitter分享通过接入SDK进行分享, 包括分享 网页/文字/图片 3种类型, 具体可查看SDK中 TwitterManager.java
Twitter分享不存在冲突问题,可使用相同的appkey/secret.