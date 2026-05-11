package com.agent.service;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.ByteArrayOutputStream;

import java.util.Base64;
import java.util.Iterator;

public class ScreenshotService {

    public static String captureBase64() {

        try {

            // Capture FULL ORIGINAL resolution
            Rectangle screenRect = new Rectangle(
                    Toolkit.getDefaultToolkit()
                            .getScreenSize());

            BufferedImage original = new Robot()
                    .createScreenCapture(screenRect);

            // OPTIONAL:
            // If you want NO resize at all:
            BufferedImage image = original;

            // ------------------------------------------
            // HIGH QUALITY JPEG COMPRESSION
            // ------------------------------------------

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");

            ImageWriter writer = writers.next();

            ImageWriteParam param = writer.getDefaultWriteParam();

            param.setCompressionMode(
                    ImageWriteParam.MODE_EXPLICIT);

            // HIGH QUALITY
            param.setCompressionQuality(0.95f);

            writer.setOutput(
                    ImageIO.createImageOutputStream(baos));

            writer.write(
                    null,
                    new IIOImage(image, null, null),
                    param);

            writer.dispose();

            return Base64.getEncoder()
                    .encodeToString(
                            baos.toByteArray());

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }
}