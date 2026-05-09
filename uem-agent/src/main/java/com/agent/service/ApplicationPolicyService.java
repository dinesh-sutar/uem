// package com.agent.service;

// import java.nio.file.Paths;

// import com.agent.util.OSUtil;

// public class ApplicationPolicyService {

//     public static void start() {

//         new Thread(() -> {

//             while (true) {

//                 try {

//                     ProcessHandle.allProcesses()
//                             .forEach(processHandle -> {

//                                 processHandle.info()
//                                         .command()
//                                         .ifPresent(command -> {

//                                             try {

//                                                 String processName = Paths.get(command)
//                                                         .getFileName()
//                                                         .toString();

//                                                 if (PolicyCache.blockedProcesses
//                                                         .contains(processName)) {

//                                                     blockProcess(processName);
//                                                 }

//                                             } catch (Exception e) {
//                                                 e.printStackTrace();
//                                             }
//                                         });
//                             });

//                     Thread.sleep(5000);

//                 } catch (Exception e) {
//                     e.printStackTrace();
//                 }
//             }

//         }).start();
//     }

//     private static void blockProcess(
//             String processName) {

//         try {

//             Process process = null;

//             // WINDOWS
//             if (OSUtil.isWindows()) {

//                 process = Runtime.getRuntime()
//                         .exec(
//                                 "taskkill /F /IM "
//                                         + processName);
//             }

//             // LINUX
//             else if (OSUtil.isLinux()) {

//                 String linuxProcess = processName.replace(".exe", "");

//                 process = Runtime.getRuntime()
//                         .exec(
//                                 "pkill -f "
//                                         + linuxProcess);
//             }

//             // MAC
//             else if (OSUtil.isMac()) {

//                 String macProcess = processName.replace(".app", "")
//                         .replace(".exe", "");

//                 process = Runtime.getRuntime()
//                         .exec(
//                                 "pkill -f "
//                                         + macProcess);
//             }

//             if (process != null) {

//                 int exitCode = process.waitFor();

//                 System.out.println(
//                         "Blocked Process : "
//                                 + processName
//                                 + " Exit Code : "
//                                 + exitCode);
//             }

//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }

// package com.agent.service;

// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;

// import com.agent.util.OSUtil;

// public class ApplicationPolicyService {

//     public static void start() {

//         new Thread(() -> {

//             while (true) {

//                 try {

//                     ProcessHandle.allProcesses()
//                             .forEach(processHandle -> {

//                                 processHandle.info()
//                                         .command()
//                                         .ifPresent(command -> {

//                                             try {

//                                                 Path processPath = Paths.get(command);

//                                                 String processName = processPath
//                                                         .getFileName()
//                                                         .toString();

//                                                 if (PolicyCache.blockedProcesses
//                                                         .contains(processName.toLowerCase())) {

//                                                     System.out.println(
//                                                             "Blocked Application Detected : "
//                                                                     + processName);

//                                                     // kill running process
//                                                     killProcess(processName);

//                                                     // permanently block app
//                                                     blockExecutable(processPath);

//                                                 }

//                                             } catch (Exception e) {
//                                                 e.printStackTrace();
//                                             }
//                                         });
//                             });

//                     Thread.sleep(5000);

//                 } catch (Exception e) {
//                     e.printStackTrace();
//                 }
//             }

//         }).start();
//     }

//     /**
//      * Kill running process
//      */
//     private static void killProcess(
//             String processName) {

//         try {

//             Process process = null;

//             // WINDOWS
//             if (OSUtil.isWindows()) {

//                 process = Runtime.getRuntime()
//                         .exec(
//                                 "taskkill /F /IM "
//                                         + processName);
//             }

//             // LINUX
//             else if (OSUtil.isLinux()) {

//                 String linuxProcess = processName.replace(".exe", "");

//                 process = Runtime.getRuntime()
//                         .exec(
//                                 "pkill -f "
//                                         + linuxProcess);
//             }

//             // MAC
//             else if (OSUtil.isMac()) {

//                 String macProcess = processName.replace(".app", "")
//                         .replace(".exe", "");

//                 process = Runtime.getRuntime()
//                         .exec(
//                                 "pkill -f "
//                                         + macProcess);
//             }

//             if (process != null) {

//                 int exitCode = process.waitFor();

//                 System.out.println(
//                         "Killed Process : "
//                                 + processName
//                                 + " Exit Code : "
//                                 + exitCode);
//             }

//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }

//     /**
//      * Permanently block executable
//      */
//     private static void blockExecutable(
//             Path executablePath) {

//         try {

//             // skip if already blocked
//             if (!Files.exists(executablePath)) {
//                 return;
//             }

//             // WINDOWS
//             if (OSUtil.isWindows()) {

//                 // deny execute permission
//                 String command = "icacls \""
//                         + executablePath.toString()
//                         + "\" /deny Everyone:RX";

//                 Process process = Runtime.getRuntime().exec(command);

//                 int exitCode = process.waitFor();

//                 System.out.println(
//                         "Execute permission removed : "
//                                 + executablePath
//                                 + " Exit Code : "
//                                 + exitCode);

//                 // optional rename
//                 renameExecutable(executablePath);
//             }

//             // LINUX
//             else if (OSUtil.isLinux()) {

//                 Process process = Runtime.getRuntime()
//                         .exec(new String[] {
//                                 "chmod",
//                                 "-x",
//                                 executablePath.toString()
//                         });

//                 int exitCode = process.waitFor();

//                 System.out.println(
//                         "Execute permission removed : "
//                                 + executablePath
//                                 + " Exit Code : "
//                                 + exitCode);
//             }

//             // MAC
//             else if (OSUtil.isMac()) {

//                 Process process = Runtime.getRuntime()
//                         .exec(new String[] {
//                                 "chmod",
//                                 "-x",
//                                 executablePath.toString()
//                         });

//                 int exitCode = process.waitFor();

//                 System.out.println(
//                         "Execute permission removed : "
//                                 + executablePath
//                                 + " Exit Code : "
//                                 + exitCode);
//             }

//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }

//     /**
//      * Rename executable so user cannot restore easily
//      */
//     private static void renameExecutable(
//             Path executablePath) {

//         try {

//             String originalName = executablePath.getFileName().toString();

//             // already renamed
//             if (originalName.endsWith(".blocked")) {
//                 return;
//             }

//             Path blockedPath = executablePath.resolveSibling(
//                     originalName + ".blocked");

//             Files.move(
//                     executablePath,
//                     blockedPath);

//             System.out.println(
//                     "Executable Renamed : "
//                             + blockedPath);

//         } catch (Exception e) {

//             // rename may fail if locked
//             System.out.println(
//                     "Rename failed : "
//                             + executablePath);

//             e.printStackTrace();
//         }
//     }
// }

package com.agent.service;

import java.nio.file.Files;
import java.nio.file.Path;
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

                                                Path processPath = Paths.get(command);

                                                String processName = processPath
                                                        .getFileName()
                                                        .toString()
                                                        .toLowerCase();

                                                if (PolicyCache.blockedProcesses
                                                        .contains(processName)) {

                                                    System.out.println(
                                                            "Blocked Application Detected : "
                                                                    + processName);

                                                    // kill running process
                                                    killProcess(processName);

                                                    // persistent registry block
                                                    applyPersistentBlock(processName);

                                                    // optional local executable block
                                                    blockExecutable(processPath);
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

    /**
     * Kill running process
     */
    private static void killProcess(
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
                        "Killed Process : "
                                + processName
                                + " Exit Code : "
                                + exitCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Persistent block that survives:
     * reboot
     * reinstall
     * agent deletion
     */
    private static void applyPersistentBlock(
            String processName) {

        try {

            // WINDOWS
            if (OSUtil.isWindows()) {

                String registryKey = "\"HKLM\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\Image File Execution Options\\"
                        + processName
                        + "\"";

                String command = "reg add "
                        + registryKey
                        + " /v Debugger /t REG_SZ /d \"Blocked\" /f";

                Process process = Runtime.getRuntime().exec(command);

                int exitCode = process.waitFor();

                System.out.println(
                        "Persistent IFEO Block Applied : "
                                + processName
                                + " Exit Code : "
                                + exitCode);
            }

            // LINUX
            else if (OSUtil.isLinux()) {

                System.out.println(
                        "Persistent blocking not implemented for Linux");
            }

            // MAC
            else if (OSUtil.isMac()) {

                System.out.println(
                        "Persistent blocking not implemented for Mac");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Local executable block
     */
    private static void blockExecutable(
            Path executablePath) {

        try {

            if (!Files.exists(executablePath)) {
                return;
            }

            // WINDOWS
            if (OSUtil.isWindows()) {

                String command = "icacls \""
                        + executablePath.toString()
                        + "\" /deny Everyone:RX";

                Process process = Runtime.getRuntime().exec(command);

                int exitCode = process.waitFor();

                System.out.println(
                        "Execute permission removed : "
                                + executablePath
                                + " Exit Code : "
                                + exitCode);

                renameExecutable(executablePath);
            }

            // LINUX
            else if (OSUtil.isLinux()) {

                Process process = Runtime.getRuntime()
                        .exec(new String[] {
                                "chmod",
                                "-x",
                                executablePath.toString()
                        });

                int exitCode = process.waitFor();

                System.out.println(
                        "Execute permission removed : "
                                + executablePath
                                + " Exit Code : "
                                + exitCode);
            }

            // MAC
            else if (OSUtil.isMac()) {

                Process process = Runtime.getRuntime()
                        .exec(new String[] {
                                "chmod",
                                "-x",
                                executablePath.toString()
                        });

                int exitCode = process.waitFor();

                System.out.println(
                        "Execute permission removed : "
                                + executablePath
                                + " Exit Code : "
                                + exitCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Rename executable
     */
    private static void renameExecutable(
            Path executablePath) {

        try {

            String originalName = executablePath.getFileName().toString();

            if (originalName.endsWith(".blocked")) {
                return;
            }

            Path blockedPath = executablePath.resolveSibling(
                    originalName + ".blocked");

            Files.move(
                    executablePath,
                    blockedPath);

            System.out.println(
                    "Executable Renamed : "
                            + blockedPath);

        } catch (Exception e) {

            System.out.println(
                    "Rename failed : "
                            + executablePath);

            e.printStackTrace();
        }
    }

    /**
     * Remove persistent block
     */
    public static void removePersistentBlock(
            String processName) {

        try {

            if (OSUtil.isWindows()) {

                String registryKey = "\"HKLM\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\Image File Execution Options\\"
                        + processName
                        + "\"";

                String command = "reg delete "
                        + registryKey
                        + " /f";

                Process process = Runtime.getRuntime().exec(command);

                int exitCode = process.waitFor();

                System.out.println(
                        "Persistent IFEO Block Removed : "
                                + processName
                                + " Exit Code : "
                                + exitCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}