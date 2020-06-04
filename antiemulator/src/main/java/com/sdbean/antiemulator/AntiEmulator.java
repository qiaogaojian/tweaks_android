package com.sdbean.antiemulator;


import android.content.Context;
import android.telephony.TelephonyManager;

import com.lahm.library.EasyProtectorLib;
import com.lahm.library.EmulatorCheckCallback;
import com.nekolaboratory.EmulatorDetector;
import com.snail.antifake.jni.EmulatorDetectUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class AntiEmulator {

    private static String[] known_pipes = {
            "/dev/socket/qemud",
            "/dev/qemu_pipe"
    };

    private static String[] known_qemu_drivers = {
            "goldfish"
    };

    private static String[] known_files = {
            "/system/lib/libc_malloc_debug_qemu.so",
            "/sys/qemu_trace",
            "/system/bin/qemu-props"
    };

    private static String[] known_bluestacks = {"/data/app/com.bluestacks.appmart-1.apk", "/data/app/com.bluestacks.BstCommandProcessor-1.apk",
            "/data/app/com.bluestacks.help-1.apk", "/data/app/com.bluestacks.home-1.apk", "/data/app/com.bluestacks.s2p-1.apk",
            "/data/app/com.bluestacks.searchapp-1.apk", "/data/bluestacks.prop", "/data/data/com.androVM.vmconfig",
            "/data/data/com.bluestacks.accelerometerui", "/data/data/com.bluestacks.appfinder", "/data/data/com.bluestacks.appmart",
            "/data/data/com.bluestacks.appsettings", "/data/data/com.bluestacks.BstCommandProcessor", "/data/data/com.bluestacks.bstfolder",
            "/data/data/com.bluestacks.help", "/data/data/com.bluestacks.home", "/data/data/com.bluestacks.s2p", "/data/data/com.bluestacks.searchapp",
            "/data/data/com.bluestacks.settings", "/data/data/com.bluestacks.setup", "/data/data/com.bluestacks.spotlight", "/mnt/prebundledapps/bluestacks.prop.orig"
    };

    private static String cpuinfo;

    private static String[] known_geny_files = {"/dev/socket/genyd", "/dev/socket/baseband_genyd"};

    //检测“/dev/socket/qemud”，“/dev/qemu_pipe”这两个通道
    public static boolean checkPipes() {
        for (int i = 0; i < known_pipes.length; i++) {
            String pipes = known_pipes[i];
            File qemu_socket = new File(pipes);
            if (qemu_socket.exists()) {
                return true;
            }
        }
        return false;
    }

    // 检测驱动文件内容
    // 读取文件内容，然后检查已知QEmu的驱动程序的列表
    public static Boolean checkQEmuDriverFile() {
        File driver_file = new File("/proc/tty/drivers");
        if (driver_file.exists() && driver_file.canRead()) {
            byte[] data = new byte[1024];  //(int)driver_file.length()
            try {
                InputStream inStream = new FileInputStream(driver_file);
                inStream.read(data);
                inStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String driver_data = new String(data);
            for (String known_qemu_driver : AntiEmulator.known_qemu_drivers) {
                if (driver_data.indexOf(known_qemu_driver) != -1) {
                    return true;
                }
            }
        }
        return false;
    }

    //检测模拟器上特有的几个文件
    public static Boolean CheckEmulatorFiles() {
        for (int i = 0; i < known_files.length; i++) {
            String file_name = known_files[i];
            File qemu_file = new File(file_name);
            if (qemu_file.exists()) {
                return true;
            }
        }
        return false;
    }

    //检测手机上的一些硬件信息
    public static Boolean CheckEmulatorBuild() {
        String BOARD = android.os.Build.BOARD;
        String BOOTLOADER = android.os.Build.BOOTLOADER;
        String BRAND = android.os.Build.BRAND;
        String DEVICE = android.os.Build.DEVICE;
        String HARDWARE = android.os.Build.HARDWARE;
        String MODEL = android.os.Build.MODEL;
        String PRODUCT = android.os.Build.PRODUCT;
        if (BOARD == "unknown" || BOOTLOADER == "unknown"
                || BRAND == "generic" || DEVICE == "generic"
                || MODEL == "sdk" || PRODUCT == "sdk"
                || HARDWARE == "goldfish") {
            return true;
        }
        return false;
    }


    //检测cpu信息
    public static boolean checkCupInfo() {
        String result = "";
        String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
        try {
            ProcessBuilder cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            StringBuffer sb = new StringBuffer("");
            String readLine = "";
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));
            while ((readLine = responseReader.readLine()) != null) {
                sb.append(readLine);
            }
            responseReader.close();
            result = sb.toString().toLowerCase();
            cpuinfo = result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result.contains("intel") || result.contains("amd")) {
            return true;
        }
        return false;
    }

    //针对checkBlueStacks模拟器检测特定文件是否存在
    public static boolean checkBlueStacksFiles() {
        for (int i = 0; i < known_bluestacks.length; i++) {
            String file_name = known_bluestacks[i];
            File qemu_file = new File(file_name);
            if (qemu_file.exists()) {
                return true;
            }
        }
        return false;
    }

    //检测手机运营商家
    public static boolean CheckOperatorNameAndroid(Context context) {
        String szOperatorName = ((TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE)).getNetworkOperatorName();

        if (szOperatorName.toLowerCase().equals("android") == true) {
            return true;
        }
        return false;
    }

    /**
     * 检查是否存在已知的Genemytion环境文件
     *
     * @return {@code true} if any files where found to exist or {@code false} if not.
     */
    public static boolean hasGenyFiles() {
        for (String file : known_geny_files) {
            File geny_file = new File(file);
            if (geny_file.exists()) {
                return true;
            }
        }

        return false;
    }

    public static boolean EmulatorDetector(Context context) {
        return EmulatorDetector.isEmulator(context);
    }

    public static boolean EasyProtector(Context context) {
        if (EasyProtectorLib.checkIsRoot()) {
            return true;
        }

        if (EasyProtectorLib.checkXposedExistAndDisableIt()) {
            return true;
        }

        if (EasyProtectorLib.checkIsRunningInEmulator(context, new EmulatorCheckCallback() {

            @Override
            public void findEmulator(String emulatorInfo) {
                System.out.println("findEmulator:" + emulatorInfo);
            }
        })) {
            return true;
        }
        return false;
    }

    public static boolean Antifake(Context context) {
        return EmulatorDetectUtil.isEmulator(context);
    }

    public static boolean checkEmulator(Context context) {
        if (AntiEmulator.checkBlueStacksFiles() || AntiEmulator.checkCupInfo() || AntiEmulator.checkPipes() || AntiEmulator.checkQEmuDriverFile() || AntiEmulator.CheckEmulatorFiles() || AntiEmulator.CheckEmulatorBuild()) {
            return true;
        }
        return false;
    }

    public static String checkEmulatorLog(Context context) {
        StringBuilder result = new StringBuilder();

        result.append("虚拟机检测结果: <br>");
        if (checkBlueStacksFiles()) {
            result.append(String.format("<br> checkBlueStacksFiles: <font color=\"#FF0000\">[%s]</font>", "true"));
        } else {
            result.append(String.format("<br> checkBlueStacksFiles: <font color=\"#00FF00\">[%s]</font>", "false"));
        }

        if (checkCupInfo()) {
            result.append(String.format("<br> checkCupInfo: <font color=\"#FF0000\">[%s]</font>", "true"));
        } else {
            result.append(String.format("<br> checkCupInfo: <font color=\"#00FF00\">[%s]</font>", "false"));
        }
        result.append(cpuinfo);

        if (checkPipes()) {
            result.append(String.format("<br> checkPipes: <font color=\"#FF0000\">[%s]</font>", "true"));
        } else {
            result.append(String.format("<br> checkPipes: <font color=\"#00FF00\">[%s]</font>", "false"));
        }

        if (checkQEmuDriverFile()) {
            result.append(String.format("<br> checkQEmuDriverFile: <font color=\"#FF0000\">[%s]</font>", "true"));
        } else {
            result.append(String.format("<br> checkQEmuDriverFile: <font color=\"#00FF00\">[%s]</font>", "false"));
        }

        if (CheckEmulatorFiles()) {
            result.append(String.format("<br> CheckEmulatorFiles: <font color=\"#FF0000\">[%s]</font>", "true"));
        } else {
            result.append(String.format("<br> CheckEmulatorFiles: <font color=\"#00FF00\">[%s]</font>", "false"));
        }

        if (CheckEmulatorBuild()) {
            result.append(String.format("<br> CheckEmulatorBuild: <font color=\"#FF0000\">[%s]</font>", "true"));
        } else {
            result.append(String.format("<br> CheckEmulatorBuild: <font color=\"#00FF00\">[%s]</font>", "false"));
        }

        if (CheckOperatorNameAndroid(context)) {
            result.append(String.format("<br> CheckOperatorNameAndroid: <font color=\"#FF0000\">[%s]</font>", "true"));
        } else {
            result.append(String.format("<br> CheckOperatorNameAndroid: <font color=\"#00FF00\">[%s]</font>", "false"));
        }

        if (hasGenyFiles()) {
            result.append(String.format("<br> hasGenyFiles: <font color=\"#FF0000\">[%s]</font>", "true"));
        } else {
            result.append(String.format("<br> hasGenyFiles: <font color=\"#00FF00\">[%s]</font>", "false"));
        }

        if (EmulatorDetector(context)) {
            result.append(String.format("<br> EmulatorDetector: <font color=\"#FF0000\">[%s]</font>", "true"));
        } else {
            result.append(String.format("<br> EmulatorDetector: <font color=\"#00FF00\">[%s]</font>", "false"));
        }

        if (EasyProtector(context)) {
            result.append(String.format("<br> EasyProtector: <font color=\"#FF0000\">[%s]</font>", "true"));
        } else {
            result.append(String.format("<br> EasyProtector: <font color=\"#00FF00\">[%s]</font>", "false"));
        }

        if (Antifake(context)) {
            result.append(String.format("<br> Antifake: <font color=\"#FF0000\">[%s]</font>", "true"));
        } else {
            result.append(String.format("<br> Antifake: <font color=\"#00FF00\">[%s]</font>", "false"));
        }

        System.out.println(result.toString());
        return result.toString();
    }
}