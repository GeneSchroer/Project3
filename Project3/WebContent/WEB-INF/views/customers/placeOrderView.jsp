<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script language="javascript" type="text/javascript">
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
		
		

</script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/customerStyle.css"/>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Place an Order</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
    <h3>Place an order</h3>
    
    
		<p>Stocks in portfolio:</p>
	<c:choose>
	<c:when test="${not empty hasStockList}">
		<select name="stockToSell">
			<c:forEach items="${hasStockList}" var="hasStock">
				<option value="${hasStock.stockSymbol}">Account: ${hasStock.accountId} 
														Stock: ${hasStock.stockSymbol}
														Shares Available: ${hasStock.totalShares}
														Current Price: $ ${hasStock.pricePerShare }</option>
			</c:forEach>
		</select><br>
	</c:when>
	<c:otherwise>
		<p>No stocks in your portfolio</p>
	</c:otherwise>
	</c:choose>
	
	<c:choose>	
		<c:when test="${not empty accountList}">
			<form method="POST" action="doPlaceOrder">
			Select an account to use
			<select name="accountId">
				<c:forEach items="${accountList}" var="account">
					<option value="${account.id}">${account.id}</option>
				</c:forEach>
			</select><br>
			
			<%--Stock Symbol --%>
			Select a Stock:
			<select name="stockSymbol">
				<c:forEach items="${stockList}" var="stock">
					<option value="${stock.stockSymbol }">${stock.companyName } ${stock.stockSymbol} (${stock.numShares} in Market), $ ${stock.pricePerShare} per share</option> 
					
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
			
			<%-- NumShares --%>	
			<p style="color: red;">${errorStrNumShares}</p>
			Number of Shares:
			<input type ="text" name="numShares" value="${numShares}"/><br>
			
			<%-- Price Per Share --%>
			<p style="color: red;">${errorStrPricePerShare}</p>
			Price Per Share:
			<input type="checkbox" id="pricePerShareCheckbox" name="pricePerShareCheckbox" value="checked" onChange="enablePricePerShare()" disabled/>
			<input type="text" id="pricePerShare" name="pricePerShare" value="${pricePerShare}" disabled/><br>
			
			<%-- Percentage --%>
			<p style="color: red;">${errorStrPercentage}</p>
			Percentage:
			<input type="checkbox" id="percentageCheckbox" name="percentageCheckbox" value="checked" onChange="enablePercentage()" disabled/>
			<input type="text" id="percentage" name="percentage" value="${percentage}" disabled/><br>
			
			<input type=submit value="Submit"/>
			</form>
		</c:when>
		<c:otherwise>
			<p>Cannot place an order without an account. Please talk to your broker to open one.</p>
		</c:otherwise>
	</c:choose>
	<a href="${pageContext.request.contextPath}/customers/orderList">Return to Order List</a>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>