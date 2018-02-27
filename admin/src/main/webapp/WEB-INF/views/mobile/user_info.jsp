<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/26
  Time: 14:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp" %>

    <%--<div class="page__hd">--%>
        <%--<h1 class="page__title">&#12288;</h1>--%>
        <%--<p class="page__desc">&#12288;</p>--%>
    <%--</div>--%>

    <div class="page__bd">
        userinfo & user project,section,engineering
    </div>

    <%--<div class="page__ft">--%>
        <%--<a href="javascript:home()"><img src="./images/icon_footer_link.png" /></a>--%>
    <%--</div>--%>
</div>

<script type="text/javascript">
    // toast
//    $(function(){
//        var $toast = $('#toast');
//        $('#showToast').on('click', function(){
//            if ($toast.css('display') != 'none') return;
//
//            $toast.fadeIn(100);
//            setTimeout(function () {
//                $toast.fadeOut(100);
//            }, 2000);
//        });
//    });

    // loading
    $(function(){
        var $loadingToast = $('#loadingToast');
        $('#showLoadingToast').on('click', function(){
            if ($loadingToast.css('display') != 'none') return;

            $loadingToast.fadeIn(100);

            var reqData = {
                openid: '${wxWebAccessToken.openid}',
                viewname: $('#viewname').val(),
                mobile: $('#mobile').val()
            };
            $.post('${WEB_ROOT_CONTEXT}/mobile/register',reqData,function(data,textStatus,jqXHR){
                if(data.success){
                    $loadingToast.fadeOut(100);

                    if ($toast.css('display') != 'none') return;

                    $toast.fadeIn(100);
                    setTimeout(function () {
                        $toast.fadeOut(100);
                    }, 2000);

                }
            },'json').always(function(data_jqXHR, textStatus, jqXHR_errorThrown){

            });

//            setTimeout(function () {
//                $loadingToast.fadeOut(100);
//            }, 2000);
        });
    });
</script>



<%--<script type="text/javascript">--%>
    <%--$(function(){--%>

        <%--var $toast = $('#toast');--%>
        <%--var $loadingToast = $('#loadingToast');--%>

        <%--$('#showLoadingToast').on('click', function(){--%>

            <%--console.log(111111);--%>

            <%--if ($loadingToast.css('display') != 'none') return;--%>

            <%--$loadingToast.fadeIn(100);--%>

            <%--setTimeout(function () {--%>
                <%--$loadingToast.fadeOut(100);--%>
            <%--}, 5000);--%>

        <%--});--%>
    <%--});--%>


<%--</script>--%>

    <%--<div class="page__ft">--%>
    <%--<a href="javascript:;">句容市农业资源开发科技服务中心</a>--%>
    <%--</div>--%>