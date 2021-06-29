package com.sdbean.megashare.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.sdbean.megashare.share.SharePlatform;

final public class PlatformHelper {
    public static String TwitterKey;
    public static String TwitterSecret;

    // ============================ 获得平台 SDK 所需信息相关 BEGIN ===================

    public static void setTwitterKey(String twitterKey) {
        TwitterKey = twitterKey;
    }

    public static void setTwitterSecret(String twitterSecret) {
        TwitterSecret = twitterSecret;
    }

    /**
     * 获取 Twitter Key.
     */
    public static String getTwitterConsumerKey(Context context) {
        if (TextUtils.isEmpty(TwitterKey)) {
            return "none";
        }
        return TwitterKey;

    }

    /**
     * 获取 Twitter Secret.
     */
    public static String getTwitterConsumerSecret(Context context) {

        if (TextUtils.isEmpty(TwitterSecret)) {
            return "none";
        }
        return TwitterSecret;
    }

    /**
     * 获取微信 AppID.
     */
    public static String getWechatAppId(Context context) {

        InputStream inputStream = getInputStream(context);
        Element wxe = getRootElement("WeChat", inputStream);

        return wxe.getAttribute("AppId");
    }

    /**
     * 获取微信 Secret.
     */
    public static String getWechatAppSecret(Context context) {

        InputStream inputStream = getInputStream(context);
        Element wxe = getRootElement("WeChat", inputStream);

        return wxe.getAttribute("AppSecret");
    }

    /**
     * 获取新浪微博 App Key.
     */
    public static String getSinaWeiboAppKey(Context context) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        InputStream inputStream = getInputStream(context);
        Element swbe = getRootElement("Sina", inputStream);
        return swbe.getAttribute("AppKey");
    }


    /**
     * 获取新浪微博 App Secret.
     */
    public static String getSinaWeiboAppSecret(Context context) {

        InputStream inputStream = getInputStream(context);
        Element swbe = getRootElement("Sina", inputStream);

        return swbe.getAttribute("AppSecret");
    }

    /**
     * 获取新浪微博 RedirectUrl.
     */
    public static String getSinaWeiboRedirectUrl(Context context) {

        InputStream inputStream = getInputStream(context);
        Element swbe = getRootElement("Sina", inputStream);

        return swbe.getAttribute("RedirectUrl");
    }

    /**
     * 获取新浪微博 Scope.
     */
    public static String getSinaWeiboScope(Context context) {

        InputStream inputStream = getInputStream(context);
        Element swbe = getRootElement("Sina", inputStream);
        return swbe.getAttribute("Scope");
    }

    /**
     * 获取 QQ App ID.
     */
    public static String getQQAppId(Context context) {

        InputStream inputStream = getInputStream(context);
        Element qqe = getRootElement("QQ", inputStream);

        return qqe.getAttribute("AppID");
    }

    /**
     * 获取 QQ App Secret.
     */
    public static String getQQAppSecret(Context context) {

        InputStream inputStream = getInputStream(context);
        Element qqe = getRootElement("QQ", inputStream);

        return qqe.getAttribute("AppSecret");
    }


    /**
     * 获取 Tumblr Consumer Key.
     */
    public static String getTumblrConsumerKey(Context context) {

        InputStream inputStream = getInputStream(context);
        Element te = getRootElement("Tumblr", inputStream);

        return te.getAttribute("ConsumerKey");
    }

    /**
     * 获取 Tumblr Consumer Secret.
     */
    public static String getTumblrConsumerSecret(Context context) {

        InputStream inputStream = getInputStream(context);
        Element te = getRootElement("Tumblr", inputStream);
        return te.getAttribute("ConsumerSecret");
    }

    /**
     * 获取 Tumblr Flurry Key.
     */
    public static String getTumblrFlurryKey(Context context) {

        InputStream inputStream = getInputStream(context);
        Element te = getRootElement("Tumblr", inputStream);

        return te.getAttribute("FlurryKey");
    }

    /**
     * 获取 Pinterest App ID.
     */
    public static String getPinterestAppId(Context context) {

        InputStream inputStream = getInputStream(context);
        Element te = getRootElement("Pinterest", inputStream);

        return te.getAttribute("AppId");
    }


    /***/
    private static InputStream getInputStream(Context context) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open("MegaShare.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    private static Element getRootElement(String rootElementName, InputStream is) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Element element = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(is);
            Element rootElement = document.getDocumentElement();
            element = (Element) rootElement.getElementsByTagName(rootElementName).item(0);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return element;
    }

    // ============================ 获得平台 SDK 所需信息相关 END ===================

    // ============================ 判断平台是否安装 BEGIN ===================

    public static boolean isInstalled(Context context, SharePlatform.Platform platform) {
        String packageName = "";
        if (platform == SharePlatform.Platform.Facebook) {
            packageName = "com.facebook.katana";
        } else if (platform == SharePlatform.Platform.Twitter) {
            packageName = "com.twitter.android";
        } else if (platform == SharePlatform.Platform.WeChat) {
            packageName = "com.tencent.mm";
        } else if (platform == SharePlatform.Platform.WhatsApp) {
            packageName = "com.whatsapp";
        } else if (platform == SharePlatform.Platform.QQ) {
            packageName = "com.tencent.mobileqq";
        } else if (platform == SharePlatform.Platform.Sina) {
            packageName = "com.sina.weibo";
        } else if (platform == SharePlatform.Platform.Instagram) {
            packageName = "com.instagram.android";
        } else if (platform == SharePlatform.Platform.Line) {
            packageName = "jp.naver.line.android";
        } else if (platform == SharePlatform.Platform.Pinterest) {
            packageName = "com.pinterest";
        } else {
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(PackageManager.GET_META_DATA);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (packageName.equals(pn)) {
                    return true;
                }
            }
        }
        return false;
    }
    // ============================ 判断平台是否安装 END ===================

    public static String getAllInstallPkg(Context context) {
        StringBuilder sb = new StringBuilder();

        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(PackageManager.GET_META_DATA);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                if (!isSystemPackage(pinfo.get(i))) {
                    sb.append(pinfo.get(i).packageName);
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 是否是系统应用
     *
     * @param pkgInfo
     * @return
     */
    public static boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }
}
