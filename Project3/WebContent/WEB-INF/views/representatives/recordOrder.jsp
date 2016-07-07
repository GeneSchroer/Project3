<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/representativeForms.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/representativeStyle.css">
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
    
    <h3>Record Order For Client</h3>
    <%--Client List form --%>
    <%--First, the representative must select which client 
    he wants to place an order for --%>
	<c:choose>	
		<c:when test="${not empty clientList}">
			<form method="POST" action="recordOrder">
				<div class="managerform">
				<ul>
					<li>
						<select style="position:relative;" name="clientId">
							<c:forEach items="${clientList}" var="client">
								<option value="${client.id}">${client.firstName} ${client.lastName}</option>
							</c:forEach>
						</select>
						<input type="submit" value="Submit"/>
					</li>					
				</ul>
				</div>
			</form>
			
		
	
	
	
	<%--Once the client chooses a representative, he place an order--%>
	
	
	<c:choose>
		<c:when test="${not empty haveAccount and haveAccount == false }">
			<p class="nonform">This client has no accounts opened</p>				
		</c:when>	
		<c:when test="${not empty haveAccount and haveAccount==true}">
			
		<form method="POST"  onSubmit="return validate()" action="doRecordOrder">
		
			<input type="hidden" id="clientId" name="clientId" value="${clientId}"/>
			<div class="managerform">
			<ul>
				<li>
					<span style="width:130px;"class="nonform">
					Client's stocks
					<span class="managertooltip">
						List of all of a client's stocks
					</span>
				</span>
			<c:choose>
				<c:when test="${not empty hasStockList}">		
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
				
				</li>
			
				<%--Display the broker's clients --%>
				<li>
					<span style="width: 110px; margin-bottom:5px;">
						Account
						<span class="managertooltip">
							Select the account to you wish to record the order for
						</span>
					</span>
					<select id="accountId" name="accountId">
						<c:forEach items="${accountList}" var="account">
							<option value="${account.id}">${account.id}</option>
						</c:forEach>
					</select>
				</li>				
				<li>		
					<span style="width: 110px; margin-bottom:5px;">
						Stock
						<span class="managertooltip">
							Select the stock to use
						</span>
					</span>
					<select name="stockSymbol">
						<c:forEach items="${stockList}" var="stock">
							<option value = "${stock.stockSymbol }">${stock.companyName } ${stock.stockSymbol }</option> 
						
						</c:forEach>
					</select>
				</li>
				
				<%--Order Type --%>
				<li>
					<span style="width: 110px; margin-bottom:5px;">
						Order Type
						<span class="managertooltip">
							Choose whether to buy or sell a stock
						</span>
					</span>
					<select id="orderType" name="orderType" onChange="enablePriceTypes()">
						<option id="buy" value="Buy">Buy</option>
						<option id="sell" value="Sell">Sell</option>
					</select><br>
				</li>			
				<%--Price Type --%>
				<li>
					<span style="width: 110px;">
						Price Type
						<span class="managertooltip">
							Choose which type of order to place
						</span>
					</span>
					<select id="priceType" name="priceType" onChange="enableStops()">
						<option id="market" value="Market">Market</option>
						<option id="marketOnClose" value="Market On Close">Market On Close</option>
						<option id="trailingStop" value="Trailing Stop" disabled>Trailing Stop</option>
						<option id="hiddenStop" value="Hidden Stop" disabled>Hidden Stop</option>
					</select><br>
				</li>
				
				<li>
					<span class="errordetails">
						${errorStrDateTime}
					</span>
					<span style="width: 110px;">
						DateTime
						<span class="managertooltip">
							Date the order was placed
						</span>
					</span>
					
					<div style="position:absolute; display:inline-block;">
					<input style=""type="text" id="dateTime" name="dateTime" placeholder="YYYY-MM-DD"> 
					<input style="position:absolute; margin-left: 240px" type ="checkbox" id="dateTimeCheckBox" name="now" value="now" onChange="enableDateTime()"/>
					<span style="position:absolute;margin-left: 150px;"> 
						Or Now
						<span class="managertooltip">
							Place the order now
						</span>
					</span>
					</div>
				</li>

				<%-- NumShares --%>	
				<li>
					<span class="errordetails">
						${errorStrNumShares}
					</span>
					<span style="width: 110px;">
						Shares
						<span class="managertooltip">
							The number of shares to buy/sell
						</span>
					</span>
					<input type ="number" id="numShares" name="numShares" value="${numShares}" required/>
				</li>
				
				<%-- Price Per Share --%>
				<li>
					<span class="errordetails">
						${errorStrPricePerShare}
					</span>
					<span style="width: 110px;">
						Stop Price
						<span class="managertooltip">
							The stop price of a hidden/trailing stop
						</span>
					</span>
					<input type="checkbox" id="pricePerShareCheckbox" name="pricePerShareCheckbox" value="checked" onChange="enablePricePerShare()" disabled/>
					<input style="margin-left:20px;" type="text" id="pricePerShare" name="pricePerShare" value="${pricePerShare}" disabled/>
				</li>
				<%-- Percentage --%>
				<li>
					<span class="errordetails" id="errorStrPercentage" >
						${errorStrPercentage}
					</span>
					<span style="width: 110px;">
						Percentage
						<span class="managertooltip">
							The trailing percentage for a trailing stop
						</span>
					</span>
					<input type="checkbox" id="percentageCheckbox" name="percentageCheckbox" value="checked" onChange="enablePercentage()" disabled/>
					<input style="margin-left:20px;" type="text" id="percentage" name="percentage" value="${percentage}" disabled/><br>
				</li>
				<li>
					<input type=submit value="Submit"/>
				</li>
			</ul>
			</div>
			</form>
		</c:when>
		<c:otherwise>
			<p style="
				border-radius:3px;
				display:inline-block;
				background-color: #777777;
				color: #dddddd;
				margin: 5px 0px;
				padding:5px;
			">Select a client to record an order for.</p>
		</c:otherwise>
	</c:choose>
	
	</c:when>
		<c:otherwise>
			<br/>
			<p class="nonform">You have no clients</p> 
			<br/>
		</c:otherwise>	
	</c:choose>
	<br>
	
	<a class="returnbtn" href="${pageContext.request.contextPath}/representatives">Return to representative view</a>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>