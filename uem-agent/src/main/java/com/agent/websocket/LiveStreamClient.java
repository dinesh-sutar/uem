package com.agent.websocket;

import com.agent.dto.ScreenshotFrame;
import com.agent.service.ScreenshotService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class LiveStreamClient extends WebSocketClient {

    private final String deviceId;

    private final ObjectMapper mapper = new ObjectMapper();

    public LiveStreamClient(
            String serverUrl,
            String deviceId) throws Exception {

        super(new URI(serverUrl));

        this.deviceId = deviceId;
    }

    @Override
    public void onOpen(ServerHandshake handshake) {

        System.out.println(
                "Live websocket connected");

        startStreaming();
    }

    private void startStreaming() {

        new Thread(() -> {

            while (true) {

                try {

                    // VERY IMPORTANT
                    if (!isOpen()) {

                        Thread.sleep(1000);

                        continue;
                    }

                    String image = ScreenshotService.captureBase64();

                    if (image == null) {

                        continue;
                    }

                    ScreenshotFrame frame = new ScreenshotFrame();

                    frame.setMacId(deviceId);

                    frame.setTimestamp(
                            System.currentTimeMillis());

                    frame.setImage(image);

                    String json = mapper.writeValueAsString(frame);

                    send(json);

                    System.out.println(
                            "Frame sent");

                    Thread.sleep(500);

                } catch (Exception e) {

                    e.printStackTrace();

                    try {

                        Thread.sleep(2000);

                    } catch (Exception ignored) {
                    }
                }
            }

        }).start();
    }

    @Override
    public void onMessage(String message) {

        System.out.println(
                "Server Message: " + message);
    }

    @Override
    public void onClose(
            int code,
            String reason,
            boolean remote) {

        System.out.println(
                "Websocket closed: " + reason);
    }

    @Override
    public void onError(Exception ex) {

        System.out.println(
                "Websocket error");

        ex.printStackTrace();
    }
}