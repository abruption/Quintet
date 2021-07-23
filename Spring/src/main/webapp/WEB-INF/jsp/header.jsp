<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>


<header class="site-header sticky-top py-1">
    <nav class="container d-flex flex-column flex-md-row justify-content-between">
        <a class="py-2" href="/index.do" aria-label="Product">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" class="d-block mx-auto" role="img" viewBox="0 0 24 24"><title>Product</title><circle cx="12" cy="12" r="10"/><path d="M14.31 8l5.74 9.94M9.69 8h11.48M7.38 12l5.74-9.94M9.69 16L3.95 6.06M14.31 16H2.83m13.79-4l-5.74 9.94"/></svg>
        </a>
        <c:choose>
            <c:when test="${empty userId}">
                <li class="nav-item"><a class="nav-link" href='#' onclick="Login(); return false;">출석체크</a></li>
                <li class="nav-item"><a class="nav-link" href='<c:url value="/member/LoginMember.do"/>'>로그인</a></li>
                <li class="nav-item"><a class="nav-link" href='<c:url value="/member/checkMember.do"/>'>회원가입</a></li>
            </c:when>
            <c:when test="${userId eq 'admin'}">
                <li class="nav-item"><a class="nav-link">관리자님 환영합니다.</a></li>
                <li class="nav-item"><a class="nav-link" href='<c:url value="/member/searchMember.do"/>'>회원조회</a></li>
                <li class="nav-item"><a class="nav-link" href='<c:url value=""/>'>출석현황 조회</a></li>
                <li class="nav-item"><a class="nav-link" href='<c:url value="/member/pointRanking.do"/>'>포인트 적립 순위 조회</a></li>
                <li class="nav-item"><a class="nav-link" href='<c:url value="/member/updateMember.do"/>'>회원정보 수정</a></li>
                <li class="nav-item"><a class="nav-link" href='#' onclick="Logout(); return false;">로그아웃</a></li>
            </c:when>
            <c:otherwise>
                <li class="nav-item"><a class="nav-link">${userId}님 환영합니다.</a></li>
                <li class="nav-item"><a class="nav-link" href='#'>출석체크</a></li>
                <li class="nav-item"><a class="nav-link" href='<c:url value="/member/detailPoint.do"/>'>포인트 상세내역 조회</a></li>
                <li class="nav-item"><a class="nav-link" href='<c:url value="/member/updateMember.do"/>'>회원정보 수정</a></li>
                <li class="nav-item"><a class="nav-link" href='#' onclick="Logout(); return false;">로그아웃</a></li>
            </c:otherwise>
        </c:choose>
    </nav>
</header>