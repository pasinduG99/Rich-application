/**
 * 
 */


$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	
	// Form validation-------------------
	var status = validateItemForm();
	if (status != true)
	{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	
	// If valid------------------------
	var type = ($("#hidBillNOSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
	{
		url : "BillGenerationAPI",
		type : type,
		data : $("#formBill").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			onBillSaveComplete(response.responseText, status);
		}
	});
});


function onBillSaveComplete(response, status)
{
	//Your code
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divBillsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	
	} else if (status == "error")
	{
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else	
	{
	$("#alertError").text("Unknown error while saving..");
	$("#alertError").show();
	}
	
	$("##hidBillNOSave").val("");
	$("#formItem")[0].reset();
}



