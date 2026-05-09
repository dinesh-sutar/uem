package com.agent.service;

import java.io.File;

import com.agent.dto.ActiveWindowInfo;

import com.sun.jna.Native;

import com.sun.jna.ptr.IntByReference;

import com.sun.jna.platform.win32.Kernel32Util;

import com.sun.jna.platform.win32.User32;

import com.sun.jna.platform.win32.WinDef.HWND;

public class ActiveWindowService {

    public static ActiveWindowInfo getActiveWindow() {

        try {

            char[] windowText = new char[1024];

            HWND hwnd = User32.INSTANCE
                    .GetForegroundWindow();

            User32.INSTANCE.GetWindowText(
                    hwnd,
                    windowText,
                    1024);

            String title = Native.toString(windowText);

            int pid = getProcessId(hwnd);

            String processName = getProcessName(pid);

            return new ActiveWindowInfo(
                    title,
                    processName);

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }

    private static int getProcessId(
            HWND hwnd) {

        IntByReference pid = new IntByReference();

        User32.INSTANCE
                .GetWindowThreadProcessId(
                        hwnd,
                        pid);

        return pid.getValue();
    }

    private static String getProcessName(
            int pid) {

        try {

            String fullPath = Kernel32Util
                    .QueryFullProcessImageName(
                            pid,
                            0);

            return new File(fullPath)
                    .getName();

        } catch (Exception e) {

            return "Unknown";
        }
    }
}