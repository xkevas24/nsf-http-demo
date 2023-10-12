package com.qz.nsf.ddeemmoo;
// import com.mashape.unirest.http.Unirest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;

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
import java.util.Random;


@RestController

public class WebController {
    @CrossOrigin()
    @GetMapping("/")
    public String index() {
        return "Welcome to use Netease Qingzhou Cloud Native Service!";
    }
    @CrossOrigin()
    @GetMapping("/health")
    public String health() {
        return "I'm good";
    }

    @CrossOrigin()
    @GetMapping("/get_version")
    public Map<String, Object> get_version(HttpServletRequest request) {
        Map<String, Object> ret = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        ret.put("code", 200);
        ret.put("msg", "ok");
        String version = "V2.0.0";
        /* String version1 = "V1.0.0";
        String version2 = "V2.0.0";

        Random rand = new Random();
        int num = rand.nextInt(100); // 随机生成一个0~99之间的整数

        if (num < 50) {
            version = version1;
        } else {
            version = version2;
        } */


        data.put("version", version);
        data.put("notes", "提供产品的名称和价格");
        List<String> ips = this.getLocalIp();
        HttpHeaders headers = Collections.list(request.getHeaderNames()).stream()
                .collect(HttpHeaders::new, (h, n) -> h.add(n, request.getHeader(n)), HttpHeaders::putAll);
        data.put("ips", ips);
        data.put("headers", headers);
        data.put("color_mark", null);  // 自己不是染色实例
        ret.put("data", data);
        return ret;
    }

    @CrossOrigin()
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

    @CrossOrigin()
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
            data.put("response_text", response);
            ret.put("data", data);
            return ret;
        }
        return ret;
    }

    @CrossOrigin()
    @GetMapping("/get_product")
    public String get_product() {
        // 年化从detail获取
        String lv1 = get_annual("财富管家-初级版");
        String lv2 = get_annual("财富管家-中级版");
        String lv3 = get_annual("财富管家-高级版");
        String lv4 = get_annual("财富管家-终身版");
        return String.format("{\"msg\":\"ok\",\"code\":200,\"data\": [{\"title\": \"财富管家-初级版\", \"img\": \"https://img0.baidu.com/it/u=652700042,3999938957&fm=253&fmt=auto&app=138&f=PNG?w=750&h=500\", \"basic\": \"%s\", \"notice\": \"30天｜10元起购｜中低风险｜债券基金\"}, {\"title\": \"财富管家-中级版\", \"img\": \"https://img0.baidu.com/it/u=537070019,4158887229&fm=253&fmt=auto&app=138&f=JPEG?w=657&h=370\", \"basic\": \"%s\", \"notice\": \"30天｜20元起购｜中低风险｜债券基金\"}, {\"title\": \"财富管家-高级版\", \"img\": \"https://img1.baidu.com/it/u=4007786138,3371888956&fm=253&fmt=auto&app=138&f=JPEG?w=888&h=500\", \"basic\": \"%s\", \"notice\": \"30天｜30元起购｜中低风险｜债券基金\"}, {\"title\": \"财富管家-终身版\", \"img\": \"https://img0.baidu.com/it/u=2920245905,2739364058&fm=253&fmt=auto?w=640&h=392\", \"basic\": \"%s\", \"notice\": \"120天｜100元起购｜中低风险｜债券基金\"}]}", lv1, lv2, lv3, lv4);
    }

    private String get_annual( String Product) {
        String url = String.format("http://%s:%s%s?product_name=%s", "product-detail", "8081", "/get_annual", Product); // 打包改成8080
        System.out.println(url);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }

}

