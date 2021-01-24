<%@ page pageEncoding="utf-8"%>
    <!doctype html>
    <html>

    <head>
        <meta charset="utf-8" />
        <title>正在跳转到支付页面...</title>
    </head>

    <body>
        <form method="post" id="sendRequestForm" enctype="application/x-www-form-urlencoded" action="https://xpay.tj9999.vip/api/Pay">
            <input type="hidden" name="amount" value="${payRequest.amount}" />
            <input type="hidden" name="appKey" value="${payRequest.appKey}" />
            <input type="hidden" name="callbackUrl" value="${payRequest.callbackUrl}" />
            <input type="hidden" name="channelId" value="${payRequest.channelId}" />
            <input type="hidden" name="mrn" value="${payRequest.mrn}" />
            <input type="hidden" name="returnUrl" value="${payRequest.returnUrl}" />
            <input type="hidden" name="sign" value="${payRequest.sign}" />
            <script>
                window.onload = function() {
                    document.getElementById('sendRequestForm').submit();
                }
            </script>
        </form>
    </body>

    </html>