<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Tree</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tree.css">
</head>
<body>

<!-- <div class="tree">
    <div onclick="tree_toggle(arguments[0])">
        <div>Root</div>
        <ul class="Container">
            <li class="Node IsRoot ExpandClosed">
                <div class="Expand"></div>
                <div class="Content">Item 1</div>
                <ul class="Container">
                    <li class="Node ExpandClosed">
                        <div class="Expand"></div>
                        <div class="Content">Item 1.1</div>
                        <ul class="Container">
                            <li class="Node ExpandLeaf IsLast">
                                <div class="Expand"></div>
                                <div class="Content">Item 1.1.2</div>
                            </li>
                        </ul>
                    </li>
                    <li class="Node ExpandLeaf IsLast">
                        <div class="Expand"></div>
                        <div class="Content">Item 1.2</div>
                    </li>
                </ul>
            </li>
            <li class="Node IsRoot ExpandClosed">
                <div class="Expand"></div>
                <div class="Content">Item 2</div>
                <ul class="Container">
                    <li class="Node ExpandLeaf IsLast">
                        <div class="Expand"></div>
                        <div class="Content">Item 2.1</div>
                    </li>
                </ul>
            </li>
            <li class="Node IsRoot ExpandClosed">
                <div class="Expand"></div>
                <div class="Content">Item 3</div>
                <ul class="Container">
                    <li class="Node ExpandLeaf IsLast">
                        <div class="Expand"></div>
                        <div class="Content">Item 3.1</div>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</div><-->


    <div class="tree">
        <div onclick="tree_toggle(arguments[0])">
            <div>Root</div>
            <ul class="Container">
                <c:forEach items="${tree}" var="element">
                    <li class="Node ExpandClosed">
                        <div class="Expand"></div>
                        <div class="Info" hidden>
                            <div class="id">${element.id}</div>
                            <div class="hiId">${element.hiId}</div>
                            <div class="lev">${element.lev}</div>
                            <div class="typ">${element.typ}</div>
                        </div>
                        <div class="Content">${element.name}</div>
                        <c:if test="${not empty element.child}">
                            <ul class="Container"></ul>
                        </c:if>
                    </li>
                </c:forEach>
                <li class="Node ExpandClosed">
                    <div class="Expand"></div>
                    <div class="Content">Item 1</div>
                    <ul class="Container">
                        <li class="Node ExpandClosed">
                            <div class="Expand"></div>
                            <div class="Content">Item 1.1</div>
                            <ul class="Container">
                                <li class="Node ExpandLeaf">
                                    <div class="Expand"></div>
                                    <div class="Content">Item 1.1.2</div>
                                </li>
                            </ul>
                        </li>
                        <li class="Node ExpandLeaf">
                            <div class="Expand"></div>
                            <div class="Content">Item 1.2</div>
                        </li>
                    </ul>
                </li>
                <li class="Node ExpandClosed">
                    <div class="Expand"></div>
                    <div class="Content">Item 2</div>
                    <ul class="Container">
                        <li class="Node ExpandLeaf">
                            <div class="Expand"></div>
                            <div class="Content">Item 2.1</div>
                        </li>
                    </ul>
                </li>
                <li class="Node ExpandClosed">
                    <div class="Expand"></div>
                    <div class="Content">Item 3</div>
                    <ul class="Container">
                        <li class="Node ExpandLeaf">
                            <div class="Expand"></div>
                            <div class="Content">Item 3.1</div>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>




    <script src="${pageContext.request.contextPath}/js/tree.js"></script>
</body>
</html>
