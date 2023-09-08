package com.qz.nsf.ddeemmoo;
// import com.mashape.unirest.http.Unirest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;



@RestController

public class WebController {
    @GetMapping("/")
    public String index() {
        return "Welcome to use Netease Qingzhou Cloud Native Service!";
    }
    @GetMapping("/health")
    public String health() {
        return "I'm good";
    }

    @GetMapping("/ping")
    public Map<String, Object> ping(HttpServletRequest request) {
        List<String> ips = this.getLocalIp();
        HttpHeaders headers = Collections.list(request.getHeaderNames()).stream()
                .collect(HttpHeaders::new, (h, n) -> h.add(n, request.getHeader(n)), HttpHeaders::putAll);
        Map<String, Object> ret = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        ret.put("code", 200);
        ret.put("msg", "ok");
        data.put("ips", ips);
        data.put("headers", headers);
        ret.put("data", data);
        return ret;
    }

    private List<String> getLocalIp() {
        List<String> ips = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    ips.add(addresses.nextElement().getHostAddress());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ips;
    }

    @GetMapping("/access_http")
    public Map<String, Object> access_http(@RequestParam("service_name") String ServiceName, @RequestParam("service_port") String ServicePort, @RequestParam("method") String Method, @RequestParam("api") String Api ) {
        Map<String, Object> ret = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        if (Objects.equals(Method, "GET")) {
            RestTemplate restTemplate = new RestTemplate();
            String url = String.format("http://%s:%s%s", ServiceName, ServicePort, Api);
            String response = restTemplate.getForObject(url, String.class);
            ret.put("code", 200);
            ret.put("msg", "ok");
            data.put("request_url", url);
            data.put("response_text", response);
            ret.put("data", data);
            return ret;
        }
        return ret;
    }
}

