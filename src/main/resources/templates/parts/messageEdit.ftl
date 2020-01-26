<a class="btn my-2" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    Message editor
</a>
<div class="collapse <#if message??>show</#if>" id="collapseExample">
    <div class="col-sm-6">
        <form method="post" enctype="multipart/form-data">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <input type="hidden" name="id" value="<#if message??>${message.id}</#if>" />

            <div class="form-group">
                <input type="text" name="text" class="form-control ${(textError??)?string('is-invalid', '')}"
                       placeholder="Введите сообщение" value="<#if message??>${message.text}</#if>" />
                <#if textError??>
                <div class="invalid-feedback">${textError}</div>
                </#if>
            </div>

            <div class="form-group">
                <input type="text" name="tag" class="form-control ${(tagError??)?string('is-invalid', '')}"
                       placeholder="Тэг" value="<#if message??>${message.tag}</#if>" />
                <#if tagError??>
                <div class="invalid-feedback">${tagError}</div>
                </#if>
            </div>

            <div class="custom-file">
                <input type="file" name="file" class="custom-file-input" />
                <label class="custom-file-label">Choose file</label>
            </div>

            <div class="form-group mt-3">
                <button type="submit" class="btn btn-primary">Save message</button>
            </div>
        </form>
    </div>
</div>