package youtwitch.services;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import youtwitch.Const;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public abstract class VideoMakerService {


    private static final Dimension SCREEN_BOUNDS = new Dimension(Const.WIDTH_FOR_CAPTURE, Const.HEIGHT_FOR_CAPTURE);
    private static final String OUTPUT_FILE = "container\\stream_record.avi";



    public static void makeVideo() throws IOException {

        final IMediaWriter writer = ToolFactory.makeWriter(OUTPUT_FILE);
        writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_MPEG4,
                SCREEN_BOUNDS.width / 2, SCREEN_BOUNDS.height / 2);
        long startTime = System.nanoTime();

        for (int currentFrameNum = 0; currentFrameNum < Const.NUMBER_OF_FRAMES_CAPTURED; ++currentFrameNum) {

            File currentImage = new File(Const.FILEPATH + Const.IMAGE_NAME + currentFrameNum++ + Const.JPG_FORMAT);
            BufferedImage bgrScreen = ImageIO.read(currentImage);
            System.out.println("Video is collecting, nanoseconds counted from operation started: = "+ (System.nanoTime() - startTime));
            bgrScreen = convertToType(bgrScreen, BufferedImage.TYPE_3BYTE_BGR);
            writer.encodeVideo(0, bgrScreen, System.nanoTime() - startTime,
                    TimeUnit.NANOSECONDS);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
        writer.close();
    }

    public static BufferedImage convertToType(BufferedImage sourceImage,
                                              int targetType) {

        BufferedImage image;

        if (sourceImage.getType() == targetType) {
            image = sourceImage;
        }
        else {
            image = new BufferedImage(sourceImage.getWidth(),
                    sourceImage.getHeight(), targetType);
            image.getGraphics().drawImage(sourceImage, 0, 0, null);
        }

        return image;

    }

}
