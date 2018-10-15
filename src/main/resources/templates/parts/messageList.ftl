<#include "security.ftl" />
<#import "pager.ftl" as p>

<@p.pager url page />

<div class="card-columns">
    <#list page.content as message>
        <div class="card my-3" data-id="${message.id}">
            <#if message.filename??>
            <img class="card-img-top" src="/img/${message.filename}" />
            </#if>
            <div class="card-body">
                <h5 class="card-title">#${message.id} ${message.tag!}</h5>
                <a href="/user-messages/${message.author.id}"><h6 class="card-subtitle mb-2 text-muted">${message.author.username}</h6></a>
                <p class="card-text">
                    ${message.text}<br/>
                </p>
                <div class="container">
                    <div class="row">
                        <#if message.author.id == currentUserId>
                            <a href="/user-messages/${message.author.id}?message=${message.id}" class="col">Edit</a>
                        </#if>
                        <a href="/messages/${message.id}/like" class="col align-self-center">
                            <#if message.meLiked!>
                                <i class="fas fa-heart"></i>
                            <#else>
                                <i class="far fa-heart"></i>
                            </#if>
                            ${message.likes}
                        </a>
                    </div>
                </div>
            </div>
        </div>
    <#else>
        <div>No messages</div>
    </#list>
</div>

<@p.pager url page />
