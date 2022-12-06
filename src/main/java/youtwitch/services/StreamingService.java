package youtwitch.services;

import com.github.sarxos.webcam.Webcam;
import youtwitch.Const;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;

public abstract class StreamingService {

    public static void startWebcamStreaming(int port) throws IOException {

        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(Const.WIDTH_FOR_CAPTURE, Const.HEIGHT_FOR_CAPTURE));
        webcam.open();

        ServerSocket serverSocket = new ServerSocket(port);

        try (DataOutputStream sender = new DataOutputStream(new BufferedOutputStream(serverSocket.accept().getOutputStream())))
        {
            for (int currentFrameNum = 0; currentFrameNum < Const.NUMBER_OF_FRAMES_CAPTURED; ++currentFrameNum)
            {
                BufferedImage frame = webcam.getImage();
                int frameWidth = frame.getWidth();
                int frameHeight = frame.getHeight();

                sender.writeInt(frameWidth);
                sender.writeInt(frameHeight);

                int[] pixelData = new int[frameWidth * frameHeight];
                frame.getRGB(0, 0, frameWidth, frameHeight, pixelData, 0, frameWidth);

                for (int pixelDatum : pixelData) {
                    sender.writeInt(pixelDatum);
                }
            }
        }
    }
}
