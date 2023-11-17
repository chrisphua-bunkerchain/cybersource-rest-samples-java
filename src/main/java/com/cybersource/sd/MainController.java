package com.cybersource.sd;

import Api.PaymentsApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;
import com.cybersource.authsdk.core.ConfigException;
import com.cybersource.authsdk.core.MerchantConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.Properties;

@RestController
public class MainController {
    public static boolean userCapture = true;
    private static String responseCode = null;
    private static String status = null;
    private static Properties merchantProp;
    
    @Autowired
    private Configuration configuration;
    
    @GetMapping("/greeting")
    public String greeting() {
        return "Greetings from Cybersource!";
    }

    @PostMapping("/simpleAuthorizationInternet")
    public ResponseEntity simpleAuthorizationInternet(@RequestBody PaymentRequest paymentRequest) throws Exception {
        CreatePaymentRequest requestObj = new CreatePaymentRequest();

        Ptsv2paymentsClientReferenceInformation clientReferenceInformation = new Ptsv2paymentsClientReferenceInformation();
        clientReferenceInformation.code(paymentRequest.getClientReferenceInformation().getCode());
        requestObj.clientReferenceInformation(clientReferenceInformation);

        Ptsv2paymentsProcessingInformation processingInformation = new Ptsv2paymentsProcessingInformation();
        processingInformation.capture(false);
        if (userCapture) {
            processingInformation.capture(true);
        }

        requestObj.processingInformation(processingInformation);

        Ptsv2paymentsPaymentInformation paymentInformation = new Ptsv2paymentsPaymentInformation();
        Ptsv2paymentsPaymentInformationCard paymentInformationCard = new Ptsv2paymentsPaymentInformationCard();
        paymentInformationCard.number(paymentRequest.getPaymentInformationCard().getNumber().replaceAll("\\s+",""));
        paymentInformationCard.expirationMonth(paymentRequest.getPaymentInformationCard().getExpirationMonth());
        paymentInformationCard.expirationYear(paymentRequest.getPaymentInformationCard().getExpirationYear());
        if (paymentRequest.getPaymentInformationCard().getType() != null) paymentInformationCard.type(paymentRequest.getPaymentInformationCard().getType());
        if (paymentRequest.getPaymentInformationCard().getSecurityCode() != null) paymentInformationCard.securityCode(paymentRequest.getPaymentInformationCard().getSecurityCode());
        paymentInformation.card(paymentInformationCard);
        requestObj.paymentInformation(paymentInformation);

        Ptsv2paymentsOrderInformation orderInformation = new Ptsv2paymentsOrderInformation();
        Ptsv2paymentsOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsOrderInformationAmountDetails();
        orderInformationAmountDetails.totalAmount(paymentRequest.getOrderInformationAmountDetails().getTotalAmount());
        orderInformationAmountDetails.currency(paymentRequest.getOrderInformationAmountDetails().getCurrency());
        orderInformation.amountDetails(orderInformationAmountDetails);

        Ptsv2paymentsOrderInformationBillTo orderInformationBillTo = new Ptsv2paymentsOrderInformationBillTo();
        orderInformationBillTo.firstName(paymentRequest.getOrderInformationBillTo().getFirstName());
        orderInformationBillTo.lastName(paymentRequest.getOrderInformationBillTo().getLastName());
        orderInformationBillTo.address1(paymentRequest.getOrderInformationBillTo().getAddress1());
        orderInformationBillTo.address2(paymentRequest.getOrderInformationBillTo().getAddress2());
        orderInformationBillTo.locality(paymentRequest.getOrderInformationBillTo().getLocality());
        orderInformationBillTo.administrativeArea(paymentRequest.getOrderInformationBillTo().getAdministrativeArea());
        orderInformationBillTo.postalCode(paymentRequest.getOrderInformationBillTo().getPostalCode());
        orderInformationBillTo.country(paymentRequest.getOrderInformationBillTo().getCountry());
        orderInformationBillTo.email(paymentRequest.getOrderInformationBillTo().getEmail());
        orderInformationBillTo.phoneNumber(paymentRequest.getOrderInformationBillTo().getPhoneNumber());
        orderInformation.billTo(orderInformationBillTo);

        requestObj.orderInformation(orderInformation);

        PtsV2PaymentsPost201Response result = null;
        try {
            merchantProp = configuration.getMerchantDetails(paymentRequest.getMerchantId());
            ApiClient apiClient = new ApiClient();
            MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
            apiClient.merchantConfig = merchantConfig;

            PaymentsApi apiInstance = new PaymentsApi(apiClient);
            result = apiInstance.createPayment(requestObj);

            responseCode = apiClient.responseCode;
            status = apiClient.status;
            
            return ResponseEntity.ok(result);
        } catch (ApiException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getResponseBody());
        } catch (ConfigException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
