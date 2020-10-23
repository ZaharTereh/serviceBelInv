<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Линейка продукта</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tree.css">
</head>
<body>
<jsp:include page="../component/main-navbar.jsp"/>
<jsp:include page="../component/side-navbar.jsp"/>

<div id="page-content">
    <div class="row justify-content-center page-title">
        <p class="display-4">Линейка продукта</p>
    </div>
    <div id="product-group-list" hidden>
        <c:forEach items="${productGroups}" var="productGroup">
            <div class="productGroupData">
                <span class="id">${productGroup.id}</span>
                <span class="hiId">${productGroup.hiId}</span>
                <span class="name">${productGroup.name}</span>
                <span class="lev">${productGroup.lev}</span>
                <span class="leaf">${productGroup.leaf}</span>
                <span class="tableName">${productGroup.tableName}</span>
            </div>
        </c:forEach>
    </div>
    <div class="find-hierarchy-panel-header">
        <form method="get" action="${pageContext.request.contextPath}/hierarchy_update/get_hierarchy" class="find-form" id="find-form">
            <div class="find-hierarchy-header-panel">
                <select name="firstLevelProductGroup" class="group-hierarchy-list" id="firstLevelProductGroup"  required>
                    <option value="0" selected disabled>Бизнес-направление</option>
                    <c:forEach items="${productGroups}" var="productGroup">
                        <c:if test="${productGroup.lev eq 1}">
                            <option value="${productGroup.id}">${productGroup.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
                <select name="secondLevelProductGroup" class="group-hierarchy-list" id="secondLevelProductGroup"  required>
                    <option value="0" selected disabled>Класс продукта</option>
                </select>
                <select name="thirdLevelProductGroup" class="group-hierarchy-list" id="thirdLevelProductGroup"  required>
                    <option value="0" selected disabled>Группа продукта</option>
                </select>
                <select name="hierarchyGroup" class="hierarchy-list" id="hierarchy-list" required>
                    <option product-hierarchy="true" value="0" selected disabled>Иерархия линейки</option>
                </select>
            </div>
            <div class="find-button">
                <button type="submit" form="find-form" class="btn find-button">Поиск</button>
            </div>
        </form>
    </div>

    <c:if test="${not empty fullProductHierarchyDTO}">
        <div class="hierarchy">
            <div class="hierarchy-header">
                <h4 class="hierarchy-line-label">Иерархия линейки</h4>
                <label class="">Бизнес-направление: ${fullProductHierarchyDTO.firstProductGroup.name}</label><br>
                <label class="">Класс продукта: ${fullProductHierarchyDTO.secondProductGroup.name}</label><br>
                <label class="">Группа продукта: ${fullProductHierarchyDTO.productHierarchyDTO.productGroupDTO.name}</label>
                <br/>
                <label>Название иерархии: ${fullProductHierarchyDTO.productHierarchyDTO.name}</label>
                <input type="hidden" class="id" value="${fullProductHierarchyDTO.productHierarchyDTO.id}"/>
                <input type="hidden" class="productGroupId" value="${fullProductHierarchyDTO.productHierarchyDTO.productGroupDTO.id}">
                <input type="hidden" class="tableName" value="${fullProductHierarchyDTO.productHierarchyDTO.productGroupDTO.tableName}">
                <hr>
            </div>



            <c:forEach items="${fullProductHierarchyDTO.levels}" var="level">
                <div class="level-of-hierarchy">
                    <label class="level-current">Уровень ${level.level}</label>
                    <div class="level-data">
                        <label class="label-for-select" for="select-criterion-for-level">Название уровня</label>
                        <div class="level-values">
                            <select name="criterionForLevel" class="form-control criterionForLevel" id="select-criterion-for-level" required>
                                <option value="0" selected disabled>Название уровня</option>
                                <c:forEach items="${level.criterionList}" var="criterion">
                                    <option class="criterionForLevel" value="${criterion.id}">${criterion.name}</option>
                                </c:forEach>
                            </select>
                            <div class="product-table">
                                <table class="table table-bordered goodtable">
                                    <thead>
                                        <tr>
                                            <th scope="col">Название атрибута</th>
                                            <th scope="col">Значение</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:if test="${not empty fullProductHierarchyDTO.productList}">
                                        <c:forEach items="${level.fieldList}" var="field">
                                            <tr>
                                                <td class="fieldName" fieldName="${field.fieldName}" dataLength="${field.dataLength}" dataType="${field.dataType}" id="${field.id}">${field.name}</td>
                                                <c:forEach items="${fullProductHierarchyDTO.productList.get(0).realProduct}" var="entry">
                                                    <c:if test="${field.fieldName eq entry.key}">
                                                        <td class="value"><input name="${entry.key}" type="text" value="${entry.value}"></td>
                                                    </c:if>
                                                </c:forEach>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${empty fullProductHierarchyDTO.productList}">
                                        <c:forEach items="${level.fieldList}" var="field">
                                            <tr>
                                                <td class="fieldName" fieldName="${field.fieldName}" dataLength="${field.dataLength}" dataType="${field.dataType}" id="${field.id}">${field.name}</td>
                                                <td class="value"><input type="text" value=""/></td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="level-buttons nsi-directory-page-buttons">
                            <button type="button" class="btn create-nsi-modal-button save-change-level-button">
                                Сохранить
                            </button>
                            <button type="button" class="btn edit-nsi-modal-button cancel-change-level-button" >
                                Отмена
                            </button>
                        </div>
                    </div>
                </div>
                <hr>
            </c:forEach>

            <div class="last-level">
                <div class="level-data">
                    <label class="label-for-select">Уровень ${fullProductHierarchyDTO.levels.size()+1}</label>
                    <div class="level-values">
                        <div class="product-table">
                            <table id="product-table" class="table table-bordered goodtable">
                                <thead>
                                <tr>
                                    <th scope="col">Код продукта</th>
                                    <th scope="col">Название продукта</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <tr type="NEW">
                                        <td>Новый</td>
                                        <td>Новый</td>
                                    </tr>
                                <c:if test="${not empty fullProductHierarchyDTO.productList}">
                                    <c:forEach items="${fullProductHierarchyDTO.productList}" var="productDTO">
                                        <tr>
                                            <td class="info" hidden>
                                                <c:forEach items="${productDTO.realProduct}" var="entry">
                                                    <span data-update-hierarchy class="${entry.key}">${entry.value}</span>
                                                </c:forEach>
                                            </td>
                                            <td class="id" hidden>${productDTO.product.productId}</td>
                                            <td class="productCode">${productDTO.product.productCode}</td>
                                            <c:choose>
                                                <c:when test = "${fullProductHierarchyDTO.productHierarchyDTO.productGroupDTO.id eq 1}">
                                                    <td class="NAME">${productDTO.realProduct.get("NAME")}</td>
                                                </c:when>
                                                <c:when test = "${fullProductHierarchyDTO.productHierarchyDTO.productGroupDTO.id eq 2}">
                                                    <td class="PRODUCT_NAME">${productDTO.realProduct.get("PRODUCT_NAME")}</td>
                                                </c:when>
                                                <c:when test = "${fullProductHierarchyDTO.productHierarchyDTO.productGroupDTO.id eq 3}">
                                                    <td class="PRODUCT_NAME">${productDTO.realProduct.get("PRODUCT_NAME")}</td>
                                                </c:when>
                                            </c:choose>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
                        <div class="field-for-level">
                            <table class="table table-bordered level-table-for-fields">
                                <thead>
                                <tr>
                                    <th scope="col">Название атрибута</th>
                                    <th scope="col">Значение</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:if test="${not empty fullProductHierarchyDTO.productList}">
                                    <c:forEach items="${fullProductHierarchyDTO.freeFields}" var="field">
                                        <tr>
                                            <td class="fieldName" fieldName="${field.fieldName}" dataLength="${field.dataLength}" dataType="${field.dataType}" id="${field.id}">${field.name}</td>
                                            <c:forEach items="${fullProductHierarchyDTO.productList.get(0).realProduct}" var="entry">
                                                <c:if test="${field.fieldName eq entry.key}">
                                                    <td class="value"><input name="${entry.key}" type="text" value="${entry.value}"></td>
                                                </c:if>
                                            </c:forEach>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${empty fullProductHierarchyDTO.productList}">
                                    <c:forEach items="${fullProductHierarchyDTO.freeFields}" var="field">
                                        <tr>
                                            <td class="fieldName" fieldName="${field.fieldName}" dataLength="${field.dataLength}" dataType="${field.dataType}" id="${field.id}">${field.name}</td>
                                            <td class="value"><input type="text" value=""/></td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="level-buttons nsi-directory-page-buttons">
                        <button type="button" class="btn create-nsi-modal-button save-change-product-button">
                            Сохранить
                        </button>
                        <button type="button" class="btn edit-nsi-modal-button cancel-change-product-button">
                            Отмена
                        </button>
                    </div>
                </div>
            </div>

        </div>
    </c:if>

</div>


<script src="${pageContext.request.contextPath}/js/tree.js"></script>


<script src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/popper/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/side-navbar.js"></script>
<script src="${pageContext.request.contextPath}/js/content-inject.js"></script>
<script src="${pageContext.request.contextPath}/js/hierarchy_update.js"></script>
</body>
</html>