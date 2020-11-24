<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<header>
    <div class="header-top-line"></div>
    <nav class="header-menu navbar navbar-expand-lg navbar-light bg-light">
        <a class="header-menu-logo navbar-brand mb-0 h1" href="${pageContext.request.contextPath}/">
            <img src="${pageContext.request.contextPath}/img/logo.jpg" alt="">
        </a>
        <button class="header-menu-toggler navbar-toggler" type=
                "button" data-toggle="collapse"
                data-target="#header-menu-list" aria-controls="navbarNav" aria-expanded="false"
                aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="container">
            <div class="collapse navbar-collapse" id="header-menu-list">
                <ul class="navbar-nav pull-sm-left">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/">Главная страница</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#"
                           data-toggle="dropdown" aria-expanded="false">
                            Настройка пользователей
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                            <a class="dropdown-item" href="<c:url value="/users"/>">Пользователи</a>
                            <a class="dropdown-item" href="<c:url value="/roles"/>">Роли</a>
                        </div>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/system-settings"/>">Настройка системы</a>
                    </li>
                    <li>
                        <a class="nav-link" href="<c:url value="/product_hierarchy"/>">
                            Иерархия продукта
                        </a>
                    </li>
                    <li>
                        <a class="nav-link" href="<c:url value="/hierarchy_update"/>">
                            Обновление линейки
                        </a>
                    </li>
                    <li>
                        <a class="nav-link" href="<c:url value="/product_placing"/>">
                            Продукты
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/nsi"/>">НСИ</a>
                    </li>
<%--                    <li class="nav-item dropdown">--%>
<%--                        <a class="nav-link dropdown-toggle" href="#"--%>
<%--                           data-toggle="dropdown" aria-expanded="false">--%>
<%--                            НСИ--%>
<%--                        </a>--%>
<%--                        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">--%>
<%--                            <a class="dropdown-item" href="#">Перспективы</a>--%>
<%--                            <a class="dropdown-item" href="#">Последствия</a>--%>
<%--                            <a class="dropdown-item" href="#">Риски</a>--%>
<%--                            <a class="dropdown-item" href="#">Государства и валюты</a>--%>
<%--                            <a class="dropdown-item" href="#">Типы карт</a>--%>
<%--                            <a class="dropdown-item" href="#">...</a>--%>

<%--                        </div>--%>
<%--                    </li>--%>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink"
                           data-toggle="dropdown" aria-expanded="false">
                            Аудит
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                            <a class="dropdown-item" href="<c:url value="/audit/refresh_nsi"/>">Синхронизация НСИ</a>
                            <a class="dropdown-item" href="<c:url value="/audit/log_nsi"/>">Журнал синхронизации НСИ</a>
                            <a class="dropdown-item" href="<c:url value="/audit/log_acc"/>">Журнал синхронизации данных счета/договора/клиента</a>
                            <a class="dropdown-item" href="<c:url value="/audit/log_user_act"/>">Действия пользователей</a>
                            <a class="dropdown-item" href="<c:url value="/audit/log_sys_event"/>">Системные события</a>
                        </div>
                    </li>
                </ul>
            </div>

        </div>
        <!-- Убрать -->
        <a href="#" class="nav-link mr-5" style="color: #009f88">Админ</a>
    </nav>
    <div class="header-bottom-line"></div>
    <c:if test="${not empty datasourceError}">
        <div class="datasource-error-notification">Ошибка подключения к БД</div>
    </c:if>
</header>
