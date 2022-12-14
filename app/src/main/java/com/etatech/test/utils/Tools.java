package com.etatech.test.utils;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.LogUtils;
import com.etatech.test.bean.Vector2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.etatech.test.utils.App.logArr;

/**
 * Created by Michael
 * Date:  2020/2/18
 * Func:
 */
public class Tools {

    public static <T extends ViewModel> T getViewModel(BaseActivity appCompatActivity, @NonNull Class<T> modelClass) {
        if (appCompatActivity.getSupportFragmentManager().isDestroyed()) {
            LogUtils.e("getViewModel", "isDestroyed");
        }
        return ViewModelProviders.of(appCompatActivity).get(modelClass);
    }

    public static <T extends ViewModel> T getViewModel(Fragment appCompatActivity, @NonNull Class<T> modelClass) {
        return ViewModelProviders.of(appCompatActivity).get(modelClass);
    }

    /**
     * ????????????URL??????GET???????????????
     *
     * @param url   ???????????????URL
     * @param param ???????????????????????????????????? name1=value1&name2=value2 ?????????
     * @return ???????????????????????????
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader bufferedReader = null;
        try {
            //1???????????????URL
            String urlNameString = url + "?" + param;
            //2??????url?????????URL?????????
            URL realUrl = new URL(urlNameString);

            //3????????????URL???????????????
            URLConnection connection = realUrl.openConnection();
            //4??????????????????????????????
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            //5????????????????????????
            connection.connect();
            //???????????????????????????
            Map<String, List<String>> map = connection.getHeaderFields();
            //??????????????????????????????
            for (String key : map.keySet()) {
                System.out.println(key + "---->" + map.get(key));
            }

            //6?????????BufferedReader??????????????????URL??????????????? ???UTF-8?????????????????????????????????????????????????????????????????????
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line = "";
            while (null != (line = bufferedReader.readLine())) {
                result += line;
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("??????GET???????????????????????????" + e);
            e.printStackTrace();
        } finally {        //??????finally?????????????????????
            try {
                if (null != bufferedReader) {
                    bufferedReader.close();
                }
            } catch (Exception e2) {
                // TODO: handle exception
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * ????????????URL??????POST???????????????
     *
     * @param url   ???????????????URL
     * @param param ???????????????????????????????????? name1=value1&name2=value2 ?????????
     * @return ???????????????????????????
     */
    public static String sendPost(String url, String param) {
        String result = "";
        BufferedReader bufferedReader = null;
        PrintWriter out = null;
        try {
            //1???2???????????????url?????????URL?????????
            URL realUrl = new URL(url);

            //3????????????URL???????????????
            URLConnection connection = realUrl.openConnection();
            //4??????????????????????????????
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            // ??????POST??????????????????????????????
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //5????????????????????????
            //connection.connect();
            //??????URLConnection????????????????????????
            out = new PrintWriter(connection.getOutputStream());
            //??????????????????
            out.print(param);
            //flush??????????????????
            out.flush();
            //

            //6?????????BufferedReader??????????????????URL???????????????
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while (null != (line = bufferedReader.readLine())) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("??????POST???????????????????????????" + e);
            e.printStackTrace();
        } finally {        //??????finally?????????????????????????????????
            try {
                if (null != out) {
                    out.close();
                }
                if (null != bufferedReader) {
                    bufferedReader.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String getColorStrAlpha(Context context, int color) {
        return '#' + Integer.toHexString(ContextCompat.getColor(context, color));
    }

    public static String getColorStr(Context context, int color) {
        String retColor = "#" + Integer.toHexString(ContextCompat.getColor(context, color) & 0x00ffffff);
        return retColor;
    }

    public static int getColorInt(String color) {
        return Color.parseColor(color);
    }

    public static void addLog(Context context, String log, int color) {
        logArr.add(Html.fromHtml(
                String.format("<font color=%s>%s:%s</font>",
                        Tools.getColorStr(context, color),
                        getCurrentTime(),
                        log)));
    }

    // ?????????context????????????Application??? ??????????????????????????????
    public static void addLog(String log, int color) {
        addLog(App.getInstance().getApplicationContext(), log, color);
    }

    public static String getCurrentTime() {
        // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy???MM???dd??? HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     * Convert byte array to hex string
     *
     * @param bytes toConvert
     * @return hexValue
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sbuf = new StringBuilder();
        for (int idx = 0; idx < bytes.length; idx++) {
            int intVal = bytes[idx] & 0xff;
            if (intVal < 0x10) sbuf.append("0");
            sbuf.append(Integer.toHexString(intVal).toUpperCase());
        }
        return sbuf.toString();
    }

    /**
     * Get utf8 byte array.
     *
     * @param str which to be converted
     * @return array of NULL if error was found
     */
    public static byte[] getUTF8Bytes(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Load UTF8withBOM or any ansi text file.
     *
     * @param filename which to be converted to string
     * @return String value of File
     * @throws java.io.IOException if error occurs
     */
    public static String loadFileAsString(String filename) throws java.io.IOException {
        final int BUFLEN = 1024;
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(filename), BUFLEN);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(BUFLEN);
            byte[] bytes = new byte[BUFLEN];
            boolean isUTF8 = false;
            int read, count = 0;
            while ((read = is.read(bytes)) != -1) {
                if (count == 0 && bytes[0] == (byte) 0xEF && bytes[1] == (byte) 0xBB && bytes[2] == (byte) 0xBF) {
                    isUTF8 = true;
                    baos.write(bytes, 3, read - 3); // drop UTF8 bom marker
                } else {
                    baos.write(bytes, 0, read);
                }
                count += read;
            }
            return isUTF8 ? new String(baos.toByteArray(), "UTF-8") : new String(baos.toByteArray());
        } finally {
            try {
                is.close();
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Returns MAC address of the given interface name.
     *
     * @param interfaceName eth0, wlan0 or NULL=use first interface
     * @return mac address or empty string
     */
    public static String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac == null) return "";
                StringBuilder buf = new StringBuilder();
                for (byte aMac : mac) buf.append(String.format("%02X:", aMac));
                if (buf.length() > 0) buf.deleteCharAt(buf.length() - 1);
                return buf.toString();
            }
        } catch (Exception ignored) {
        } // for now eat exceptions
        return "";
        /*try {
            // this is so Linux hack
            return loadFileAsString("/sys/class/net/" +interfaceName + "/address").toUpperCase().trim();
        } catch (IOException ex) {
            return null;
        }*/
    }

    /**
     * Get IP address from first non-localhost interface
     *
     * @param useIPv4 true=return ipv4, false=return ipv6
     * @return address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        } // for now eat exceptions
        return "";
    }

    public static Vector2 index2pos(int index, int lengh) {
        Vector2 pos = new Vector2();
        pos.setX(index % lengh + 1);
        pos.setY((int) Math.ceil(index / lengh) + 1);
        return pos;
    }

    public static int pos2index(Vector2 pos, int lengh) {
        return (pos.getY() - 1) * lengh + pos.getX() - 1;
    }

    private static Random random = new Random();

    public static int randomRange(int min, int max) {
        if (min < max) {
            return min + random.nextInt(max - min);
        } else if (min == max) {
            return min;
        } else {
            return -1;
        }
    }

    public static int randomRange(int max) {
        return randomRange(0, max);
    }

    public static boolean isOverLap(Rect rect1, Rect rect2) {
        return rect1.left <= rect2.right
                && rect2.left <= rect1.right
                && rect1.top <= rect2.bottom
                && rect2.top <= rect1.bottom;
    }

    public static boolean isConnect(Rect rect1, Rect rect2) {
        return rect1.left <= rect2.right + 1
                && rect2.left <= rect1.right + 1
                && rect1.top <= rect2.bottom + 1
                && rect2.top <= rect1.bottom + 1;
    }

    public static int pt2Px(float pt) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        if (App.getInstance() != null) {
            return AdaptScreenUtils.pt2Px(pt);
        }
        return (int) (pt * metrics.xdpi / 72f + 0.5);
    }

    public static int dp2Px(float dp, Context mContext) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                mContext.getResources().getDisplayMetrics());
    }

    public static Point calCirPos(Point center, float radius, float degree) {
        int posX1 = center.x + (int) (radius * Math.cos(Math.toRadians(degree)));
        int posY1 = center.y + (int) (radius * Math.sin(Math.toRadians(degree)));
        return new Point(posX1, posY1);
    }

    public static float[] hex2Color(String hexColor) {
        char[] charArray = hexColor.toCharArray();
        float[] rgba = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
        float r = 0, g = 0, b = 0, a = 0;
        String sr, sg, sb, sa;
        if (hexColor.contains("#")) {
            if (hexColor.length() == 7) {
                sr = String.format("%c%c", charArray[1], charArray[2]);
                sg = String.format("%c%c", charArray[3], charArray[4]);
                sb = String.format("%c%c", charArray[5], charArray[6]);
                r = Integer.valueOf(sr, 16) / 255.0f;
                g = Integer.valueOf(sg, 16) / 255.0f;
                b = Integer.valueOf(sb, 16) / 255.0f;
            } else if (hexColor.length() == 9) {
                sa = String.format("%c%c", charArray[1], charArray[2]);
                sr = String.format("%c%c", charArray[3], charArray[4]);
                sg = String.format("%c%c", charArray[5], charArray[6]);
                sb = String.format("%c%c", charArray[7], charArray[8]);
                a = Integer.valueOf(sa, 16) / 255.0f;
                r = Integer.valueOf(sr, 16) / 255.0f;
                g = Integer.valueOf(sg, 16) / 255.0f;
                b = Integer.valueOf(sb, 16) / 255.0f;
            } else {
                LogUtils.e("Color Format Error");
                return null;
            }
        } else {
            if (hexColor.length() == 6) {
                sr = String.format("%c%c", charArray[0], charArray[1]);
                sg = String.format("%c%c", charArray[2], charArray[3]);
                sb = String.format("%c%c", charArray[4], charArray[5]);
                r = Integer.valueOf(sr, 16) / 255.0f;
                g = Integer.valueOf(sg, 16) / 255.0f;
                b = Integer.valueOf(sb, 16) / 255.0f;
            } else if (hexColor.length() == 8) {
                sa = String.format("%c%c", charArray[0], charArray[1]);
                sr = String.format("%c%c", charArray[2], charArray[3]);
                sg = String.format("%c%c", charArray[4], charArray[5]);
                sb = String.format("%c%c", charArray[6], charArray[7]);
                a = Integer.valueOf(sa, 16) / 255.0f;
                r = Integer.valueOf(sr, 16) / 255.0f;
                g = Integer.valueOf(sg, 16) / 255.0f;
                b = Integer.valueOf(sb, 16) / 255.0f;
            } else {
                LogUtils.e("Color Format Error");
                return null;
            }
        }

        rgba[0] = r;
        rgba[1] = g;
        rgba[2] = b;
        rgba[3] = a;

        return rgba;
    }

    public static String readFile(AssetManager mgr, String path) {
        String contents = "";
        InputStream is = null;
        BufferedReader reader = null;
        try {
            is = mgr.open(path);
            reader = new BufferedReader(new InputStreamReader(is));
            contents = reader.readLine();
            String line = null;
            while ((line = reader.readLine()) != null) {
                contents += '\n' + line;
            }
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
        return contents;
    }

    public static SharedPreferences getShare() {
        return App.getInstance().getSharedPreferences(Consts.SHARE_PREFERENCE_SIGN, Activity.MODE_PRIVATE);
    }

    /**
     * ?????????????????????????????????
     *
     * @param context
     * @return
     */
    public static boolean isAppRunningForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : runningAppProcesses) {
            if (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (appProcessInfo.processName.equals(context.getApplicationInfo().processName))
                    return true;
            }
        }
        return false;
    }

    /**
     * ??????????????????????????????
     *
     * @param
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static boolean pingNetwork(String ip) {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 " + ip);
            int mExitValue = mIpAddrProcess.waitFor();
            System.out.println(" mExitValue " + mExitValue);
            if (mExitValue == 0) {
                return true;
            } else {
                return false;
            }
        } catch (InterruptedException ignore) {
            ignore.printStackTrace();
            System.out.println(" Exception:" + ignore);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(" Exception:" + e);
        }
        return false;
    }
}
