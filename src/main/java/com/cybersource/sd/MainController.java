package com.cybersource.sd;

import Api.CreditApi;
import Api.PaymentsApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;
import com.cybersource.authsdk.core.ConfigException;
import com.cybersource.authsdk.core.MerchantConfig;
import com.cybersource.ws.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
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
        paymentInformationCard.number(paymentRequest.getPaymentInformationCard().getNumber().replaceAll("\\s+", ""));
        paymentInformationCard.expirationMonth(paymentRequest.getPaymentInformationCard().getExpirationMonth());
        paymentInformationCard.expirationYear(paymentRequest.getPaymentInformationCard().getExpirationYear());
        if (paymentRequest.getPaymentInformationCard().getType() != null)
            paymentInformationCard.type(paymentRequest.getPaymentInformationCard().getType());
        if (paymentRequest.getPaymentInformationCard().getSecurityCode() != null)
            paymentInformationCard.securityCode(paymentRequest.getPaymentInformationCard().getSecurityCode());
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

    @PostMapping("/credit")
    public ResponseEntity credit(@RequestBody PaymentRequest paymentRequest) throws Exception {
        CreateCreditRequest requestObj = new CreateCreditRequest();

        Ptsv2paymentsClientReferenceInformation clientReferenceInformation = new Ptsv2paymentsClientReferenceInformation();
        clientReferenceInformation.code(paymentRequest.getClientReferenceInformation().getCode());
        requestObj.clientReferenceInformation(clientReferenceInformation);
        
        Ptsv2paymentsidrefundsPaymentInformation paymentInformation = new Ptsv2paymentsidrefundsPaymentInformation();
        Ptsv2paymentsidrefundsPaymentInformationCard paymentInformationCard = new Ptsv2paymentsidrefundsPaymentInformationCard();
        paymentInformationCard.number(paymentRequest.getPaymentInformationCard().getNumber().replaceAll("\\s+", ""));
        paymentInformationCard.expirationMonth(paymentRequest.getPaymentInformationCard().getExpirationMonth());
        paymentInformationCard.expirationYear(paymentRequest.getPaymentInformationCard().getExpirationYear());
        if (paymentRequest.getPaymentInformationCard().getType() != null)
            paymentInformationCard.type(paymentRequest.getPaymentInformationCard().getType());
        if (paymentRequest.getPaymentInformationCard().getSecurityCode() != null)
            paymentInformationCard.securityCode(paymentRequest.getPaymentInformationCard().getSecurityCode());
        paymentInformation.card(paymentInformationCard);
        requestObj.paymentInformation(paymentInformation);

        Ptsv2paymentsidrefundsOrderInformation orderInformation = new Ptsv2paymentsidrefundsOrderInformation();
        Ptsv2paymentsidcapturesOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsidcapturesOrderInformationAmountDetails();
        orderInformationAmountDetails.totalAmount(paymentRequest.getOrderInformationAmountDetails().getTotalAmount());
        orderInformationAmountDetails.currency(paymentRequest.getOrderInformationAmountDetails().getCurrency());
        orderInformation.amountDetails(orderInformationAmountDetails);

        Ptsv2paymentsidcapturesOrderInformationBillTo orderInformationBillTo = new Ptsv2paymentsidcapturesOrderInformationBillTo();
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

        PtsV2CreditsPost201Response result = null;
        try {
            merchantProp = configuration.getMerchantDetails(paymentRequest.getMerchantId());
            ApiClient apiClient = new ApiClient();
            MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
            apiClient.merchantConfig = merchantConfig;

            CreditApi apiInstance = new CreditApi(apiClient);
            result = apiInstance.createCredit(requestObj);

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

    @PostMapping("/simpleOrder/sale")
    public ResponseEntity authAndCapture(@RequestBody SimplePaymentRequest simplePaymentRequest) throws Exception {
        HashMap<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("ccAuthService_run", "true");
        requestMap.put("ccCaptureService_run", "true");
        requestMap.put("merchantReferenceCode", simplePaymentRequest.getMerchantReferenceCode());
        requestMap.put("billTo_firstName", simplePaymentRequest.getFirstName());
        requestMap.put("billTo_lastName", simplePaymentRequest.getLastName());
        requestMap.put("billTo_street1", simplePaymentRequest.getStreet1());
        requestMap.put("billTo_street2", simplePaymentRequest.getStreet2());
        requestMap.put("billTo_city", simplePaymentRequest.getCity());
        requestMap.put("billTo_state", simplePaymentRequest.getState());
        requestMap.put("billTo_postalCode", simplePaymentRequest.getPostalCode());
        requestMap.put("billTo_country", simplePaymentRequest.getCountry());
        requestMap.put("billTo_email", simplePaymentRequest.getEmail());
        requestMap.put("billTo_phoneNumber", simplePaymentRequest.getPhoneNumber());
        requestMap.put("card_accountNumber", simplePaymentRequest.getAccountNumber());
        requestMap.put("card_expirationMonth", simplePaymentRequest.getExpirationMonth());
        requestMap.put("card_expirationYear", simplePaymentRequest.getExpirationYear());
        requestMap.put("card_cardType", simplePaymentRequest.getCardType());
        requestMap.put("card_cvNumber", simplePaymentRequest.getCvNumber());
        requestMap.put("purchaseTotals_currency", simplePaymentRequest.getCurrency());
        requestMap.put("purchaseTotals_grandTotalAmount", simplePaymentRequest.getGrandTotalAmount());

        Properties merchantProperties = new Properties();
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(simplePaymentRequest.getMerchantId() + "." + configuration.getActiveProfile() + ".properties");
        if (in == null) {
            throw new RuntimeException("Unable to load " + simplePaymentRequest.getMerchantId() + ".properties file");
        }
        merchantProperties.load(in);
        Map<String, String> replyMap = Client.runTransaction(requestMap, merchantProperties);
        return ResponseEntity.ok(replyMap);
    }
}
