package Data;

import java.util.Properties;

public class Configuration {
    public static Properties getMerchantDetails(String merchantId) {
        Properties props = new Properties();

        // HTTP_Signature = http_signature and JWT = jwt
        props.setProperty("authenticationType", "jwt");
        props.setProperty("merchantID", merchantId);
        props.setProperty("runEnvironment", "apitest.cybersource.com");
//		props.setProperty("requestJsonPath", "src/main/resources/request.json");

        // MetaKey Parameters
//		props.setProperty("portfolioID", "");
        props.setProperty("useMetaKey", "false");

        // JWT Parameters
        props.setProperty("keyAlias", merchantId);
        props.setProperty("keyPass", merchantId);
        props.setProperty("keyFileName", merchantId);

        // P12 key path. Enter the folder path where the .p12 file is located.

        props.setProperty("keysDirectory", "src/main/resources");
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
        props.setProperty("enableClientCert", "false");
        props.setProperty("clientCertDirectory", "src/main/resources");
//		props.setProperty("clientCertFile", "");
//		props.setProperty("clientCertPassword", "");
//		props.setProperty("clientId", "");
//		props.setProperty("clientSecret", "");

        return props;

    }

    public static Properties getMerchantDetails() {
        Properties props = new Properties();

        // HTTP_Signature = http_signature and JWT = jwt
        props.setProperty("authenticationType", "jwt");
        props.setProperty("merchantID", "chrisphua1");
        props.setProperty("runEnvironment", "apitest.cybersource.com");
//		props.setProperty("requestJsonPath", "src/main/resources/request.json");

        // MetaKey Parameters
//		props.setProperty("portfolioID", "");
        props.setProperty("useMetaKey", "false");

        // JWT Parameters
        props.setProperty("keyAlias", "chrisphua1");
        props.setProperty("keyPass", "chrisphua1");
        props.setProperty("keyFileName", "chrisphua1");

        // P12 key path. Enter the folder path where the .p12 file is located.

        props.setProperty("keysDirectory", "src/main/resources");
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
        props.setProperty("enableClientCert", "false");
        props.setProperty("clientCertDirectory", "src/main/resources");
//		props.setProperty("clientCertFile", "");
//		props.setProperty("clientCertPassword", "");
//		props.setProperty("clientId", "");
//		props.setProperty("clientSecret", "");

        return props;

    }

    public static Properties getAlternativeMerchantDetails() {
        Properties props = new Properties();

        // HTTP_Signature = http_signature and JWT = jwt
        props.setProperty("authenticationType", "http_signature");
        props.setProperty("merchantID", "testrest_cpctv");
        props.setProperty("runEnvironment", "apitest.cybersource.com");
        props.setProperty("requestJsonPath", "src/main/resources/request.json");

        // JWT Parameters
        props.setProperty("keyAlias", "testrest_cpctv");
        props.setProperty("keyPass", "testrest_cpctv");
        props.setProperty("keyFileName", "testrest_cpctv");

        // P12 key path. Enter the folder path where the .p12 file is located.

        props.setProperty("keysDirectory", "src/main/resources");
        // HTTP Parameters
        props.setProperty("merchantKeyId", "964f2ecc-96f0-4432-a742-db0b44e6a73a");
        props.setProperty("merchantsecretKey", "zXKpCqMQPmOR/JRldSlkQUtvvIzOewUVqsUP0sBHpxQ=");
        // Logging to be enabled or not.
        props.setProperty("enableLog", "true");
        // Log directory Path
        props.setProperty("logDirectory", "log");
        props.setProperty("logFilename", "cybs");

        // Log file size in KB
        props.setProperty("logMaximumSize", "5M");

        return props;

    }

}
