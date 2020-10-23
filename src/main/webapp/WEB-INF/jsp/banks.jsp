<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Банки</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="../component/main-navbar.jsp"/>
<jsp:include page="../component/side-navbar.jsp"/>

<div id="page-content">
    <div class="row justify-content-center page-title">
        <p class="display-4">Банки</p>
    </div>

    <div class="table-responsive">
        <table id="nsi-table" class="table table-bordered">
            <thead>
            <tr>
                <th scope="col">БИК</th>
                <th scope="col">Наименование Банка</th>
                <th scope="col">Адрес банка</th>
                <th scope="col">Телефон банка</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${not empty bankDirectory.banks}">
                <c:forEach items="${bankDirectory.banks}" var="bank">
                    <tr>
                        <td class="info" hidden>
                            <span data-nsi class="id">${bank.id}</span>
                            <span data-nsi class="code">${bank.code}</span>
                            <span data-nsi class="name">${bank.name}</span>
                            <span data-nsi class="banksAddress">${bank.banksAddress}</span>
                            <span data-nsi class="banksPryam">${bank.banksPryam}</span>
                            <span data-nsi class="banksRegion">${bank.banksRegion}</span>
                            <span data-nsi class="banksOldCode">${bank.banksOldCode}</span>
                            <span data-nsi class="banksBiciso">${bank.banksBiciso}</span>
                            <span data-nsi class="banksRang">${bank.banksRang}</span>
                            <span data-nsi class="banksKodGb">${bank.banksKodGb}</span>
                            <span data-nsi class="banksKodAutu">${bank.banksKodAutu}</span>
                            <span data-nsi class="banksKodType">${bank.banksKodType}</span>
                            <span data-nsi class="banksKodSoato">${bank.banksKodSoato}</span>
                            <span data-nsi class="banksKodStatu">${bank.banksKodStatu}</span>
                            <span data-nsi class="banksPhone">${bank.banksPhone}</span>
                        </td>
                        <td class="code">${bank.code}</td>
                        <td class="name">${bank.name}</td>
                        <td class="banksAddress">${bank.banksAddress}</td>
                        <td class="banksPhone">${bank.banksPhone}</td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
    </div>
    <div class="nsi-directory-page-buttons float-right">
        <button type="button" class="btn create-nsi-modal-button" data-toggle="modal"
                data-target=".create-nsi-entry-modal">
            Добавить
        </button>
        <button type="button" class="btn view-nsi-modal-button" disabled>
            Просмотреть
        </button>
        <button type="button" class="btn nsi-edit-entry-modal-button" disabled>
            Редактировать
        </button>
        <button type="button" class="btn nsi-remove-entry-button" disabled>
            Удалить
        </button>
    </div>

    <div class="modal fade view-nsi-entry-modal" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content view-nsi-entry-modal-content">
                <div class="modal-header view-nsi-entry-modal-header">
                    <h5 class="modal-title">Просмотр записи</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span class="view-nsi-entry-modal-close-cross">&times;</span>
                    </button>
                </div>
                <div class="modal-body justify-content-center">
                    <div class="row">
                        <div class="col-12">
                            <form>
                                <input data-nsi id="input-nsi-bank-id-view" name="id" hidden disabled>
                                <div class="form-group">
                                    <label for="input-nsi-bank-code-view">Код (БИК):</label>
                                    <input data-nsi name="code" class="form-control" id="input-nsi-bank-code-view"
                                           disabled>
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-name-view">Наименование банка:</label>
                                    <input data-nsi name="name" class="form-control" id="input-nsi-bank-name-view"
                                           disabled>
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-pryam-view">Отделение прямого подчинеия:</label>
                                    <input data-nsi name="banksPryam" class="form-control"
                                           id="input-nsi-bank-pryam-view" disabled>
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-region-view">Номер региона:</label>
                                    <input data-nsi name="banksRegion" class="form-control"
                                           id="input-nsi-bank-region-view" disabled>
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-old-code-view">Условный номер участника расчетов:</label>
                                    <input data-nsi name="banksOldCode" class="form-control"
                                           id="input-nsi-bank-old-code-view" disabled>
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-biciso-view">Признак формата BICISO:</label>
                                    <input data-nsi type="number" min="0" max="1" name="banksBiciso"
                                           class="form-control" id="input-nsi-bank-biciso-view" disabled>
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-rang-view">Ранг:</label>
                                    <input data-nsi name="banksRang" class="form-control" id="input-nsi-bank-rang-view"
                                           disabled>
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-kod-gb-view">Код головного банка:</label>
                                    <input data-nsi name="banksKodGb" class="form-control"
                                           id="input-nsi-bank-kod-gb-view"
                                           placeholder="Код головного банка" disabled>
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-kod-autu-view">Код аутентификации участника:</label>
                                    <input data-nsi name="banksKodAutu" class="form-control"
                                           id="input-nsi-bank-kod-autu-view" disabled>
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-address-view">Адрес банка:</label>
                                    <input data-nsi name="banksAddress" class="form-control"
                                           id="input-nsi-bank-address-view" disabled>
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-kod-type-view">Код типа участника:</label>
                                    <input data-nsi name="banksKodType" class="form-control"
                                           id="input-nsi-bank-kod-type-view" disabled>
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-kod-soato-view">Код СОАТО региона:</label>
                                    <input data-nsi name="banksKodSoato" class="form-control"
                                           id="input-nsi-bank-kod-soato-view" disabled>
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-kod-statu-view">Код статуса участника:</label>
                                    <input data-nsi name="banksKodStatu" class="form-control"
                                           id="input-nsi-bank-kod-statu-view" disabled>
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-phone-view">Телефон банка:</label>
                                    <input data-nsi name="banksPhone" class="form-control"
                                           id="input-nsi-bank-phone-view" disabled>
                                </div>
                                <div class="row nsi-entry-buttons justify-content-center">
                                    <button type="button" class="btn nsi-edit-entry-button">Редактировать</button>
                                </div>
                                <div class="row nsi-entry-edit-buttons float-right" hidden="">
                                    <button type="button" class="btn nsi-edit-save-button">Сохранить</button>
                                    <button type="button" class="btn nsi-edit-cancel-button">Отмена</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%----%>
    <div class="modal fade create-nsi-entry-modal" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content create-nsi-entry-modal-content">
                <div class="modal-header create-nsi-entry-modal-header">
                    <h5 class="modal-title">Создать запись в справочнике</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span class="create-nsi-entry-modal-close-cross">&times;</span>
                    </button>
                </div>
                <div class="modal-body justify-content-center">
                    <div class="row">
                        <div class="col-12">
                            <form action="<c:url value="/nsi/banks/create"/>" method="post">
                                <div class="form-group">
                                    <label for="input-nsi-bank-code-create">Код (БИК) (*):</label>
                                    <input name="code" class="form-control" id="input-nsi-bank-code-create"
                                           placeholder="Код (БИК)" required>
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-name-create">Наименование банка (*):</label>
                                    <input name="name" class="form-control" id="input-nsi-bank-name-create"
                                           placeholder="Наименоваие банка" required>
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-pryam-create">Отделение прямого подчинеия:</label>
                                    <input name="banksPryam" class="form-control" id="input-nsi-bank-pryam-create"
                                           placeholder="Отделение прямого подчинения">
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-region-create">Номер региона:</label>
                                    <input name="banksRegion" class="form-control" id="input-nsi-bank-region-create"
                                           placeholder="Номер региона">
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-old-code-create">Условный номер участника
                                        расчетов:</label>
                                    <input name="banksOldCode" class="form-control" id="input-nsi-bank-old-code-create"
                                           placeholder="Условный номер участника расчетов">
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-biciso-create">Признак формата BICISO:</label>
                                    <input type="number" min="0" max="1" name="banksBiciso" class="form-control"
                                           id="input-nsi-bank-biciso-create"
                                           placeholder="Признак формата BICISO">
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-rang-create">Ранг:</label>
                                    <input name="banksRang" class="form-control" id="input-nsi-bank-rang-create"
                                           placeholder="Ранг">
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-kod-gb-create">Код головного банка:</label>
                                    <input name="banksKodGb" class="form-control" id="input-nsi-bank-kod-gb-create"
                                           placeholder="Код головного банка">
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-kod-autu-create">Код аутентификации участника:</label>
                                    <input name="banksKodAutu" class="form-control" id="input-nsi-bank-kod-autu-create"
                                           placeholder="Код аутентификации участника">
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-address-create">Адрес банка:</label>
                                    <input name="banksAddress" class="form-control" id="input-nsi-bank-address-create"
                                           placeholder="Адрес банка">
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-kod-type-create">Код типа участника:</label>
                                    <input name="banksKodType" class="form-control" id="input-nsi-bank-kod-type-create"
                                           placeholder="Код типа участника">
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-kod-soato-create">Код СОАТО региона:</label>
                                    <input name="banksKodSoato" class="form-control"
                                           id="input-nsi-bank-kod-soato-create"
                                           placeholder="Код СОАТО региона">
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-kod-statu-create">Код статуса участника:</label>
                                    <input name="banksKodStatu" class="form-control"
                                           id="input-nsi-bank-kod-statu-create"
                                           placeholder="Код статуса участника">
                                </div>
                                <div class="form-group">
                                    <label for="input-nsi-bank-phone-create">Телефон банка:</label>
                                    <input name="banksPhone" class="form-control" id="input-nsi-bank-phone-create"
                                           placeholder="Телефон банка">
                                </div>
                                <div class="row float-right">
                                    <button class="btn create-nsi-entry-submit-button">Создать</button>
                                    <button type="button" class="btn create-cancel-nsi-entry-button">Отмена</button>
                                </div>
                            </form>
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
<script src="${pageContext.request.contextPath}/js/nsi.js"></script>
</body>
</html>
