<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="pl">
    <head>
        <link rel="shortcut icon" href="/public/favicon.ico"/>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>WMS | Strona Logowania</title>

        <link href="${contextPath}/public/css/bootstrap.min.css" rel="stylesheet">
        <link href="${contextPath}/public/css/common.css" rel="stylesheet">
    </head>

    <body>

        <div class="container">

            <form method="POST" action="${contextPath}/login" class="form-signin">
                <h2 class="form-heading">Zaloguj się</h2>

                <div class="form-group ${error != null ? 'has-error' : ''}">
                    <span>${message}</span>
                    <input name="login" type="text" class="form-control" placeholder="login"
                           autofocus="true"/>
                    <input name="password" type="password" class="form-control" placeholder="hasło"/>
                    <span>${error}</span>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                    <button class="btn btn-lg btn-primary btn-block" type="submit">Zaloguj się</button>
                    <h4 class="text-center"><a href="${contextPath}/register">Zarejestruj się</a></h4>
                </div>

            </form>

        </div>

        <script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
        <script src="https://unpkg.com/popper.js@1.12.6/dist/umd/popper.js" integrity="sha384-fA23ZRQ3G/J53mElWqVJEGJzU0sTs+SvzG8fXVWP+kJQ1lwFAOkcUOysnlKJC33U" crossorigin="anonymous"></script>
        <script src="https://unpkg.com/bootstrap-material-design@4.1.1/dist/js/bootstrap-material-design.js" integrity="sha384-CauSuKpEqAFajSpkdjv3z9t8E7RlpJ1UP0lKM/+NdtSarroVKu069AlsRPKkFBz9" crossorigin="anonymous"></script>
    </body>
</html>