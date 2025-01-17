package samples.RecurringBillingSubscriptions.Plans;

import Api.PlansApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.InlineResponse200;
import com.cybersource.authsdk.core.MerchantConfig;

import java.lang.invoke.MethodHandles;
import java.util.Properties;

public class GetListOfPlans {
    private static String responseCode = null;
    private static String responseStatus = null;
    private static Properties merchantProp;

    public static void WriteLogAudit(int status) {
        String filename = MethodHandles.lookup().lookupClass().getSimpleName();
        System.out.println("[Sample Code Testing] [" + filename + "] " + status);
    }

    public static void main(String args[]) throws Exception {
        run();
    }

    private static InlineResponse200 run() {
        int offset = 0;
        int limit = 100;
        String code = null;
        String status = null;
        String name = null;

        InlineResponse200 result = null;

        try {
            merchantProp = Configuration.getMerchantDetails();
            ApiClient apiClient = new ApiClient();
            MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
            apiClient.merchantConfig = merchantConfig;

            PlansApi apiInstance = new PlansApi(apiClient);
            result = apiInstance.getPlans(null, null, null, null, null);

            responseCode = apiClient.responseCode;
            responseStatus = apiClient.status;
            System.out.println("ResponseCode :" + responseCode);
            System.out.println("ResponseMessage :" + responseStatus);
            System.out.println(result);
            WriteLogAudit(Integer.parseInt(responseCode));
        } catch (ApiException e) {
            e.printStackTrace();
            WriteLogAudit(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
