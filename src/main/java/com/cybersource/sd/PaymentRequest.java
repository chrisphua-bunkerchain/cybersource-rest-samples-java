package com.cybersource.sd;

import Model.*;

public class PaymentRequest {
    private Ptsv2paymentsPaymentInformationCard paymentInformationCard;
    
    private Ptsv2paymentsOrderInformationAmountDetails orderInformationAmountDetails;

    private Ptsv2paymentsClientReferenceInformation clientReferenceInformation;

    private Ptsv2paymentsOrderInformationBillTo orderInformationBillTo;

    public Ptsv2paymentsPaymentInformationCard getPaymentInformationCard() {
        return paymentInformationCard;
    }

    public void setPaymentInformationCard(Ptsv2paymentsPaymentInformationCard paymentInformationCard) {
        this.paymentInformationCard = paymentInformationCard;
    }
    
    public Ptsv2paymentsOrderInformationAmountDetails getOrderInformationAmountDetails() {
        return orderInformationAmountDetails;
    }

    public void setOrderInformationAmountDetails(Ptsv2paymentsOrderInformationAmountDetails orderInformationAmountDetails) {
        this.orderInformationAmountDetails = orderInformationAmountDetails;
    }

    public Ptsv2paymentsClientReferenceInformation getClientReferenceInformation() {
        return clientReferenceInformation;
    }

    public void setClientReferenceInformation(Ptsv2paymentsClientReferenceInformation clientReferenceInformation) {
        this.clientReferenceInformation = clientReferenceInformation;
    }

    public Ptsv2paymentsOrderInformationBillTo getOrderInformationBillTo() {
        return orderInformationBillTo;
    }

    public void setOrderInformationBillTo(Ptsv2paymentsOrderInformationBillTo orderInformationBillTo) {
        this.orderInformationBillTo = orderInformationBillTo;
    }
}
