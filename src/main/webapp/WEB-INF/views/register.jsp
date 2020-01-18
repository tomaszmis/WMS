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

        <title>WMS | Rejestracja</title>
        <link href="${contextPath}/public/css/bootstrap.min.css" rel="stylesheet">
        <link href="${contextPath}/public/css/common.css" rel="stylesheet">


    </head>

    <body>

        <div class="container">

            <form:form id="userForm" method="POST" modelAttribute="registrationForm">
                <h2 class="form-heading">Zarejestruj się</h2>
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="text" path="name" class="form-control" placeholder="Imię" autofocus="true"></form:input>
                        <form:errors class="bmd-help" path="name"></form:errors>
                    </div>

                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="text" path="surname" class="form-control" placeholder="Nazwisko"></form:input>
                        <form:errors class="bmd-help" path="surname"></form:errors>
                    </div>

                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="text" path="email" class="form-control" placeholder="Email"></form:input>
                        <form:errors class="bmd-help" path="email"></form:errors>
                    </div>

                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="text" path="login" class="form-control" placeholder="Login"></form:input>
                        <form:errors class="bmd-help" path="login"></form:errors>
                    </div>

                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="password" path="password" class="form-control" placeholder="Hasło"></form:input>
                        <form:errors class="bmd-help" path="password"></form:errors>
                    </div>

                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="password" path="passwordConfirm" class="form-control"
                                    placeholder="Potwierdź hasło"></form:input>
                        <form:errors class="bmd-help" path="passwordConfirm"></form:errors>
                    </div>

                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="text" path="whName" class="form-control" placeholder="Nazwa Magazynu"></form:input>
                        <form:errors class="bmd-help" path="whName"></form:errors>
                    </div>

                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="text" path="whDesc" class="form-control" placeholder="Opis Magazynu"></form:input>
                        <form:errors class="bmd-help" path="whDesc"></form:errors>
                    </div>

                <button id="submit" class="btn btn-lg btn-primary btn-block" type="submit">Zarejestruj</button>
            </form:form>

        </div>

        <script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
        <script src="https://unpkg.com/popper.js@1.12.6/dist/umd/popper.js" integrity="sha384-fA23ZRQ3G/J53mElWqVJEGJzU0sTs+SvzG8fXVWP+kJQ1lwFAOkcUOysnlKJC33U" crossorigin="anonymous"></script>
        <script src="https://unpkg.com/bootstrap-material-design@4.1.1/dist/js/bootstrap-material-design.js" integrity="sha384-CauSuKpEqAFajSpkdjv3z9t8E7RlpJ1UP0lKM/+NdtSarroVKu069AlsRPKkFBz9" crossorigin="anonymous"></script>
    </body>
</html>
