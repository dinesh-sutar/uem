package com.agent.service.intraface;

import java.util.List;
import java.util.Map;

public interface BrowserHistoryStrategy {

    List<Map<String, Object>> collect(
            String deviceId);
}