<#import "parts/common.ftl" as c />
<#import "parts/login.ftl" as l />

<@c.page>
<h2>Login page</h2>

${message!}

<@l.login "/login" "Sign in" true />

</@c.page>