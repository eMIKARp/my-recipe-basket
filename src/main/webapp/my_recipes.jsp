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
                    <a href="index"><b>mójPrzepiśnik</b></a>
                    <a href="javascript:void(0);" class="icon" onclick="showTopNav()">
                        <i class="fa fa-bars"></i>
                    </a>
                </div>
                
                <a href="index">Strona główna</a> 
                <a href="my_recipes">Moje przepisy</a> 
                <a href="add">Dodaj przepis</a>
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <a href="logout">Wyloguj</a>
                    </c:when>
                    <c:otherwise>
                        <a href="login">Zaloguj</a>    
                    </c:otherwise>    
                </c:choose>
            </div>
        </div> 
        
        <div class="myRow">
            <div class="mySide" >
                <div class="navHeader" style="justify-content: center; font-size: 17px; background-color: #1abc9c;">
                    <a href="#"><b>Czego szukasz?</b></a>
                    <a href="javascript:void(0);" class="icon" onclick="showSideNav()">
                     <i class="fa fa-bars"></i>
                    </a>
                </div>
                <div class="mySideNav" id="mySideNav">
                    <a href="#">Kategoria 1</a> 
                    <a href="#">Kategoria 2</a> 
                    <a href="#">Kategoria 3</a> 
                    <a href="#">Kategoria 4</a> 
                </div>
            </div>
            <div class="myMain">
                 <c:if test="${not empty requestScope.recipes}">
                    <c:forEach var="recipe" items="${requestScope.recipes}">    
                        <div class="myRow">
                          <div class="myRecipe">  
                              <h2 style="margin-top:6px;"><c:out value="${recipe.name   }"/></h2>
                              <p style="color:grey;">Kategorie: 
                                  <c:forEach var="category" items="${recipe.category}" >
                                      <c:out value="${category.name}"/>
                                  </c:forEach>
                              </p>    
                            <div style="padding: 10px;">
                                <p><c:out value="${recipe.description}"/></p>
                            </div>
                            <p style="color:grey;">Dodano przez <c:out value="${recipe.user.username}"/>, Dnia: <fmt:formatDate value="${recipe.timestamp}" pattern="dd/MM/YYYY"/></p>
                            <div class="myRow">
                                <a href="<c:out value="${recipe.ulr}"/>" class="btn btn-primary btn-info" style="margin: 5px; padding-left: 20px; padding-right: 20px;">Przejdź do strony</a>
                                <a href="#" class="btn btn-primary btn-warning" style="margin: 5px;">Wrzuć na główną</a>
                                <a href="#" class="btn btn-primary btn-primary" style="margin: 5px;">Usuń z przepiśnika</a>
                            </div>    
                        </div>  
                        </div>
                    </c:forEach>    
                </c:if>    
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
