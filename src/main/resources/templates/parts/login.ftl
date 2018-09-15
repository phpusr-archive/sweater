<#include "security.ftl" />

<#macro login path submitName isLoginPage>
<form action="${path}" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />

    <div class="col-sm-6">
        <div class="form-group">
            <label for="username">User name</label>
            <input type="text" class="form-control" name="username" placeholder="Enter username">
        </div>

        <#if !isLoginPage>
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control" name="email" placeholder="Enter email">
        </div>
        </#if>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" name="password" placeholder="Password">
        </div>

        <#if isLoginPage>
        <div class="form-group form-check">
            <input type="checkbox" class="form-check-input">
            <label class="form-check-label" for="rememberMe">Remember me</label>
        </div>
        </#if>

        <button type="submit" class="btn btn-primary">${submitName}</button>
        <#if isLoginPage>or <a href="/registration">Registration</a></#if>
    </div>
</form>
</#macro>

<#macro loginAndLogout>
    <#if name??>
        <a href="/user/profile">${name!'-'}</a>

        <form action="/logout" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <button type="submit" class="btn btn-link">Sign out</button>
        </form>
    <#else>
        <a href="/login">Log in</a>
    </#if>
</#macro>