<#import "parts/common.ftl" as c>

<@c.page>
<h2>User edit</h2>

<form action="/user" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <input type="hidden" name="userId" value="${user.id}" />
    <div>Name: <input type="text" name="username" value="${user.username}" /></div>

    <#list roles as role>
        <div>
            <label>
                <input type="checkbox" name="roles" value="${role}" ${user.roles?seq_contains(role)?string("checked", "")} />
                ${role}
            </label>
        </div>
    </#list>

    <button type="submit">Save</button>

</form>
</@c.page>