<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<script language="javascript" type="text/javascript">
	
	function enableDateTime(){
		var x = document.getElementById("dateTimeCheckBox");
		var y = document.getElementById("dateTime");
		if (x.checked)
			y.disabled = true;
		else
			y.disabled = false;
	}
	function enablePercentage(){
		document.getElementById("percentage").disabled = false;
		document.getElementById("pricePerShare").disabled = true;
		document.getElementById("pricePerShareCheckbox").checked = false;
	}
	function enablePricePerShare(){
		document.getElementById("pricePerShare").disabled = false;
		document.getElementById("percentage").disabled = true;
		document.getElementById("percentageCheckbox").checked = false;
	}
	function enableStops(){
		if(document.getElementById("priceType").value=="Hidden Stop"){
			document.getElementById("pricePerShare").disabled = false;
			document.getElementById("percentage").disabled=true;	
			document.getElementById("percentageCheckbox").disabled=true;
			document.getElementById("pricePerShareCheckbox").disabled=true;	}

		else if(document.getElementById("priceType").value=="Trailing Stop"){
			document.getElementById("percentageCheckbox").disabled = false;
			document.getElementById("pricePerShareCheckbox").disabled = false;	
			if(document.getElementById("pricePerShareCheckbox").checked==true)
				enablePricePerShare();	
			else if(document.getElementById("percentageCheckbox").checked==true)
				enablePercentage();
		
		}
		else{
			document.getElementById("pricePerShare").disabled=true;
			document.getElementById("percentage").disabled=true;	
			document.getElementById("pricePerShareCheckbox").disabled=true;															
			document.getElementById("percentageCheckbox").disabled=true;	}	}
	
	function enablePriceTypes(){
		y = document.getElementById("orderType").value;
		if(y=="Sell"){
			document.getElementById("trailingStop").disabled=false;
			document.getElementById("hiddenStop").disabled=false;
		}
		else{ //If the Order Type is Buy
			document.getElementById("trailingStop").disabled = true;
			document.getElementById("hiddenStop").disabled = true;
			document.getElementById("pricePerShare").disabled = true;
			document.getElementById("percentage").disabled = true;
			document.getElementById("pricePerShareCheckbox").disabled = true;
			document.getElementById("percentageCheckbox").disabled = true;
			if(document.getElementById("trailingStop").selected==true 
					|| document.getElementById("hiddenStop").selected==true){
				document.getElementById("market").selected=true;
			}	
		}
	}
	function validate(){
	//	var x = document.getElementById("percentage").value;
	//	y=parseFloat(x);
	//	if(isNaN(y)){
			
		//	document.getElementById("errorStrPercentage").innerHTML="Error";
		//	return false;
		//	}
		//else{
			return true;}
	//	return false;
//	}
	
</script>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Record Order</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
    
    
    <%--Client List form --%>
    <%--First, the representative must select which client 
    he wants to place an order for --%>
	<c:choose>	
		<c:when test="${not empty clientList}">
			<form method="POST" action="recordOrder">
				<select name="clientId">
					<c:forEach items="${clientList}" var="client">
						<option value="${client.id}">${client.firstName} ${client.lastName}</option>
					</c:forEach>
				</select>
				<input type="submit" value="Submit"/>
			</form>
			
		</c:when>
		<c:otherwise>
		<label>You have no clients</label> 
		</c:otherwise>	
	</c:choose>
	
	
	
	<%--Once the client chooses a representative, he place an order--%>
	
	
	<c:choose>
		<c:when test="${not empty haveAccount and haveAccount == false }">
			This client has no accounts opened				
		</c:when>	
		<c:when test="${not empty haveAccount and haveAccount==true}">
		
		
		<label>Client's stocks</label>
		<c:choose><c:when test="${not empty hasStockList}">		
		<select name="hasStockList">
			<c:forEach items="${hasStockList}" var="hasStock">
				<option>${hasStock.accountId} ${hasStock.stockSymbol} Shares: ${hasStock.totalShares}</option>
			</c:forEach>
		
		</select>
		</c:when>
		<c:otherwise>
		<select disabled>
			<option>Empty</option>
		</select>
		</c:otherwise>
		</c:choose>
		<form method="POST"  onSubmit="return validate()" action="doRecordOrder">
		
			<input type="hidden" id="clientId" name="clientId" value="${clientId}"/>
		
		
			<%--Display the broker's clients --%>
			<label>Select an account</label>
			<select id="accountId" name="accountId">
				<c:forEach items="${accountList}" var="account">
					<option value="${account.id}">${account.id}</option>
			
				</c:forEach>
			</select>
		
		
			Select a Stock:
			<select name="stockSymbol">
				<c:forEach items="${stockList}" var="stock">
					<option value = "${stock.stockSymbol }">${stock.companyName } ${stock.stockSymbol }</option> 
				
				</c:forEach>
			</select><br>
			
			
			<%--Order Type --%>
			Order Type:
			<select id="orderType" name="orderType" onChange="enablePriceTypes()">
				<option id="buy" value="Buy">Buy</option>
				<option id="sell" value="Sell">Sell</option>
			</select><br>
			
			<%--Price Type --%>
			Price Type:
			<select id="priceType" name="priceType" onChange="enableStops()">
				<option id="market" value="Market">Market</option>
				<option id="marketOnClose" value="Market On Close">Market On Close</option>
				<option id="trailingStop" value="Trailing Stop" disabled>Trailing Stop</option>
				<option id="hiddenStop" value="Hidden Stop" disabled>Hidden Stop</option>
			</select><br>
			
			Date and Time
			
			<p style="color: red;">${errorStrDateTime}</p>
			(YYYY-MM-DD HH:MI:SS):<br>
			<input type="text" id="dateTime" name="dateTime"> 
			<input type ="checkbox" id="dateTimeCheckBox" name="now" value="now" onChange="enableDateTime()"/> Or Now<br>
			
			<%-- NumShares --%>	
			<p style="color: red;">${errorStrNumShares}</p>
			Number of Shares:
			<input type ="number" id="numShares" name="numShares" value="${numShares}"/><br>
			
			<%-- Price Per Share --%>
			<p style="color: red;">${errorStrPricePerShare}</p>
			Price Per Share:
			<input type="checkbox" id="pricePerShareCheckbox" name="pricePerShareCheckbox" value="checked" onChange="enablePricePerShare()" disabled/>
			<input type="text" id="pricePerShare" name="pricePerShare" value="${pricePerShare}" disabled/><br>
			
			<%-- Percentage --%>
			<p id="errorStrPercentage" style="color: red;">${errorStrPercentage}</p>
			<label for="percentage">Percentage:</label>
			<input type="checkbox" id="percentageCheckbox" name="percentageCheckbox" value="checked" onChange="enablePercentage()" disabled/>
			<input type="text" id="percentage" name="percentage" value="${percentage}" disabled/><br>
			
			<input type=submit value="Submit"/>
			
			</form>
		</c:when>
		<c:otherwise>
			<label>Select a client to record an order for.</label>
		</c:otherwise>
	</c:choose>
	<br>
	
	<a href="${pageContext.request.contextPath}/representatives">Return to representative view</a>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>