<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>购物车</title>


    <%--静态包含base标签，css样式，jQuery文件--%>
    <%@include file="/pages/common/head.jsp" %>
</head>
<body>

<div id="header">
    <img class="logo_img" alt="" src="static/img/logo.gif">
    <span class="wel_word">购物车</span>

    <script type="text/javascript">
        $(function(){
            //给【删除】绑定单击事件
            $("a.deleteItem").click(function () {
                return confirm("确定删除【"+ $(this).parent().parent().find("td:first").text() + "】？")
            });

            //给【清空购物车】绑定单击事件
            $("#clearCart").click(function () {
                return confirm("确定清空购物车吗？")
            });

        //    给修改商品数量绑定onchange内容发生改变事件
            $(".updateCount").change(function () {
               // 获取商品名称
               var name= $(this).parent().parent().find("td:first").text();
                var id = $(this).attr('bookId');
               //获取商品数量
                var count = this.value;
               if(confirm("你确定将【"+name+"】商品修改数量为："+count+"吗？")){
               //    发起请求，给服务器保存修改
                   location.href="http://localhost:8080/book/cartServlet?" +
                       "action=updateCount&count=" + count + "&id=" + id;
               }else{
                   //de属性是表单项DOM对象的属性
                   this.value = this.defaultValue;
               }
            });

        });

    </script>


    <%--			静态包含登录成功之后的菜单--%>
    <%@include file="/pages/common/login_success_menu.jsp" %>
</div>

<div id="main">

    <table>
        <tr>
            <td>商品名称</td>
            <td>数量</td>
            <td>单价</td>
            <td>金额</td>
            <td>操作</td>
        </tr>


        <c:if test="${empty sessionScope.cart.items}">
            <%--				购物车清空的情况下--%>
            <tr>
                <td colspan="5"><a href="index.jsp">当前购物车为空!去购物吧</a></td>
            </tr>
        </c:if>

        <c:if test="${not empty sessionScope.cart.items}">
            <%--				购物车bu空的情况下--%>

            <c:forEach items="${sessionScope.cart.items}" var="entry">
                <tr>
                    <td>${entry.value.name}</td>
                    <td>
                        <input class="updateCount" style="width: 80px;"
                               bookId = "${entry.value.id}"
                               type="text" value="${entry.value.count}">
                    </td>
                    <td>${entry.value.price}</td>
                    <td>${entry.value.totalPrice}</td>
                    <td><a class="deleteItem" href="cartServlet?action=deleteItem&id=${entry.value.id}">删除</a></td>
                </tr>
            </c:forEach>
        </c:if>


    </table>
    <%--		购物车非空才输出--%>
    <c:if test="${not empty sessionScope.cart.items}">
        <div class="cart_info">
            <span class="cart_span">购物车中共有<span class="b_count">${sessionScope.cart.totalCount}</span>件商品</span>
            <span class="cart_span">总金额<span class="b_price">${sessionScope.cart.totalPrice}</span>元</span>
            <span class="cart_span"><a id="clearCart" href="cartServlet?action=clear">清空购物车</a></span>
            <span class="cart_span"><a href="orderServlet?action=createOrder">去结账</a></span>
        </div>
    </c:if>

</div>

<%--	静态页面页脚--%>
<%@include file="/pages/common/footer.jsp" %>
</body>
</html>