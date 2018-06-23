
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

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
            <h4 style="margin-bottom: 0px;">Cześć <b><c:out value="${sessionScope.user.username}"/></b>.</h4>
            <div class="myRow">
                <h2 style="margin-top: 5px; margin-bottom: 0;">Witaj w aplikacji &nbsp</h2>
                <h2 style="margin-top: 5px; margin-bottom: 0; font-family: 'Indie Flower', cursive;"><b>mójPrzepiśnik</b></h2>
            </div>    
            <h5 style="margin-top:10px;">Przygotuj na dzisiaj coś wyjątkowego !</h5>
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
        
        
        <div class="myRow">
            <div class="mySide" >
                <div class="navHeader" style="justify-content: center; font-size: 17px; background-color: #1abc9c;">
                    <a href="#"><b>Czego szukasz?</b></a>
                    <a href="javascript:void(0);" class="icon" onclick="showSideNav()">
                     <i class="fa fa-bars"></i>
                    </a>
                </div>
                <div class="mySideNav" id="mySideNav">
                    <a href="${pageContext.request.contextPath}">Pokaż wszystkie</a> 
                    <c:if test="${not empty requestScope.categories}">
                        <c:forEach var="category" items="${requestScope.categories}">
                            <a href="${pageContext.request.contextPath}?category_name=${category.name}">${category.name}</a> 
                        </c:forEach>
                    </c:if>
                </div>
            </div>
            <div class="myMain">
                <c:if test="${not empty requestScope.recipes}">
                    <c:forEach var="recipe" items="${requestScope.recipes}">    
                        <div class="myRow">
                          <div class ="myVote">
                              <a href="${pageContext.request.contextPath}/vote?recipe_id=${recipe.id}&vote=VOTE_UP" class="btn btn-block btn-primary btn-success" style="background-color: #1abc9c;"><span class="glyphicon glyphicon-thumbs-up"></span></a>
                            <div class="well-sm well" style="text-align: center; margin: 0;">
                                <c:out value="${recipe.upVote - recipe.downVote}"/>
                            </div>
                              <a href="${pageContext.request.contextPath}/vote?recipe_id=${recipe.id}&vote=VOTE_DOWN" class="btn btn-block btn-primary btn-danger"><span class="glyphicon glyphicon-thumbs-down"></span></a>
                         </div>
                          <div class="myRecipe">  
                              <h2 style="margin-top:6px;"><c:out value="${recipe.name}"/></h2>
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
                                <a href="<c:out value="${recipe.ulr}"/>" class="btn btn-primary btn-info" style="margin: 5px;padding-left: 20px; padding-right: 20px;">Przejdź do strony</a>
                                <a href="${pageContext.request.contextPath}/like?recipe_id=${recipe.id}&recipe_user_id=${recipe.user.id}" class="btn btn-primary btn-warning" style="margin: 5px;">Dodaj do ulubionych</a>
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
