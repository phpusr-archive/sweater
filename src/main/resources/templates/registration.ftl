<#import "parts/common.ftl" as c />
<#import "parts/login.ftl" as l />

<@c.page>
<h2>Registration</h2>

<div>${message!}</div>

<@l.login "/registration" "Registration" false />

</@c.page>