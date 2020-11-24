<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="wrapper d-flex align-items-stretch">
    <nav id="side-menu" class="active">
        <div class="custom-menu">
            <button type="button" id="side-menu-collapse-button" class="btn btn-primary">
                <i class="fa fa-bars"></i>
                <span class="sr-only">Toggle Menu</span>
            </button>
        </div>
        <div class="p-4 side-menu-list-block">
            <ul class="list-unstyled components mb-5 side-menu-list">
                <li>
                    <a class="nav-link" href="${pageContext.request.contextPath}/"><span
                            class="fa fa-sticky-note mr-3"></span>
                        Главная страница
                    </a>
                </li>
                <li>
                    <a class="nav-link dropdown-toggle" data-toggle="collapse" href="#side-menu-user-setting"
                       aria-expanded="false" aria-controls="side-menu-user-setting"><span
                            class="fa fa-user mr-3"></span>
                        Настройка пользователей
                    </a>
                </li>
                <div class="collapse" id="side-menu-user-setting">
                    <div class="side-sub-menu card-body">
                        <ul class="side-sub-menu-list list-group">
                            <li>
                                <a href="<c:url value="/users"/>" class="nav-link">Пользователи</a>
                            </li>
                            <li>
                                <a href="<c:url value="/roles"/>" class="nav-link">Роли</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <li>
                    <a class="nav-link" href="<c:url value="/system-settings"/>"><span
                            class="fa fa-sticky-note mr-3"></span>
                        Настройка системы
                    </a>
                </li>
                <li>
                    <a class="nav-link" href="<c:url value="/product_hierarchy"/>"><span class="fa fa-sticky-note mr-3"></span>
                        Иерархия продукта
                    </a>
                </li>
                <li>
                    <a class="nav-link" href="<c:url value="/hierarchy_update"/>"><span class="fa fa-sticky-note mr-3"></span>
                        Обновление линейки
                    </a>
                </li>
                <li>
                    <a class="nav-link" href="<c:url value="/product_placing"/>"><span class="fa fa-sticky-note mr-3"></span>
                        Продукты
                    </a>
                </li>
                <li>
                    <a class="nav-link" href="<c:url value="/nsi"/>"><span class="fa fa-sticky-note mr-3"></span>
                        НСИ
                    </a>
                </li>

                <%--                <li>--%>
                <%--                    <a class="nav-link dropdown-toggle" data-toggle="collapse" href="#side-menu-nsi"--%>
                <%--                       aria-expanded="false" aria-controls="side-menu-nsi"><span--%>
                <%--                            class="fa fa-user mr-3"></span>--%>
                <%--                        НСИ--%>
                <%--                    </a>--%>
                <%--                </li>--%>
                <%--                <div class="collapse" id="side-menu-nsi">--%>
                <%--                    <div class="side-sub-menu card-body">--%>
                <%--                        <ul class="side-sub-menu-list list-group">--%>
                <%--                            <li>--%>
                <%--                                <a href="#" class="nav-link">Перспективы</a>--%>
                <%--                            </li>--%>
                <%--                            <li>--%>
                <%--                                <a href="#" class="nav-link">Последствия</a>--%>
                <%--                            </li>--%>
                <%--                            <li>--%>
                <%--                                <a href="#" class="nav-link">Риски</a>--%>
                <%--                            </li>--%>
                <%--                            <li>--%>
                <%--                                <a href="#" class="nav-link">Государства и валюты</a>--%>
                <%--                            </li>--%>
                <%--                            <li>--%>
                <%--                                <a href="#" class="nav-link">Типы карт</a>--%>
                <%--                            </li>--%>
                <%--                        </ul>--%>
                <%--                    </div>--%>
                <%--                </div>--%>
                <li>
                    <a class="nav-link dropdown-toggle" data-toggle="collapse" href="#side-menu-audit"
                       aria-expanded="false" aria-controls="side-menu-audit"><span
                            class="fa fa-user mr-3"></span>
                        Аудит
                    </a>
                </li>
                <div class="collapse" id="side-menu-audit">
                    <div class="side-sub-menu card-body">
                        <ul class="side-sub-menu-list list-group">
                            <li>
                                <a href="<c:url value="/audit/refresh_nsi"/>" class="nav-link">Синхронизация НСИ</a>
                            </li>
                            <li>
                                <a href="<c:url value="/audit/log_nsi"/>" class="nav-link">Журнал синхронизации НСИ</a>
                            </li>
                            <li>
                                <a href="<c:url value="/audit/log_acc"/>" class="nav-link">Журнал синхронизации данных
                                    счета/договора/клиента</a>
                            </li>
                            <li>
                                <a href="<c:url value="/audit/log_user_act"/>" class="nav-link">Действия
                                    пользователей</a>
                            </li>
                            <li>
                                <a href="<c:url value="/audit/log_sys_event"/>" class="nav-link">Системные событи</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <li>
                    <a class="nav-link dropdown-toggle" data-toggle="collapse" href="#side-menu-role-changer"
                       aria-expanded="false" aria-controls="side-menu-role-changer"><span
                            class="fa fa-user mr-3"></span>
                        Переключение роли
                    </a>
                </li>
                <div class="collapse" id="side-menu-role-changer">

                    <!-- Убрать -->

                    <div class="side-sub-menu card-body" style="color: rgba(255, 255, 255, 0.6);">
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="exampleRadios"
                                   id="exampleRadios1" value="option1" checked>
                            <label class="form-check-label" for="exampleRadios1">
                                Админ
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="exampleRadios"
                                   id="exampleRadios2" value="option2">
                            <label class="form-check-label" for="exampleRadios2">
                                Роль 1
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="exampleRadios"
                                   id="exampleRadios3" value="option3">
                            <label class="form-check-label" for="exampleRadios3">
                                Роль 2
                            </label>
                        </div>
                    </div>
                </div>

            </ul>

        </div>
    </nav>

    <div id="content" class="p-4 p-md-5 pt-5">

        <div id="page-frame"></div>

        <div class="compit-label">
            <p class="font-italic">ЗАО "Compit", 2020.</p>
        </div>
    </div>
</div>
