package com.agent.service;

import java.util.HashSet;
import java.util.Set;

public class PolicyCache {

    public static volatile Boolean usbBlocked;

    public static volatile Set<String> blockedProcesses = new HashSet<>();
}
