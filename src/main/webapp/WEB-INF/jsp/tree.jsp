<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Tree</title>

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
            <ul class="Container">
                <c:forEach items="${tree.child}" var="element">
                    <c:set var="element" value="${element}" scope="request"/>
                    <jsp:include page="treeElement.jsp"/>
                </c:forEach>
            </ul>
        </div>
    </div>


</body>
</html>
