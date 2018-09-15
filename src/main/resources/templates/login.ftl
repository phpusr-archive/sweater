<#import "parts/common.ftl" as c />
<#import "parts/login.ftl" as l />

<@c.page>
<h2>Login page</h2>

<#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
    <div class="alert alert-danger" role="alert">
        ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
    </div>
</#if>

<@l.login "/login" "Sign in" true />

</@c.page>