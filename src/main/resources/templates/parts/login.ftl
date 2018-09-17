<#include "security.ftl" />

<#macro login path submitName isLoginPage>
<form action="${path}" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />

    <div class="col-sm-6">
        <div class="form-group">
            <label for="username">User name</label>
            <input type="text" class="form-control ${(usernameError??)?string('is-invalid', '')}" name="username"
                   placeholder="Enter username" value="<#if user??>${user.username}</#if>" />
            <#if usernameError??>
            <div class="invalid-feedback">${usernameError}</div>
            </#if>
        </div>

        <#if !isLoginPage>
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control ${(emailError??)?string('is-invalid', '')}" name="email"
                   placeholder="Enter email" value="<#if user??>${user.email}</#if>" />
            <#if emailError??>
            <div class="invalid-feedback">${emailError}</div>
            </#if>
        </div>
        </#if>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control ${(passwordError??)?string('is-invalid', '')}" name="password"
                   placeholder="Password" />
            <#if passwordError??>
            <div class="invalid-feedback">${passwordError}</div>
            </#if>
        </div>

        <#if !isLoginPage>
        <div class="form-group">
            <label for="password2">Password confirm</label>
            <input type="password" class="form-control ${(password2Error??)?string('is-invalid', '')}" name="password2"
                   placeholder="Password confirm" />
            <#if password2Error??>
            <div class="invalid-feedback">${password2Error}</div>
            </#if>
        </div>
        <div>
            <div class="g-recaptcha mb-3" data-sitekey="6LdKn3AUAAAAANwFBrpiYHHldTAA3kHfcES67yun"></div>
            <#if captchaError??>
            <div class="alert alert-danger" role="alert">${captchaError}</div>
            </#if>
        </div>
        </#if>

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