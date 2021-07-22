package hla.homeworks.hw3.ga.googleanaliticsapp;

import com.brsanthu.googleanalytics.GoogleAnalytics;
import com.brsanthu.googleanalytics.GoogleAnalyticsConfig;
import com.brsanthu.googleanalytics.request.EventHit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GreetingController {
    public static final String measurementId = "G-PBEX775M4N";
    public static final String apiSecret = "0nf1HwHzSQaxbLqx4SFjsg";

    public static final String GAMP_URL = String.format("https://www.google-analytics.com/mp/collect?measurement_id=%s&api_secret=%s", measurementId, apiSecret);


    public static final String TRACKING_ID = "UA-202723483-1";

//    private final RestTemplate restTemplate;

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private GoogleAnalytics ga;

    @PostConstruct
    public void init() {
        GoogleAnalyticsConfig googleAnalyticsConfig = new GoogleAnalyticsConfig();
        googleAnalyticsConfig.setValidate(true);
        ga = GoogleAnalytics.builder()
                .withTrackingId(TRACKING_ID)
                .withConfig(googleAnalyticsConfig)
                .build();

    }

    private void scheduleEventsForUser(String ga,String gid, String name) {
        scheduledExecutorService.scheduleAtFixedRate(() -> {

            EventHit eventHit = this.ga.event()
                    .clientId(ga)
                    .userId(gid)
                    .eventAction("SCHEDULED_ACTION")
                    .eventLabel(name)
                    .customDimension(2, name)
                    .customMetric(3, "custom-metric")
                    .eventCategory("BACKEND_EVENTS")
                    .eventValue(LocalDateTime.now().getSecond());

            log.info("Sending GA event {}", eventHit);
            eventHit.send();


        }, 10, 30, TimeUnit.SECONDS);

    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
                           Model model,
                           @CookieValue(value = "_ga",required = false) String ga,
                           @CookieValue(value = "_gid", required = false) String gid) {
        log.info(">>>  _ga={}, _gid={}", ga, gid);

        scheduleEventsForUser(ga, gid, name);

        model.addAttribute("name", name);
        return "greeting";
    }

}