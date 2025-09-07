function hideRecordAdder() {
    $("#recordAdder").hide("fast");
    $("#showRecordAdder").show("fast");
}

function showRecordAdder() {
    $("#recordAdder").show("fast");
    $("#showRecordAdder").hide("fast");
}

function showAddNewType() {
    $("#addNewTypeDiv").show("fast");
}

function loadRecord(all) {

}

function addNewType() {
    const newOption = document.createElement("option");
    newOption.value = newOption.text = $("#newTypeName").val();
    $("#addNewTypeOption").before(newOption);
    $("#purpose").val(newOption.text);

    $("$addNewTypeMsg").show("fast");
}

function submitRecordValidation(formData) {
    const emptyField = [];
    if (formData["recordType"] == null) {
        emptyField.push("Record Type");
    }
    if (formData["amount"] == "") {
        emptyField.push("Amount");
    }
    if (formData["dateTimeRadio"] == null) {
        emptyField.push("Date/Time");
    } else if (formData["dateTimeRadio"] == "userDefineTime"
               && formData["dateTimeBox"] == null) {
        emptyField.push("User Defined Date/Time");
    }

    const amountNotPositiveNum = (isNaN(formData["amount"]) || formData["amount"] <= 0);

    const n = emptyField.length;
    let errorMsg = "";
    if (n != 0) {
        let emptyFieldString = "Please fill in ";
        if (n == 1) {
            emptyFieldString += "\"" + emptyField[0] + "\".";
        } else {
            for (let i = 0; i < n - 2; i++) {
                emptyFieldString += "\"" + emptyField[i] + "\", ";
            }
            emptyFieldString += "\"" + emptyField[n - 2] + "\" and \"" + emptyField[n - 1] + "\".";
        }
        errorMsg += emptyFieldString;
    }
    if (amountNotPositiveNum) {
        if (errorMsg != "") {
            errorMsg += "\n";
        }
        errorMsg += "\"Amount\" should be a positive number. ";
    }

    if (n == 0 && !amountNotPositiveNum) {
        return true;
    } else {
        $("#submitRecordMsg").text(errorMsg);
        return false;
    }
}

function submitRecord() {
    const formDataRaw = $("#recordAdderForm").serializeArray();
    const formDataProceed = {};
    $.map(formDataRaw, function(n, i) {
            formDataProceed[n["name"]] = n["value"];
    });

    if (!submitRecordValidation(formDataProceed)) return;

    if (formDataProceed["dateTimeRadio"] == "autoTime") {
        formDataProceed["dateTime"] = new Date();
    } else {
        const date = new Date(formDataProceed["dateTimeBox"]);
        formDataProceed["dateTime"] = date.toISOString();
    }
    delete formDataProceed["dateTimeRadio"];
    delete formDataProceed["dateTimeBox"];

    $.ajax({
        type: 'POST',
        url: '/addRecord',
        data: JSON.stringify(formDataProceed),
        success: function(response) {
            $("#submitRecordMsg").show();
            $("#submitRecordMsg").text(response);
        },
        contentType: "application/json",
        dataType: 'json'
    });
}