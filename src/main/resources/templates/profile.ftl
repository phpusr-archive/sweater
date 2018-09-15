<#import "parts/common.ftl" as c />

<@c.page>
<h2>Profile</h2>
<h5>${username}</h5>

${message!}

<form method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />

    <div class="col-sm-6">
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control" name="email" placeholder="Enter email" value="${email}">
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" name="password" placeholder="Password">
        </div>

        <button type="submit" class="btn btn-primary">Save</button>
    </div>
</form>

</@c.page>