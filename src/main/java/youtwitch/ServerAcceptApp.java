package youtwitch;

import youtwitch.services.AcceptingService;
import youtwitch.services.VideoConverterService;
import youtwitch.services.VideoMakerService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ServerAcceptApp {

	public static void main(String[] args) throws IOException {
		AcceptingService.startAccept(54339);
		VideoMakerService.makeVideo();
		VideoConverterService.convertVideo();
	}

}
