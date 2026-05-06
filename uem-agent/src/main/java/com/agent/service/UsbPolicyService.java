package com.agent.service;

import com.agent.util.OSUtil;

public class UsbPolicyService {

    private static Boolean lastState = null;

    public static void start() {

        new Thread(() -> {

            while (true) {

                try {

                    Boolean currentState = PolicyCache.usbBlocked;

                    if (currentState != null &&
                            !currentState.equals(lastState)) {

                        if (currentState) {

                            disableUsb();

                        } else {

                            enableUsb();
                        }

                        lastState = currentState;
                    }

                    Thread.sleep(10000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    private static void disableUsb() {

        try {

            Process process = null;

            // WINDOWS
            if (OSUtil.isWindows()) {

                process = Runtime.getRuntime()
                        .exec(
                                "reg add HKLM\\SYSTEM\\CurrentControlSet\\Services\\USBSTOR "
                                        + "/v Start /t REG_DWORD /d 4 /f");
            }

            // LINUX
            else if (OSUtil.isLinux()) {

                process = Runtime.getRuntime()
                        .exec(
                                "modprobe -r usb_storage");
            }

            // MAC
            else if (OSUtil.isMac()) {

                process = Runtime.getRuntime()
                        .exec(
                                "diskutil unmountDisk force /Volumes/*");
            }

            if (process != null) {

                int exitCode = process.waitFor();

                System.out.println(
                        "USB Disabled Exit Code : "
                                + exitCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void enableUsb() {

        try {

            Process process = null;

            // WINDOWS
            if (OSUtil.isWindows()) {

                process = Runtime.getRuntime()
                        .exec(
                                "reg add HKLM\\SYSTEM\\CurrentControlSet\\Services\\USBSTOR "
                                        + "/v Start /t REG_DWORD /d 3 /f");
            }

            // LINUX
            else if (OSUtil.isLinux()) {

                process = Runtime.getRuntime()
                        .exec(
                                "modprobe usb_storage");
            }

            // MAC
            else if (OSUtil.isMac()) {

                System.out.println(
                        "USB enable operation for macOS is manual/MDM based");

                return;
            }

            if (process != null) {

                int exitCode = process.waitFor();

                System.out.println(
                        "USB Enabled Exit Code : "
                                + exitCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}