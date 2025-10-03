getAllRecord();

function showAndHide(show, hide) {
    show.show("fast");
    hide.hide("fast");
}

function showMonthInput() {
    $('#chooseMonthDiv').show('fast');

}

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

function addNewType() {
    const newOption = document.createElement("option");
    newOption.value = newOption.text = $("#newTypeName").val();
    $("#addNewTypeOption").before(newOption);
    $("#purpose").val(newOption.text);

    $("$addNewTypeMsg").show("fast");
}

function getAllRecord() {
    $.ajax({
            type: "GET",
            url: "/allRecord",
            success: function(result) {
                fillRecordTable(result);
            }
        });
}

function getRecordByMonth() {
    let startDate = new Date($("#monthInput").val());
    const d = new Date();
    startDate.setMinutes(d.getTimezoneOffset());
    let endDate = new Date(startDate);
    endDate.setMonth(startDate.getMonth() + 1);
    endDate.setMilliseconds(-1);
    getRecordByExactDate(startDate, endDate);
}

function getRecordByExactDate(startDate, endDate) {
    let d = new Date(startDate);
    startDate = d.toISOString(d);
    d = new Date(endDate);
    endDate = d.toISOString(d);

    const query = new URLSearchParams({
        start: startDate,
        end: endDate
        });
    $.ajax({
            type: "GET",
            url: "/record?" + query.toString(),
            success: function(result) {
                fillRecordTable(result);
            }
        });
}

function fillRecordTable(record) {
    $("#recordTableBody").empty();

    record.forEach(function(value) {
        let row = document.createElement("tr");
        let rowData = "";
        rowData += "<td>" + value.recordType + "</td>";
        rowData += "<td>$" + value.amount.toFixed(2) + "</td>";
        const d = new Date(value.dateTime);
        rowData += "<td>" + d + "</td>";
        rowData += "<td>" + value.purpose + "</td>";
        rowData += "<td>" + value.remark + "</td>";
        row.innerHTML = rowData;
        $("#recordTableBody").append(row);
    });
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
        type: "POST",
        url: "/addRecord",
        data: JSON.stringify(formDataProceed),
        success: function(response) {
            $("#submitRecordMsg").show();
            $("#submitRecordMsg").text(response);
        },
        contentType: "application/json",
        dataType: "json"
    });
}