package com.aid.daily_expenditure;

import jakarta.validation.constraints.*;

import java.time.ZonedDateTime;

public class AddRecordInput {
    @NotBlank(message = "Record Type should not be blank.")
    @Pattern(regexp = "^(expenditure|income)", message = "RecordType should be either income or expenditure. ")
    private String recordType;

    @NotBlank(message = "Amount should not be blank.")
    @Positive(message = "Amount should be a positive integer.")
    private Integer amount;

    @NotBlank(message = "DateTime should not be blank.")
    @PastOrPresent(message = "DateTime should not be in the future.")
    private ZonedDateTime dateTime;

    private String purpose;

    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }
}
