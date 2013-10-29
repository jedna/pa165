<%@page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
 <head>
 <title>Home</title>
 </head>
 <body>
 <h1>Hooray, it works !</h1>
 <ul>
 <li><a href="${pageContext.request.contextPath}/text/direct?a=1&b=ccc">text directly from servlet</a></li>
 <li><a href="${pageContext.request.contextPath}/file">file</a></li>
 <li><a href="${pageContext.request.contextPath}/redirect">redirect</a></li>
 <li><a href="${pageContext.request.contextPath}/text/bla">intentional error</a></li>
 </ul>
 <h2>will work later</h2>
 <ul>
 <li><a href="${pageContext.request.contextPath}/text/fromjsp">text from JSP</a></li>
 <li><a href="${pageContext.request.contextPath}/protected/protectedfile.txt">protected by password</a></li>
 </ul>
 </body>
</html>