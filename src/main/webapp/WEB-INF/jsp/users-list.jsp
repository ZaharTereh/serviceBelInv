<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Пользователи</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="component/main-navbar.jsp"/>
<jsp:include page="component/side-navbar.jsp"/>

<div id="page-content">
    <div class="row justify-content-center page-title">
        <p class="display-4">Пользователи</p>
    </div>
        <table id="users-table" class="table table-bordered">
            <thead>
            <tr>
                <th scope="col">Имя</th>
                <th scope="col">Доменное имя</th>
                <th scope="col">Описание</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${not empty usersList}">
                <c:forEach items="${usersList}" var="user">
                    <tr>
                        <td class="table-user-name">${user.name}</td>
                        <td class="table-user-domain">${user.name}@compit.lan</td>
                        <td class="table-user-description">${user.description}</td>
                    </tr>
                </c:forEach>
            </c:if>

            </tbody>
        </table>

    <div class="users-page-buttons float-right">
        <button type="button" class="btn view-user-modal-button" disabled>Просмотреть</button>
        <button type="button" class="btn create-user-modal-button" data-toggle="modal" data-target=".create-user-modal">
            Добавить пользователя
        </button>
        <button type="button" class="btn user-role-modal-button" disabled>
            Назначить роль
        </button>
    </div>

    <%--_______________CREATING USER_______________--%>
    <div class="modal fade create-user-modal" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content create-user-modal-content">
                <div class="modal-header create-user-modal-header">
                    <h5 class="modal-title">Создать пользователя</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span class="create-user-modal-close-cross">&times;</span>
                    </button>
                </div>
                <div class="modal-body justify-content-center">
                    <div class="row">
                        <div class="col-12">
                            <form action="<c:url value="/create-user"/>" method="post">
                                <div class="form-group">
                                    <label for="input-username-create">Имя пользователя:</label>
                                    <input name="userName" class="form-control" id="input-username-create"
                                           placeholder="Имя пользователя">
                                </div>
                                <div class="form-group">
                                    <label for="input-user-password-create">Пароль:</label>
                                    <input type="password" class="form-control" id="input-user-password-create"
                                           placeholder="Пароль">
                                </div>
                                <div class="form-group">
                                    <label for="input-user-description-create">Описание:</label>
                                    <textarea name="userDescription" class="form-control"
                                              id="input-user-description-create"
                                              rows="3"></textarea>
                                </div>
                                <button class="btn create-user-submit-button">Создать</button>
                                <button type="button" class="btn ldap-users-modal-button" data-toggle="modal"
                                        data-target=".ldap-users-modal" data-dismiss="modal">Выгрузить из LDAP
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade ldap-users-modal" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content ldap-users-modal-content">
                <div class="modal-header ldap-users-modal-header">
                    <h5 class="modal-title">Выгрузить пользователей из LDAP</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span class="ldap-users-modal-close-cross">&times;</span>
                    </button>
                </div>
                <div class="modal-body ldap-users-modal-body">
                    <div class="row justify-content-center">
                        <div class="col-5 ldap-users-modal-left-panel">
                            <ul class="list-group ldap-users-modal-left-list">
                            </ul>
                        </div>
                        <div class="col-1">
                            <button type="button" class="btn ldap-users-modal-move-all-to-right-button">&gt;&gt;
                            </button>
                            <button type="button" class="btn ldap-users-modal-move-to-right-button">&gt;</button>
                            <button type="button" class="btn ldap-users-modal-move-to-left-button">&lt;</button>
                            <button type="button" class="btn ldap-users-modal-move-all-to-left-button">&lt;&lt;</button>
                        </div>
                        <div class="col-5 ldap-users-modal-right-panel">
                            <ul class="list-group ldap-users-modal-right-list">
                            </ul>
                        </div>
                    </div>
                    <div class="row ldap-users-modal-bottom">
                        <div class="offset-4 col-4 ldap-users-load">
                            <button class="btn ldap-users-submit-button">
                                <span class="ldap-users-submit-button-text">Выгрузить</span>
                                <span class="ldap-users-spinner">
                                    <span class="spinner-border spinner-border-sm" role="status"></span>
                                    Выгрузка...
                                </span>
                            </button>
                        </div>
                        <div class="col-4">
                            <label>
                                <input type="checkbox" class="form-check-input" name="updateIfExist" value="">
                                <span>обновить</span>
                            </label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade load-ldap-user-modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content load-ldap-user-content">
                <div class="modal-header load-ldap-user-header">
                    <h5 class="modal-title load-ldap-user-title">Обновить данные</h5>
                    <button type="button" class="close load-ldap-user-close-cross" aria-label="Close">
                        <span>&times;</span>
                    </button>
                </div>
                <div class="modal-body load-ldap-user-body">
                    Пользователь <span class="load-ldap-user-modal-username font-weight-bold"></span> уже существует. Обновить данные?
                    <span class="load-ldap-user-modal-description" hidden></span>
                </div>
                <div class="modal-footer load-ldap-user-bottom">
                    <button type="button" class="btn load-ldap-user-modal-yes-button">Да</button>
                    <button type="button" class="btn load-ldap-user-modal-no-button">Нет</button>
                    <button type="button" class="btn load-ldap-user-modal-cancel-button">Отмена</button>
                </div>
            </div>
        </div>
    </div>

    <%--//////////CREATING USERS////////////--%>

    <div class="modal fade user-role-modal" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-lg modal-dialog-centered">
            <div class="modal-content user-role-modal-content">
                <div class="modal-header user-role-modal-header">
                    <h5 class="modal-title">Назначить роль</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span class="user-role-modal-close-cross">&times;</span>
                    </button>
                </div>
                <div class="modal-body user-role-modal-body">
                    <div class="row justify-content-center">
                        <div class="col-5 user-role-modal-left-panel">
                            <ul class="list-group user-role-modal-left-list">
                            </ul>
                        </div>
                        <div class="col-1">
                            <button type="button" class="btn user-role-modal-move-all-to-right-button">&gt;&gt;</button>
                            <button type="button" class="btn user-role-modal-move-to-right-button">&gt;</button>
                            <button type="button" class="btn user-role-modal-move-to-left-button">&lt;</button>
                            <button type="button" class="btn user-role-modal-move-all-to-left-button">&lt;&lt;</button>
                        </div>
                        <div class="col-5 user-role-modal-right-panel">
                            <ul class="list-group user-role-modal-right-list">
                            </ul>
                        </div>
                    </div>
                    <div class="row user-role-modal-bottom  justify-content-center">
                        <div class="user-role-submit">
                            <button class="btn user-role-submit-button">
                                Назначить
                                <input class="user-role-modal-username" type="hidden" value="">
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/popper/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/side-navbar.js"></script>
<script src="${pageContext.request.contextPath}/js/content-inject.js"></script>
<script src="${pageContext.request.contextPath}/js/users.js"></script>
</body>
</html>
