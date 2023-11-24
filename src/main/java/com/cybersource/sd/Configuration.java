package com.cybersource.sd;

import org.springframework.beans.factory.annotation.Value;

import java.util.Properties;

@org.springframework.context.annotation.Configuration
public class Configuration {

    public String getActiveProfile() {
        return activeProfile;
    }
    
    @Value("${spring.profiles.active}")
    private String activeProfile;

    public String getKeyDirectory() {
        return keyDirectory;
    }

    @Value("${gp.key-directory}")
    private String keyDirectory;

    public String getEndPoint() {
        return endPoint;
    }

    @Value("${gp.endpoint}")
    private String endPoint;

    public Properties getMerchantDetails(String merchantId) {
        Properties props = new Properties();

        // HTTP_Signature = http_signature and JWT = jwt
        props.setProperty("authenticationType", "jwt");
        props.setProperty("merchantID", merchantId);
        props.setProperty("runEnvironment", getEndPoint());
//		props.setProperty("requestJsonPath", "src/main/resources/request.json");

        // MetaKey Parameters
//		props.setProperty("portfolioID", "");
        props.setProperty("useMetaKey", "false");

        // JWT Parameters
        props.setProperty("keyAlias", merchantId);
        props.setProperty("keyPass", merchantId);
        props.setProperty("keyFileName", merchantId);

        // P12 key path. Enter the folder path where the .p12 file is located.

        props.setProperty("keysDirectory", getKeyDirectory());
        // HTTP Parameters
//		props.setProperty("merchantKeyId", "08c94330-f618-42a3-b09d-e1e43be5efda");
//		props.setProperty("merchantsecretKey", "yBJxy6LjM2TmcPGu+GaJrHtkke25fPpUX+UY6/L/1tE=");
        // Logging to be enabled or not.
//		props.setProperty("enableLog", "true");
        // Log directory Path
        props.setProperty("logDirectory", "log");
        props.setProperty("logFilename", "cybs");

        // Log file size in KB
        props.setProperty("logMaximumSize", "5M");

        // OAuth related properties.
//        props.setProperty("enableClientCert", "false");
//        props.setProperty("clientCertDirectory", "src/main/resources");
//		props.setProperty("clientCertFile", "");
//		props.setProperty("clientCertPassword", "");
//		props.setProperty("clientId", "");
//		props.setProperty("clientSecret", "");

        return props;

    }
}
