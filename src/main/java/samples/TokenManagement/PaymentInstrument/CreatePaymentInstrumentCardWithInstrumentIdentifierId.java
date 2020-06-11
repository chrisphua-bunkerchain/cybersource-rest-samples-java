package samples.TokenManagement.PaymentInstrument;

import java.*;
import java.util.*;
import java.math.BigDecimal;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import com.google.common.base.Strings;
import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;

public class CreatePaymentInstrumentCardWithInstrumentIdentifierId {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		run();
	}

	public static TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedPaymentInstruments run() {
		String profileid = "93B32398-AD51-4CC2-A682-EA3E93614EB1";
	
		CreatePaymentInstrumentRequest requestObj = new CreatePaymentInstrumentRequest();

		TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedCard card = new TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedCard();
		card.expirationMonth("09");
		card.expirationYear("2017");
		card.type("visa");
		card.issueNumber("01");
		card.startMonth("01");
		card.startYear("2016");
		requestObj.card(card);

		TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedBuyerInformation buyerInformation = new TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedBuyerInformation();
		buyerInformation.companyTaxID("12345");
		buyerInformation.currency("USD");
		requestObj.buyerInformation(buyerInformation);

		TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedBillTo billTo = new TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedBillTo();
		billTo.firstName("John");
		billTo.lastName("Smith");
		billTo.company("Cybersource");
		billTo.address1("8310 Capital of Texas Highwas North");
		billTo.address2("Bluffstone Drive");
		billTo.locality("Austin");
		billTo.administrativeArea("TX");
		billTo.postalCode("78731");
		billTo.country("US");
		billTo.email("john.smith@test.com");
		billTo.phoneNumber("+44 2890447951");
		requestObj.billTo(billTo);

		TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedProcessingInformation processingInformation = new TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedProcessingInformation();
		processingInformation.billPaymentProgramEnabled(true);
		requestObj.processingInformation(processingInformation);

		Tmsv1paymentinstrumentsInstrumentIdentifier instrumentIdentifier = new Tmsv1paymentinstrumentsInstrumentIdentifier();
		instrumentIdentifier.id("7020000000001061135");
		requestObj.instrumentIdentifier(instrumentIdentifier);

		TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedPaymentInstruments result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			PaymentInstrumentApi apiInstance = new PaymentInstrumentApi(apiClient);
			result = apiInstance.createPaymentInstrument(profileid, requestObj);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	return result;
	}
}
