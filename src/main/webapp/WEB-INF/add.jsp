<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" type="text/css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resources/css/styles.css" type="text/css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Indie+Flower" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <title>mójPrzepiśnik</title>
    </head>

    <body>
        <div class="myHeader">
            <div class="myRow">
                <h2>Witaj w aplikacji &nbsp</h2>
                <h2 style="font-family: 'Indie Flower', cursive;"><b>mójPrzepiśnik</b></h2>
            </div>    
            <h6>Przygotuj na dzisiaj coś wyjątkowego !</h6>
        </div>
        
        <div class="myRow" style="position: sticky; position: -webkit-sticky; top: 0;">
            
            <div class="myTopNav" id="myTopNav">
                <div class="navHeader" style="font-family: 'Indie Flower', cursive;"> 
                    <a href="${pageContext.request.contextPath}"><b>mójPrzepiśnik</b></a>
                    <a href="javascript:void(0);" class="icon" onclick="showTopNav()">
                        <i class="fa fa-bars"></i>
                    </a>
                </div>
                
                <a href="${pageContext.request.contextPath}">Strona główna</a> 
                <a href="${pageContext.request.contextPath}/my_recipes">Moje przepisy</a> 
                <a href="${pageContext.request.contextPath}/add">Dodaj przepis</a>
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <a href="${pageContext.request.contextPath}/logout">Wyloguj</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/login">Zaloguj</a>    
                    </c:otherwise>    
                </c:choose>
            </div>
        </div>  
        
        <div class="myColumn" style="background-color: #f1f1f1;">
            <div class="myAddForm">
                <form action="add" method="post">
                        <h3 class="form-signin-heading">Dodaj nowy przepis</h3>
                        <input name="inputRecipeName" type="text" class="form-control" style="margin-bottom: 5px;" placeholder="Co dodajesz?" required autofocus>
                        <select multiple name="inputCategory" class="form-control" style="margin-bottom: 5px; color:grey;" required autofocus>
                            <c:if test="${not empty requestScope.categories}">
                                <c:forEach var="category" items="${requestScope.categories}">
                                    <option>${category.name}</option> 
                                </c:forEach>
                            </c:if>
                        </select>
                        <input name="inputUrl" type="url" class="form-control" style="margin-bottom: 5px;" placeholder="URL" required autofocus>
                        <textarea name="inputDescription" rows="5" class="form-control" style="margin-bottom: 5px;" placeholder="Opis" required></textarea>
                        <button class="btn btn-md btn-block" style="margin-bottom: 10px; background-color: #1abc9c; color:white" type="submit">Dodaj przepis</button>
                </form>
            </div>    
        </div>
        
        <div class="myFooter">
            <h6>Copyright &copy; Emil Karpowicz</h6>
        </div>
        
        <script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
        <script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
        <script src="resources/js/bootstrap.min.js"></script>
 
        <script>
        function showTopNav() {
            var x = document.getElementById("myTopNav");
            if (x.className === "myTopNav") {
                x.className += " responsive";
            } else {
                x.className = "myTopNav";
            }
        }
        </script>
        <script>
        function showSideNav() {
            var x = document.getElementById("mySideNav");
            if (x.className === "mySideNav") {
                x.className += " responsive";
            } else {
                x.className = "mySideNav";
            }
        }
        </script>
        
    </body>
</html>
