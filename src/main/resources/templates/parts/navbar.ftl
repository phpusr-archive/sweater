<#include "security.ftl" />
<#import "login.ftl" as l />

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">Sweater</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/">Home</a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="/main">Messages</a>
            </li>

            <#if name??>
            <li class="nav-item">
                <a class="nav-link" href="/user-messages/${currentUserId}">My messages</a>
            </li>
            </#if>

            <#if isAdmin>
            <li class="nav-item">
                <a class="nav-link" href="/user">User list</a>
            </li>
            </#if>
        </ul>

        <@l.loginAndLogout/>
    </div>
</nav>