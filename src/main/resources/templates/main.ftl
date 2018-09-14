<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
<div>
    <@l.logout />
    <span><a href="/user">User list</a></span>
</div>

<div>
    <form method="post" action="/add">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <input type="text" name="text" placeholder="Введите сообщение" />
        <input type="text" name="tag" placeholder="Тэг" />
        <button type="submit">Добавить</button>
    </form>
</div>

<div>Список сообщений</div>

<form method="get" action="/main">
    <input type="text" name="filter" value="${filter}" />
    <button type="submit">Фильтр</button>
</form>

<#list messages as message>
<div>
    <b>${message.id}</b>
    <span>${message.text}</span>
    <i>${message.tag}</i>
    <strong>${message.author.username}</strong>
</div>
<#else>
<div>No messages</div>
</#list>
</@c.page>