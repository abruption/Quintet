<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%-- Google jQuery Library --%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">
    <c:forEach var="winner" items="${drawEvent}" varStatus="status">
        var data = {
            id : '${winner.ID}',
            title : '${title}',
            use_point: '${use_point}'
        }
        $.ajax({
            url: '/updateWinner',
            type: 'POST',
            data: data,

            success: function(result) {
                if (result == 1) {
                    location.href = '/event/manageEvent.do';
                    return alert('이벤트 추첨과 포인트 지급이 완료되었습니다.');
                } else {
                    location.href = '/event/manageEvent.do';
                    return alert('이미 추첨한 이벤트입니다.');
                }
            }
        });
    </c:forEach>
</script>
