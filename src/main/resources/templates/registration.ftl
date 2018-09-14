<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
<h2>Add new user</h2>

<a href="/main">Main Page</a>

<div>${message!}</div>

<@l.login "/registration" "Registration" />

</@c.page>