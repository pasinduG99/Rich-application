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
	
	$("#hidBillNOSave").val("");
	$("#formItem")[0].reset();
}


$(document).on("click", ".btnUpdate", function(event)
{
	$("#hidBillNOSave").val($(this).closest("tr").find('#hidBillNOUpdate').val());
	$("#username").val($(this).closest("tr").find('td:eq(0)').text());
	$("#ano").val($(this).closest("tr").find('td:eq(1)').text());
	$("#address").val($(this).closest("tr").find('td:eq(2)').text());
	$("#units").val($(this).closest("tr").find('td:eq(3)').text());
	$("#amount").val($(this).closest("tr").find('td:eq(4)').text());
});

$(document).on("click", ".btnRemove", function(event)
{
	$.ajax(
	{
	url : "BillGenerationAPI",
	type : "DELETE",
	data : "billno=" + $(this).data("billno"),
	dataType : "text",
	complete : function(response, status)
	{
		onBillDeleteComplete(response.responseText, status);
	}
	});
});

function onBillDeleteComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divBillsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error")
	{
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else
	{
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}



