<#import "parts/common.ftl" as c />

<@c.page>
<h2>User edit</h2>

<div class="col-sm-3">
    <form action="/user" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />

        <input type="hidden" name="userId" value="${user.id}" />

        <div class="form-group">
            <label for="username">Name</label>
            <input type="text" name="username" value="${user.username}" class="form-control" />
        </div>

        <#list roles as role>
            <div class="form-group form-check">
                <input type="checkbox" name="roles" class="form-check-input" value="${role}" ${user.roles?seq_contains(role)?string("checked", "")} />
                <label class="form-check-label">${role}</label>
            </div>
        </#list>

        <button type="submit" class="btn btn-primary">Save</button>

    </form>
</div>
</@c.page>