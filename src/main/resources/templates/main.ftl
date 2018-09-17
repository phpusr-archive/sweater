<#import "parts/common.ftl" as c />

<@c.page>
<div class="form-row">
    <form class="form-inline" method="get" action="/main">
        <input type="text" name="filter" class="form-control mr-1" value="${filter!}" placeholder="Search by tag" />
        <button type="submit" class="btn btn-primary">Search</button>
    </form>
</div>

<#include "parts/messageEdit.ftl" />
<#include "parts/messageList.ftl" />
</@c.page>