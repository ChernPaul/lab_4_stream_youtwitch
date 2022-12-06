package youtwitch.services;

import youtwitch.Const;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public abstract class AcceptingService {
    public static void startAccept(int port) throws IOException {

        Socket socket = new Socket("127.0.0.1", port);

        try (DataInputStream rcv = new DataInputStream(new BufferedInputStream(socket.getInputStream())))
        {
            int iteration = 0;
            for (int currentFrameIndex = 0; currentFrameIndex < Const.NUMBER_OF_FRAMES_CAPTURED; ++currentFrameIndex) {
                int frameWidth = rcv.readInt();
                int frameHeight = rcv.readInt();

                int[] pixelData = new int[frameWidth * frameHeight];
                for (int i = 0; i < pixelData.length; i++) {
                    pixelData[i] = rcv.readInt();
                }

                BufferedImage frame = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_RGB);
                frame.setRGB(0, 0, frameWidth, frameHeight, pixelData, 0, frameWidth);
                File outputFile = new File(Const.FILEPATH + Const.IMAGE_NAME + iteration++ + Const.JPG_FORMAT);
                ImageIO.write(frame, Const.JPG_FORMAT, outputFile);
            }
        }
    }
}
