<#import "parts/common.ftl" as c />

<@c.page>
<div class="form-row">
    <form class="form-inline" method="get" action="/main">
        <input type="text" name="filter" class="form-control mr-1" value="${filter}" placeholder="Search by tag" />
        <button type="submit" class="btn btn-primary">Search</button>
    </form>
</div>

<a class="btn my-2" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    Add new message
</a>
<div class="collapse" id="collapseExample">
    <div class="col-sm-6">
        <form method="post" action="/add" enctype="multipart/form-data">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />

            <div class="form-group">
                <input type="text" name="text" class="form-control" placeholder="Введите сообщение" />
            </div>

            <div class="form-group">
                <input type="text" name="tag" class="form-control" placeholder="Тэг" />
            </div>

            <div class="custom-file">
                <input type="file" name="file" class="custom-file-input" />
                <label class="custom-file-label">Choose file</label>
            </div>

            <div class="form-group mt-3">
                <button type="submit" class="btn btn-primary">Добавить</button>
            </div>
        </form>
    </div>
</div>

<div class="card-columns">
<#list messages as message>
    <div class="card my-3">
        <#if message.filename??>
        <img class="card-img-top" src="/img/${message.filename}" />
        </#if>
        <div class="card-body">
            <h5 class="card-title">#${message.id} ${message.tag}</h5>
            <h6 class="card-subtitle mb-2 text-muted">${message.author.username}</h6>
            <p class="card-text">${message.text}</p>
        </div>
    </div>
<#else>
    <div>No messages</div>
</#list>
</div>
</@c.page>