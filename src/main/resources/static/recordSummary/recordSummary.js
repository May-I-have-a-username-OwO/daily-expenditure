let goalType;
clickAllRecord();

let oldestYear;
getOldestDate();



function showRecordSelect(by) {
    $("#chooseMonthDiv").hide("fast");
    $("#chooseYearDiv").hide("fast");
    $("#chooseExactDateDiv").hide("fast");

    by.show("fast");
}

function hideGoalDiv() {
    $("#expenditureGoalDiv").hide();
    $("#incomeGoalDiv").hide();
}



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
