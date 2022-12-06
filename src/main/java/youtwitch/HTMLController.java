package youtwitch;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HTMLController {

    @GetMapping("/youTwitch")
    public String watchVideo() {
        return "index.html";
    }
}
