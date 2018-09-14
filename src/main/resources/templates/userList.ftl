<#import "parts/common.ftl" as c />

<@c.page>
<h2>List of users</h2>

<table class="table table-striped table-hover">
    <thead>
        <tr>
            <th>Name</th>
            <th>Role</th>
            <th></th>
        </tr>
    </thead>
    <tbody>
    <#list users as user>
        <tr>
            <td>${user.username}</td>
            <td>
            <#list user.roles as role>
                ${role}<#sep>,
            </#list>
            </td>
            <td><a href="/user/${user.id}">Edit</a></td>
        </tr>
    </#list>
    </tbody>


</table>
</@c.page>