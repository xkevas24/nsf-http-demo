package com.qz.nsf.ddeemmoo;
// import com.mashape.unirest.http.Unirest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import javax.servlet.http.HttpServletRequest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.concurrent.TimeUnit;

import static com.qz.nsf.ddeemmoo.DdeemmooApplication.global_version;
import static com.qz.nsf.ddeemmoo.DdeemmooApplication.global_color;


@RestController

public class WebController {
    public Map<String, Object> fake_429() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("timestamp", "2023-10-24T02:36:33.030+00:00");
        ret.put("status", "429");
        ret.put("error", "Too Many Requests");
        ret.put("path", "/*");
        return ret;
    }
    @CrossOrigin()
    @GetMapping("/")
    public String index() {
        return "Welcome to use Netease Qingzhou Cloud Native Service! - " + global_version;
    }
    @CrossOrigin()
    @GetMapping("/health")
    public String health() {
        return "I'm good";
    }

    @CrossOrigin()
    @GetMapping("/info/get_version")
    public Map<String, Object> get_version(HttpServletRequest request) {
        Map<String, Object> ret = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        ret.put("code", 200);
        ret.put("msg", "ok");
        String version = global_version;
        data.put("version", version);
        data.put("notes", "提供产品的名称和价格");
        List<String> ips = this.getLocalIp();
        HttpHeaders headers = Collections.list(request.getHeaderNames()).stream()
                .collect(HttpHeaders::new, (h, n) -> h.add(n, request.getHeader(n)), HttpHeaders::putAll);
        data.put("ips", ips);
        data.put("headers", headers);
        data.put("color_mark", global_color);  // 自己不是染色实例
        ret.put("data", data);
        // return fake_429();
        return ret;
    }

    @CrossOrigin()
    @GetMapping("/detail/get_version")
    public Map<String, Object> detail_get_version(HttpServletRequest request) {
        Map<String, Object> ret = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        ret.put("code", 200);
        ret.put("msg", "ok");
        String version = global_version;

        data.put("version", version);
        data.put("notes", "提供产品的详细介绍信息，为product-info提供年化利率的数据（非核心）");
        List<String> ips = this.getLocalIp();
        HttpHeaders headers = Collections.list(request.getHeaderNames()).stream()
                .collect(HttpHeaders::new, (h, n) -> h.add(n, request.getHeader(n)), HttpHeaders::putAll);
        data.put("ips", ips);
        data.put("headers", headers);
        // data.put("color_mark", "blue");  // 自己不是染色实例
        data.put("color_mark", global_color);  // 自己不是染色实例
        ret.put("data", data);
        return ret;
    }

    @CrossOrigin()
    @GetMapping("/detail/get_annual")
    public String getAnnual(@RequestParam String product_name) {
        String annual = "0.00%";

        if ("财富管家-初级版".equals(product_name)) {
            annual = "3.33%";
        } else if ("财富管家-中级版".equals(product_name)) {
            annual = "3.60%";
        } else if ("财富管家-高级版".equals(product_name)) {
            annual = "4.49%";
        } else if ("财富管家-终身版".equals(product_name)) {
            annual = "5.06%";
        }

        return annual;
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
    @GetMapping("/info/get_product")
    public String get_product() {
        // 年化从detail获取
        String lv1 = get_annual("财富管家-初级版");
        String lv2 = get_annual("财富管家-中级版");
        String lv3 = get_annual("财富管家-高级版");
        String lv4 = get_annual("财富管家-终身版");
        return String.format("{\"msg\":\"ok\",\"code\":200,\"data\": [{\"title\": \"财富管家-初级版\", \"img\": \"https://img0.baidu.com/it/u=652700042,3999938957&fm=253&fmt=auto&app=138&f=PNG?w=750&h=500\", \"basic\": \"%s\", \"notice\": \"30天｜10元起购｜中低风险｜债券基金\"}, {\"title\": \"财富管家-中级版\", \"img\": \"https://img0.baidu.com/it/u=537070019,4158887229&fm=253&fmt=auto&app=138&f=JPEG?w=657&h=370\", \"basic\": \"%s\", \"notice\": \"30天｜20元起购｜中低风险｜债券基金\"}, {\"title\": \"财富管家-高级版\", \"img\": \"https://img1.baidu.com/it/u=4007786138,3371888956&fm=253&fmt=auto&app=138&f=JPEG?w=888&h=500\", \"basic\": \"%s\", \"notice\": \"30天｜30元起购｜中低风险｜债券基金\"}, {\"title\": \"财富管家-终身版\", \"img\": \"https://img0.baidu.com/it/u=2920245905,2739364058&fm=253&fmt=auto?w=640&h=392\", \"basic\": \"%s\", \"notice\": \"120天｜100元起购｜中低风险｜债券基金\"}]}", lv1, lv2, lv3, lv4);
    }

    private String get_annual( String Product) {
        String url = String.format("http://%s%s?product_name=%s", "product-detail", "/detail/get_annual", Product);
        System.out.println(url);
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(url, String.class);
        } catch (RestClientException e) {
            System.err.println("An error occurred while making a REST call: " + e.getMessage());
            e.printStackTrace();
            return "0.00%";
        }

    }

    @CrossOrigin()
    @GetMapping("/detail/get_product_detail")
    public Map<String, Object> get_product_detail() {

        Map<String, Object> info_lv1 = new HashMap<>();
        info_lv1.put("title", "财富管家-初级版");
        info_lv1.put("detail", "财富管家-初级版主要投向低等级<span style='color: #1976D2'>短期债券</span>，远离股市波动。所投债券资产久期较短且信用等级较高，相对能更好地控制风险，力争匹配投资者的<span style='color: #1976D2'>短期闲钱理财需求</span>。");
        Map<String, Object> info_lv2 = new HashMap<>();
        info_lv2.put("title", "财富管家-中级版");
        info_lv2.put("detail", "财富管家-中级版主要投向中高等级<span style='color: #1976D2'>中短期债券</span>，远离股市波动。所投债券资产久期较短且信用等级较高，相对能更好地控制风险，力争匹配投资者的<span style='color: #1976D2'>短期闲钱理财需求</span>。");
        Map<String, Object> info_lv3 = new HashMap<>();
        info_lv3.put("title", "财富管家-初级版");
        info_lv3.put("detail", "财富管家-高级版主要投向中高等级<span style='color: #1976D2'>中短期债券</span>，远离股市波动。所投债券资产久期较短且信用等级较高，相对能更好地控制风险，力争匹配投资者的<span style='color: #1976D2'>短期闲钱理财需求</span>。");
        Map<String, Object> info_lv4 = new HashMap<>();
        info_lv4.put("title", "财富管家-初级版");
        info_lv4.put("detail", "财富管家-终身版主要投向中高等级<span style='color: #1976D2'>中短期债券</span>，远离股市波动。所投债券资产久期较短且信用等级较高，相对能更好地控制风险，力争匹配投资者的<span style='color: #1976D2'>短期闲钱理财需求</span>。");
        List<Map<String, Object>> infos = Arrays.asList(info_lv1, info_lv2, info_lv3, info_lv4);
        Map<String, Object> ret = new HashMap<>();

        ret.put("code", 200);
        ret.put("msg", "ok");
        ret.put("data", infos);
        return ret;
    }

    @CrossOrigin()
    @GetMapping("/api")
    public ResponseEntity<String> apiRedirect(@RequestParam String entry, @RequestParam(required = false) String color_mark, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        if (color_mark != null) {
            headers.add("X-Nsf-Mark", color_mark);
        }
        String url = "http://" + entry + "?" + request.getQueryString();
        System.out.println(url);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class, headers);
            String responseBody = new String(response.getBody(), StandardCharsets.UTF_8);
            return new ResponseEntity<>(responseBody, response.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @CrossOrigin()
    @GetMapping("/test_discovery")
    public String apiRedirect() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = WebController.class.getClassLoader().getResourceAsStream("application.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String value = properties.getProperty("testhost");
        System.out.println("value: " + value);


        String url = "http://127.0.0.1:8999/info/get_version";
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(url, String.class);
        } catch (RestClientException e) {
            return "0.00%";
        }
    }

    @CrossOrigin()
    @GetMapping("/unstable")
    public ResponseEntity<String> unstable(@RequestParam(required = false, defaultValue = "500") int code,
                                           @RequestParam(required = false, defaultValue = "0.5") double chance) {
        if (chance == 1) {
            return ResponseEntity.status(code).body("Error!");
        }

        if (Math.random() < chance) {
            return ResponseEntity.status(code).body("Error!");
        } else {
            return ResponseEntity.ok("Work!");
        }
    }

    @CrossOrigin()
    @GetMapping("/delay_return")
    public String delayReturn(@RequestParam(required = false, defaultValue = "2") int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds); // 延迟指定的秒数
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Delayed response after " + seconds + " seconds";
    }

    @CrossOrigin()
    @GetMapping("/cpu_max")
    public String cpuMax() {
        new Thread(this::simulateCpuUsage).start();
        return "Processing...";
    }

    private void simulateCpuUsage() {
        while (true) {
            // 模拟CPU密集型任务
        }
    }

}

