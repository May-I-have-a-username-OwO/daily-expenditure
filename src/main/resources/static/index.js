let goalType;
clickAllRecord();

let oldestYear;
getOldestDate();



function checkIfNewPurpose(value) {
    if (value == "newType") {
        $("#addNewTypeDiv").show("fast");
    }
}

function showRecordSelect(by) {
    $("#chooseMonthDiv").hide("fast");
    $("#chooseYearDiv").hide("fast");
    $("#chooseExactDateDiv").hide("fast");

    by.show("fast");
}

function hideRecordAdder() {
    $("#recordAdder").hide("fast");
    $("#showRecordAdder").show("fast");
}

function showRecordAdder() {
    $("#recordAdder").show("fast");
    $("#showRecordAdder").hide("fast");
}

function hideGoalDiv() {
    $("#expenditureGoalDiv").hide();
    $("#incomeGoalDiv").hide();
}

/*
function showHideDiv(div, button) {
    if (window.getComputedStyle(div).display === "none") {
        div.show("fast");
        button.text("up");
    } else {
        div.hide("fast");
        button.text("down");
    }
}
*/



function changeGoalType(gt) {
    goalType = gt;
    $("#expenditureGoalLabel").text(`${goalType} Goal: <$`);
    $("#incomeGoalLabel").text(`${goalType} Goal: >$`);

    $("#expenditureGoalDiv").show("fast");
    $("#incomeGoalDiv").show("fast");
}

function clickAllRecord() {
    showRecordSelect($("#nothing"));
    getAllRecord();
    hideGoalDiv();
}

function clickMonthRecord() {
    showRecordSelect($('#chooseMonthDiv'));
}

function clickYearRecord() {
    showRecordSelect($('#chooseYearDiv'));
    fillYearSelect();
}

function clickExactDateRecord() {
    showRecordSelect($('#chooseExactDateDiv'));
}



function addNewType() {
    const newOption = document.createElement("option");
    newOption.value = newOption.text = $("#newTypeName").val();
    $("#addNewTypeOption").before(newOption);
    $("#purpose").val(newOption.text);

    $("$addNewTypeMsg").show("fast");
}

function setGoal(goal, goalText, greaterOrLess) {
    goal = parseInt(goal).toFixed(2);
    goalText.text(`Goal: ${greaterOrLess}$${goal}`);
}



function getOldestDate() {
    $.ajax({
            type: "GET",
            url: "/record/oldestDate",
            success: function (result) {
                let d = new Date(result)
                oldestYear = d.getFullYear();
            }
    })
}

function getAllRecord() {
    $.ajax({
            type: "GET",
            url: "/allRecord",
            success: function(result) {
                fillRecordTable(result);
                $("#whichRecordMsg").text("All Records");
            }
        });
}

function getRecordByYear() {
    if ($("#yearInput").val != "") {
        let startDate = $("#yearInput").val() + "-01-01";
        let endDate = $("#yearInput").val() + "-12-31";
        getRecordByExactDate(startDate, endDate);

        changeGoalType("Yearly");
    }
}

function getRecordByMonth() {
    if ($("#monthInput").val()) {
        let startDate = new Date($("#monthInput").val());
        let endDate = new Date(startDate);
        endDate.setMonth(startDate.getMonth() + 1);
        endDate.setDate(0);
        getRecordByExactDate(startDate, endDate);

        changeGoalType("Monthly");
    }
}

function getRecordByExactDate(startDate, endDate) {
    let sd = new Date(startDate);
    let ed = new Date(endDate);

    ed.setDate(ed.getDate() + 1);
    ed.setMilliseconds(-1);

    const d = new Date();
    sd.setMinutes(d.getTimezoneOffset());
    ed.setMinutes(d.getTimezoneOffset());

    startDate = sd.toISOString();
    endDate = ed.toISOString();

    const query = new URLSearchParams({
        start: startDate,
        end: endDate
        });
    $.ajax({
            type: "GET",
            url: "/record?" + query.toString(),
            success: function(result) {
                fillRecordTable(result);
                $("#whichRecordMsg").text(`Records from ${sd.toLocaleDateString()} to ${ed.toLocaleDateString()}`);
            }
        });
}



function fillYearSelect() {
    let options = "";
    const d = new Date();
    const y = d.getFullYear();

    for (let i = y; i >= oldestYear; i--) {
        options += `<option value='${i}'>${i}</option>\n`;
    }

    $("#yearInput").append(options);
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

    updateTotal(record);
}

function updateTotal(record) {
    let totalExpenditure = 0;
    let totalIncome = 0;
    record.forEach(function(value) {
        if (value.recordType == "income") {
            totalIncome += value.amount;
        } else {
            totalExpenditure += value.amount;
        }
    })

    $("#totalExpenditure").text("$" + totalExpenditure.toFixed(2));
    $("#totalIncome").text("$" + totalIncome.toFixed(2));
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
            emptyFieldString += `"${emptyField[0]}".`;
        } else {
            for (let i = 0; i < n - 2; i++) {
                emptyFieldString += `"${emptyField[i]}", `;
            }
            emptyFieldString += `"${emptyField[n - 2]}" and "${emptyField[n - 1]}".`;
        }
        errorMsg += emptyFieldString;
    }
    if (amountNotPositiveNum && formData["amount"] != "") {
        if (errorMsg != "") {
            errorMsg += "\n<br>\n";
        }
        errorMsg += '"Amount" should be a positive number. ';
    }

    if (n == 0 && !amountNotPositiveNum) {
        return true;
    } else {
        $("#submitRecordMsg").html(errorMsg);
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