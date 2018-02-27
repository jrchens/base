<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/26
  Time: 14:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp" %>

    <div class="page__hd">
        <h1 class="page__title">&#12288;</h1>
        <%--<p class="page__desc">&#12288;</p>--%>
    </div>

    <div class="page__bd page__bd_spacing">
        <%--<div class="weui-cells__title">&#12288;</div>--%>
        <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <div class="weui-cell__hd"><label class="weui-label">监理姓名</label></div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="text" id="viewname" name="viewname" placeholder="请输入监理姓名">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label">手机号码</label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="tel" id="mobile" name="mobile" placeholder="请输入手机号码">
                </div>
            </div>
        </div>

        <div class="weui-btn-area">
            <a class="weui-btn weui-btn_primary" href="javascript:" id="showLoadingToast">确定</a>
        </div>

    </div>



    <%--<div class="page__hd">--%>
        <%--<h1 class="page__title">Toast</h1>--%>
        <%--<p class="page__desc">弹出式提示</p>--%>
    <%--</div>--%>
    <%--<div class="page__bd page__bd_spacing">--%>
        <%--<a href="javascript:;" class="weui-btn weui-btn_default" id="showToast">成功提示</a>--%>
        <%--<a href="javascript:;" class="weui-btn weui-btn_default" id="showLoadingToast">加载中提示</a>--%>
    <%--</div>--%>
    <%--<div class="page__ft">--%>
        <%--<a href="javascript:home()"><img src="./images/icon_footer_link.png" /></a>--%>
    <%--</div>--%>
</div>

<script type="text/javascript">
    var openid = '${wxWebAccessToken.openid}';
    localStorage.setItem("openid",openid);
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
                openid: openid,
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