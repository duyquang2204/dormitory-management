package vn.iotstar.dormitory.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VNPAYConfig {

    @Value("${vnpay.tmn-code}")
    private String vnp_TmnCode;

    @Value("${vnpay.hash-secret}")
    private String vnp_HashSecret;

    @Value("${vnpay.url}")
    private String vnp_Url;

    @Value("${vnpay.return-url}")
    private String vnp_ReturnUrl;
    
    @Value("${vnpay.version}")
    private String vnp_Version;

    @Value("${vnpay.command}")
    private String vnp_Command;

    public String getTmnCode() {
        return vnp_TmnCode;
    }

    public String getHashSecret() {
        return vnp_HashSecret;
    }

    public String getUrl() {
        return vnp_Url;
    }

    public String getReturnUrl() {
        return vnp_ReturnUrl;
    }

    public String getVersion() {
        return vnp_Version;
    }

    public String getCommand() {
        return vnp_Command;
    }
}
