package sese.responses;

import java.sql.Blob;

public class BillPdfResponse {
    private String billPdf; //base64 encoded

    public BillPdfResponse(String billPdf) {
        this.billPdf = billPdf;
    }

    public String getBillPdf() {
        return billPdf;
    }

    public void setBillPdf(String billPdf) {
        this.billPdf = billPdf;
    }
}
