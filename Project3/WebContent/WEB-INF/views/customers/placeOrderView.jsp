<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
   	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
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
    
    
		
	<c:choose>
	<c:when test="${not empty hasStockList}">
		<%--Stock portfolio button should be to the right of the screen --%>
		<div style="float:right">
			<div class="portfoliolist">
				<button class="hasStock">Touch here for Stock Portfolio</button>
				<div class="portfoliolist-content">
				<table>
					<tr>
						<th>Account</th>
						<th>Stock</th>
						<th>Shares</th>
					</tr>
					<c:forEach items="${hasStockList}" var="hasStock">
					<tr>
						<td>${hasStock.accountId} </td>
						<td>${hasStock.stockSymbol}</td>
						<td>${hasStock.totalShares}</td>
					</tr>
					</c:forEach>
				</table>
				</div>
			</div>
		</div>
	</c:when>
	<c:otherwise>
		<div style="float:right">You currently have no stocks in your portfolio</div>
	</c:otherwise>
	</c:choose>
	<c:choose>
	<c:when test="${not empty bestSellerList }">
		<div  class="portfoliolist">
			<button class="hasStock">Hover here for best-selling stocks</button>
			<div class="portfoliolist-content">
				<div class="bestseller">
				<table >
				<c:forEach items="${bestSellerList }" var="bestSeller">
				<tr>
					<th>Symbol</th>
					<th>Total Sold</th>
				</tr>
				<tr>
					<td>
						${bestSeller.stockId }
					</td>
					<td >
						${bestSeller.totalShares }
					</td>
				</tr>
				</c:forEach>
				</table>	
				</div>
			</div>
		
		</div>
	</c:when>
	</c:choose>
	<c:choose>	
		<c:when test="${not empty accountList}">
			<form method="POST" action="doPlaceOrder">
			
			<ul class="placeorder">
			
			<%--AccountId --%>
			<li class="placeorder">
				<span class="placeorder">
					Account
					<span class="customertooltip">
						The account you wish to use
					</span>
				</span>
				<select class="placeorder" name="accountId">
					<c:forEach items="${accountList}" var="account">
						<option value="${account.id}">${account.id}</option>
					</c:forEach>
				</select>
			</li>
			
			<%--Stock Symbol --%>
			<li class="placeorder">
	
				<span class="placeorder">
					Stock
					<span class="customertooltip">
						Select the stock you want
					</span>
				</span>
	
				<select class="placeorder" name="stockSymbol">
					<c:forEach items="${stockList}" var="stock">
						<option value="${stock.stockSymbol }">
							${stock.companyName} ${stock.stockSymbol}
							 (${stock.numShares} in Market), 
					 		<fmt:formatNumber type="currency" value="${stock.pricePerShare}"/> 
					 		per share
						</option> 
					</c:forEach>
				</select>
			</li>
			
			
			<%--Order Type --%>
			<li class="placeorder">
	
				<span class="placeorder">
					Order Type
					<span class="customertooltip">
						Buy or sell  stock
					</span>
				</span>
	
				<select class="placeorder" id="orderType" name="orderType" onChange="enablePriceTypes()">
					<option id="buy" value="Buy">Buy</option>
					<option id="sell" value="Sell">Sell</option>
				</select>
			</li>

			<%--Price Type --%>
			<li class="placeorder">
	
				<span class="placeorder">
					Price Type
					<span class="customertooltip">
						Choose the type of order you wish to place
					</span>
				</span>

				<select class="placeorder" id="priceType" name="priceType" onChange="enableStops()">
					<option id="market" value="Market">Market</option>
					<option id="marketOnClose" value="Market On Close">Market On Close</option>
					<option id="trailingStop" value="Trailing Stop" disabled>Trailing Stop</option>
					<option id="hiddenStop" value="Hidden Stop" disabled>Hidden Stop</option>
				</select>
			</li>	
			
			<%-- NumShares --%>	
			<li class="placeorder">
			
				<span class="placeorder">
					Shares
					<span class="customertooltip">
						Choose how many shares you wish to buy/sell
					</span>
				</span>
			
				<input class="placeorder" type ="text" name="numShares" value="${numShares}" required/><br>
				<span style="color: red;">${errorStrNumShares}</span>
			</li>
			
			<%-- Price Per Share --%>
			<li class="placeorder">
			
				<span class="placeorder">
					Stop Price
					<input type="checkbox" class="placeorder" id="pricePerShareCheckbox" name="pricePerShareCheckbox" value="checked" onChange="enablePricePerShare()" disabled/>
					<span class="customertooltip">
						Select a stopping price for either a hidden or trailing stop.
					</span>
				</span>
				
				<input class="placeorder" type="text" id="pricePerShare" name="pricePerShare" value="${pricePerShare}" disabled/><br>
				
				<span style="color: red;">${errorStrPricePerShare}</span>
			</li>
			
			<%-- Percentage --%>
			<li class="placeorder">
				<span class="placeorder">
					Percentage
					<input type="checkbox" id="percentageCheckbox" name="percentageCheckbox" value="checked" onChange="enablePercentage()" disabled/>
					<span class="customertooltip">
						Select a trailing percentage for a trailing stop
					</span>
				</span>
				<input class="placeorder" type="text" id="percentage" name="percentage" value="${percentage}" disabled/><br>
				<span style="color: red;">${errorStrPercentage}</span>
			
			</li>
			<li class="placeorder">
				<input type=submit value="Submit"/>
			</li>
			</ul>
			
			</form>
		</c:when>
		<c:otherwise>
			<p>Cannot place an order without an account. Please talk to your broker to open one.</p>
		</c:otherwise>
	</c:choose>
	<a class="returnbtn" href="${pageContext.request.contextPath}/customers/orderList">Return to Order List</a>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>