package com.agent.service;

import java.nio.file.Paths;

import com.agent.util.OSUtil;

public class ApplicationPolicyService {

    public static void start() {

        new Thread(() -> {

            while (true) {

                try {

                    ProcessHandle.allProcesses()
                            .forEach(processHandle -> {

                                processHandle.info()
                                        .command()
                                        .ifPresent(command -> {

                                            try {

                                                String processName = Paths.get(command)
                                                        .getFileName()
                                                        .toString();

                                                if (PolicyCache.blockedProcesses
                                                        .contains(processName)) {

                                                    blockProcess(processName);
                                                }

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        });
                            });

                    Thread.sleep(5000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    private static void blockProcess(
            String processName) {

        try {

            Process process = null;

            // WINDOWS
            if (OSUtil.isWindows()) {

                process = Runtime.getRuntime()
                        .exec(
                                "taskkill /F /IM "
                                        + processName);
            }

            // LINUX
            else if (OSUtil.isLinux()) {

                String linuxProcess = processName.replace(".exe", "");

                process = Runtime.getRuntime()
                        .exec(
                                "pkill -f "
                                        + linuxProcess);
            }

            // MAC
            else if (OSUtil.isMac()) {

                String macProcess = processName.replace(".app", "")
                        .replace(".exe", "");

                process = Runtime.getRuntime()
                        .exec(
                                "pkill -f "
                                        + macProcess);
            }

            if (process != null) {

                int exitCode = process.waitFor();

                System.out.println(
                        "Blocked Process : "
                                + processName
                                + " Exit Code : "
                                + exitCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}