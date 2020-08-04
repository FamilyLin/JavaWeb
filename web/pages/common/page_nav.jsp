<%--
  Created by IntelliJ IDEA.
  User: Family_Lin
  Date: 2020/8/2
  Time: 20:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--分页条的开始--%>
<div id="page_nav">
    <%--			大于首页，才显示--%>
    <c:if test="${requestScope.page.pageNo > 1}">
        <a href="${requestScope.page.url}&pageNo=1">首页</a>
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo-1}">上一页</a>
    </c:if>

    <%--页码输出的开始--%>

    <c:choose>
        <%--	情况一：如果总页码小于等于5，页码的显示范围为1-总页码--%>
        <c:when test="${requestScope.page.pageTotal <= 5}">
            <c:set var="begin" value="1"/>
            <c:set var="end" value="5"/>
        </c:when>
        <%--情况二：总页码数大于5--%>
        <c:when test="${requestScope.page.pageTotal > 5}">
            <c:choose>
                <%--            小情况1：当前页码为前面3个--%>
                <c:when test="${requestScope.page.pageNo <= 3}">
                    <c:set var="begin" value="1"/>
                    <c:set var="end" value="5"/>
                </c:when>
                <%--            小情况2：当前页码为最后三个--%>
                <c:when test="${requestScope.page.pageNo >=requestScope.page.pageTotal-3}">
                    <c:set var="begin" value="${requestScope.page.pageTotal-4}"/>
                    <c:set var="end" value="${requestScope.page.pageTotal}"/>
                </c:when>
                <%--            页码在中间时--%>
                <c:otherwise>
                    <c:set var="begin" value="${requestScope.page.pageNo-2}"/>
                    <c:set var="end" value="${requestScope.page.pageNo + 2}"/>

                </c:otherwise>
            </c:choose>
        </c:when>
    </c:choose>

    <c:forEach begin="${begin}" end="${end}" var="i">
        <c:if test="${i == requestScope.page.pageNo}">
            【${i}】
        </c:if>
        <c:if test="${i != requestScope.page.pageNo}">
            <a href="${requestScope.page.url}&pageNo=${i}">${i}</a>
        </c:if>
    </c:forEach>


    <%--页码输出的结束--%>
    <%--			如果是最后一页，不显示“下一页”--%>
    <c:if test="${requestScope.page.pageNo < requestScope.page.pageTotal}">
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo+1}">下一页</a>
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageTotal}">末页</a>
    </c:if>

    共${requestScope.page.pageTotal}页，
    ${requestScope.page.pageTotalCount}条记录

    到第<input value="${param.pageNo}" name="pn" id="pn_input"/>页
    <input id="searchPageBtn" type="button" value="确定">

    <%--			跳转页码超出索引需要控制
    获取总页码
    var pagetotal = ${requestScope.page.pageTotal};
    --%>
    <script type="text/javascript">
        $(function(){
            $("#searchPageBtn").click(function(){
                var pageNo = $("#pn_input").val();
                //javaScript中的location地址栏对象中有一个属性href,可以获取浏览器地址栏中的地址
                location.href = "${pageScope.basePath}${requestScope.page.url}&pageNo=" + pageNo;

            });
        });
    </script>
</div>
<%--分页条的结束--%>