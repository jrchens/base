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

        <div class="weui-gallery" id="gallery">
            <span class="weui-gallery__img" id="galleryImg"></span>
            <div class="weui-gallery__opr">
                <a href="javascript:" class="weui-gallery__del">
                    <i class="weui-icon-delete weui-icon_gallery-delete"></i>
                </a>
            </div>
        </div>

        <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <div class="weui-cell__bd">
                    <div class="weui-uploader">
                        <div class="weui-uploader__hd">
                            <p class="weui-uploader__title">图片上传</p>
                            <div class="weui-uploader__info">0/2</div>
                        </div>
                        <div class="weui-uploader__bd">
                            <ul class="weui-uploader__files" id="uploaderFiles">
                            </ul>
                            <div class="weui-uploader__input-box">
                                <%--<input id="uploaderInput" class="weui-uploader__input" type="file" accept="image/*" multiple/>--%>
                                    <a id="uploaderInput" class="weui-uploader__input" href="javascript:" ></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%--<div class="page__ft">--%>
        <%--<a href="javascript:home()"><img src="./images/icon_footer_link.png" /></a>--%>
    <%--</div>--%>
</div>

<script type="text/javascript">
    $.getJSON('${WEB_ROOT_CONTEXT}/mobile/signature?url=' + encodeURIComponent(location.href.split('#')[0]), {}, function (data,textStatus,jqXHR) {
        var resData = data.data;
        wx.config({
            debug: true,
            appId: resData.appId,
            timestamp: resData.timestamp,
            nonceStr: resData.nonceStr,
            signature: resData.signature,
            jsApiList: [
                "chooseImage","uploadImage","previewImage","downloadImage"
            ]
        });

//        var tmpl = '<li class="weui-uploader__file" style="background-image:url(#url#)"></li>',
//            $gallery = $("#gallery"), $galleryImg = $("#galleryImg"),
//            $uploaderInput = $("#uploaderInput"),
//            $uploaderFiles = $("#uploaderFiles")
//        ;
//
//        $uploaderInput.on("change", function(e){
//            var src, url = window.URL || window.webkitURL || window.mozURL, files = e.target.files;
//            for (var i = 0, len = files.length; i < len; ++i) {
//                var file = files[i];
//
//                if (url) {
//                    src = url.createObjectURL(file);
//                } else {
//                    src = e.target.result;
//                }
//
//                $uploaderFiles.append($(tmpl.replace('#url#', src)));
//            }
//        });
//
//        $uploaderFiles.on("click", "li", function(){
//            $galleryImg.attr("style", this.getAttribute("style"));
//            $gallery.fadeIn(100);
//        });
//        $gallery.on("click", function(){
//            $gallery.fadeOut(100);
//        });

        wx.ready(function () {
            $('#uploaderInput').on('click', function(e){
                wx.chooseImage({
                    count: 9, // 默认9
                    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
                    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
                    success: function (res) {
                        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                        for (var i = 0, len = localIds.length; i < len; ++i) {
                            wx.uploadImage({
                                localId: localIds[i], // 需要上传的图片的本地ID，由chooseImage接口获得
                                isShowProgressTips: 1, // 默认为1，显示进度提示
                                success: function (res) {
                                    var serverId = res.serverId; // 返回图片的服务器端ID
                                    $.post('${WEB_ROOT_CONTEXT}/mobile/engineering-progress', {serverId:serverId}, function (data,textStatus,jqXHR) {
                                        console.log(data);
                                    });
                                }
                            });
                        }
                    }
                });
            });

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