<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Иерархия продукта</title>
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
        <p class="display-4">Иерархия продукта</p>
    </div>

    <div class="add-panel-header">
        <form method="get" action="${pageContext.request.contextPath}/product_hierarchy/by_group" id="find-form" class="find-form">
            <div class="product-group-list">
                <c:forEach items="${productGroups}" var="productGroup">
                    <div class="productGroupData" hidden>
                        <span class="id">${productGroup.id}</span>
                        <span class="hiId">${productGroup.hiId}</span>
                        <span class="name">${productGroup.name}</span>
                        <span class="lev">${productGroup.lev}</span>
                        <span class="leaf">${productGroup.leaf}</span>
                        <span class="tableName">${productGroup.tableName}</span>
                    </div>
                </c:forEach>
                <select name="firstLevelProductGroup"  class="selectForFindHierarchies" id="firstLevelProductGroup" required>
                    <option value="0" selected disabled>Бизнес-направление</option>
                    <c:forEach items="${productGroups}" var="productGroup">
                        <c:if test="${productGroup.lev eq 1}">
                            <option value="${productGroup.id}">${productGroup.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
                <select name="secondLevelProductGroup"  class="selectForFindHierarchies" id="secondLevelProductGroup" required>
                    <option value="0" selected disabled>Класс продукта</option>
                </select>
                <select name="thirdLevelProductGroup"  class="selectForFindHierarchies" id="thirdLevelProductGroup" required>
                    <option value="0" selected disabled>Группа продукта</option>
                </select>
            </div>
            <div class="nsi-directory-page-buttons">
                <button form="find-form" class="btn find-button">Поиск</button>
            </div>
        </form>
        <div class="nsi-directory-page-buttons main-buttons">
                <button type="button" class="btn create-nsi-modal-button" >
                    Добавить
                </button>
                <button type="button" class="btn edit-nsi-modal-button" disabled>
                    Редактировать
                </button>
        </div>
    </div>

    <div class="table-responsive">
        <table id="product-hierarchy-table" class="table table-bordered">
            <thead>
            <tr>
                <th scope="col">Бизнес-направление</th>
                <th scope="col">Класс продукта</th>
                <th scope="col">Группа продукта</th>
                <th scope="col">Название иерархии</th>
                <th scope="col">Дата начала действия</th>
                <th scope="col">Дата окончания действия</th>
                <th scope="col">Автор</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${not empty productHierarchyList}">
                <c:forEach items="${productHierarchyList}" var="fullProductHierarchy">
                    <tr>
                        <td class="info" hidden>
                            <span data-product-hierarchy class="id">${fullProductHierarchy.productHierarchyDTO.id}</span>
                            <span data-product-hierarchy class="thirdProductGroup">${fullProductHierarchy.productHierarchyDTO.productGroupDTO}</span>
                            <span data-product-hierarchy class="secondProductGroup">${fullProductHierarchy.secondProductGroup}</span>
                            <span data-product-hierarchy class="firstProductGroup">${fullProductHierarchy.firstProductGroup}</span>
                            <span data-product-hierarchy class="name">${fullProductHierarchy.productHierarchyDTO.name}</span>
                            <span data-product-hierarchy class="activity">${fullProductHierarchy.productHierarchyDTO.activity}</span>
                            <span data-product-hierarchy class="levelQuant">${fullProductHierarchy.productHierarchyDTO.levelQuant}</span>
                            <span data-product-hierarchy class="dateBegin">${fullProductHierarchy.productHierarchyDTO.dateBegin}</span>
                            <span data-product-hierarchy class="dateEnd">${fullProductHierarchy.productHierarchyDTO.dateEnd}</span>
                            <span data-product-hierarchy class="createdBy">${fullProductHierarchy.author}</span>
                            <span data-product-hierarchy class="productHierarchyStructs">${fullProductHierarchy.productHierarchyDTO.productHierarchyStructs}</span>
                        </td>
                        <td class="productGroup">${fullProductHierarchy.firstProductGroup.name}</td>
                        <td class="productGroup">${fullProductHierarchy.secondProductGroup.name}</td>
                        <td class="productGroup">${fullProductHierarchy.productHierarchyDTO.productGroupDTO.name}</td>
                        <td class="name">${fullProductHierarchy.productHierarchyDTO.name}</td>
                        <td class="dateBegin">${fullProductHierarchy.productHierarchyDTO.dateBegin}</td>
                        <td class="dateEnd">${fullProductHierarchy.productHierarchyDTO.dateEnd}</td>
                        <td class="createdBy">${fullProductHierarchy.author}</td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
    </div>

    <div class="justify-content-center edit-panel" id="add-and-edit-form">
        <div class="justify-content-left header-edit-panel">
            <h4 class="hierarchy-line-label">Иерархия линейки</h4>
            <button type="button" class="btn create-nsi-entry-submit-button" disabled>Сохранить</button>
            <button type="button" class="btn create-cancel-nsi-entry-button" disabled>Отмена</button>
        </div>
        <div class="error-input">
            <h5 class="error-input-text"></h5>
        </div>
            <input type="hidden" id="hierarchy-id" value="" name="id">
            <div class="form-group product-group-label">
                <label for="input-product-group-level-1-name-create">Бизнес-направление:</label>
                <select disabled name="firstProductGroup" class="form-control input-product-group-name-create" id="input-product-group-level-1-name-create" required>
                    <option  value="0" disabled selected>Бизнес-направление</option>
                    <c:forEach items="${productGroups}" var="productGroup">
                        <c:if test="${productGroup.lev eq 1}">
                            <option value="${productGroup.id}">${productGroup.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group product-group-label">
                <label for="input-product-group-level-2-name-create">Класс продукта:</label>
                <select disabled name="secondProductGroup" class="form-control input-product-group-name-create" id="input-product-group-level-2-name-create" required>
                    <option  value="0" disabled selected>Класс продукта</option>
                </select>
            </div>
            <div class="form-group product-group-label">
                <label for="input-product-group-level-3-name-create">Группа продукта:</label>
                <select disabled name="thirdProductGroup" class="form-control input-product-group-name-create" id="input-product-group-level-3-name-create" required>
                    <option  value="0" disabled selected>Группа продукта</option>
                </select>
            </div>
            <div class="form-group name-and-current-of-level">
                <label for="input-product-hierarchy-name-create">Название иерархии:</label>
                <input disabled name="name" class="form-control input-product-hierarchy-name-create" id="input-product-hierarchy-name-create"
                       placeholder="Название иерархии" required>
                <label for="input-product-hierarchy-date-end-create">Количество уровней:</label>
                <input disabled type="number" name="levelQuant" min="1" max="9" class="form-control input-quantity-of-level" id="input-product-hierarchy-levelQuant-create"
                       placeholder="Количество уровней:" required>
            </div>

            <div class="form-group date-and-activity">
                <label for="input-product-hierarchy-date-begin-create">Дата начала действия:</label>
                <input disabled type="date" name="dateBegin" class="form-control" id="input-product-hierarchy-date-begin-create"
                       placeholder="Дата начала действия" required>
                <label for="input-product-hierarchy-date-end-create">Дата конца действия:</label>
                <input disabled type="date" name="dateEnd" class="form-control" id="input-product-hierarchy-date-end-create"
                       placeholder="Дата конца действия" required>
                <label class="checkbox-label" for="input-product-hierarchy-activity-create">Активность:</label>
                <input disabled type="checkbox" name="activity" class="checkbox-activity" id="input-product-hierarchy-activity-create">
            </div>
            <hr>

        <div class="levels-of-hierarchy justify-content-center" id="levels-of-hierarchy justify-content-center">
            <div class="level-of-hierarchy">
                <div class="level-data">
                    <div class="labels-for-level">
                        <label for="select-criterion-for-level">Критерий для уровня </label>
                        <label for="select-field-for-level">Поля уровня </label>
                    </div>
                    <div class="selects-for-level">
                        <select disabled name="criterionForLevel" class="form-control criterionForLevel" id="select-criterion-for-level" required>
                            <option value="0" disabled selected>Критерий для уровня</option>
                        </select>
                        <select disabled name="fieldForLevel" class="form-control fieldForLevel" id="select-field-for-level" required>
                            <option value="0" disabled selected>Поле уровня</option>
                        </select>
                        <select disabled name="listOfFieldsForLevel" class="form-control result-list-of-field-for-level form-control"
                                id="result-list-of-field-for-level"  size="4" required>
                        </select>
                        <select class="hidden-list-of-field-for-level" hidden></select>
                    </div>
                    <div class="buttons-for-level nsi-directory-page-buttons">
                        <button type="button" class="btn plus-field-button" data-toggle="modal"
                                data-target=".create-nsi-entry-modal" disabled>
                            +
                        </button>
                        <button type="button" class="btn minus-field-button" data-toggle="modal"
                                data-target=".create-nsi-entry-modal" disabled>
                            -
                        </button>
                    </div>
                </div>
                <hr>
            </div>
        </div>
</div>


</div>

<script src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/popper/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/side-navbar.js"></script>
<script src="${pageContext.request.contextPath}/js/content-inject.js"></script>
<script src="${pageContext.request.contextPath}/js/product_hierarchy.js"></script>
</body>
</html>
