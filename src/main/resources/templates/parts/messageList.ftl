<#include "security.ftl" />

<div class="card-columns">
<#list messages as message>
    <div class="card my-3">
        <#if message.filename??>
        <img class="card-img-top" src="/img/${message.filename}" />
        </#if>
        <div class="card-body">
            <h5 class="card-title">#${message.id} ${message.tag!}</h5>
            <a href="/user-messages/${message.author.id}"><h6 class="card-subtitle mb-2 text-muted">${message.author.username}</h6></a>
            <p class="card-text">
                ${message.text}<br/>
                <#if message.author.id == currentUserId>
                <a href="/user-messages/${message.author.id}?message=${message.id}">Edit</a>
                </#if>
            </p>
        </div>
    </div>
<#else>
    <div>No messages</div>
</#list>
</div>