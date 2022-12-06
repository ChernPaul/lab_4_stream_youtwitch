package youtwitch;

import youtwitch.services.StreamingService;

import java.io.IOException;

public class StreamerApp {

	public static void main(String[] args) throws IOException {
		StreamingService.startWebcamStreaming(54339);
	}

}
