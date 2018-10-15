<#macro pager url page>
    <#if page.totalPages gt 7>
        <#assign
            totalPages = page.totalPages
            pageIndex = page.number

            <#-- Кол-во выводимых соседних элементов -->
            neighborCount = 2

            <#-- Индекс первого элемента после начального блока -->
            firstNotBlockElementIndex = 1 + neighborCount
            <#-- Индекс первого элемента до конечного блока -->
            firstNotEndBlockElementIndex = totalPages - neighborCount - 2

            <#-- Предпоследняя страница в начальном блоке -->
            penultimateStartIndex = firstNotBlockElementIndex - 2
            <#-- Предпослденяя страница в конечном блоке -->
            penultimateEndIndex = firstNotEndBlockElementIndex + 2

            <#-- Страницы в начальном блоке -->
            startPages = [0, 1, 2]
            <#-- Страницы в конечном блоке -->
            endPages = [totalPages - 3, totalPages - 2, totalPages -1]
            <#-- Первая страница и разрыв -->
            splitLeftPages = [0, -1]
            <#-- Разрыв и последняя страница -->
            splitRightPages = [-1, totalPages - 1]
            <#-- Соседи слева -->
            neighborLeftPages = [pageIndex - 2, pageIndex - 1]
            <#-- Соседи справа -->
            neighborRightPages = [pageIndex + 1, pageIndex + 2]
            empty = []


            head = (pageIndex > firstNotBlockElementIndex)?then(splitLeftPages, startPages)
            beforeCurrentPage = (pageIndex > firstNotBlockElementIndex && pageIndex < penultimateEndIndex)?then(neighborLeftPages, empty)
            afterCurrentPage = (pageIndex > penultimateStartIndex && pageIndex < firstNotEndBlockElementIndex)?then(neighborRightPages, empty)
            tail = (pageIndex < firstNotEndBlockElementIndex)?then(splitRightPages, endPages)

            currentPage = (pageIndex > (firstNotBlockElementIndex - 1) && pageIndex < (firstNotEndBlockElementIndex + 1))?then([pageIndex], empty)

            body = head + beforeCurrentPage + currentPage + afterCurrentPage + tail
        />
    <#else>
        <#assign body = 0..page.totalPages - 1 />
    </#if>
    <div class="container mt-3">
        <div class="row">
            <ul class="pagination pagination col justify-content-center">
                <li class="page-item disabled">
                    <a class="page-link" href="#" tabindex="-1">Pages</a>
                </li>
                <#list body as p>
                    <#if p == page.number>
                        <li class="page-item active">
                            <a class="page-link" href="#" tabindex="-1">${p + 1}</a>
                        </li>
                    <#elseif p == -1>
                        <li class="page-item disabled">
                            <a class="page-link" href="#" tabindex="-1">...</a>
                        </li>
                    <#else>
                        <li class="page-item">
                            <a class="page-link" href="${url}?page=${p}&size=${page.size}" tabindex="-1">${p + 1}</a>
                        </li>
                    </#if>
                </#list>
            </ul>

            <ul class="pagination col justify-content-center">
                <li class="page-item disabled">
                    <a class="page-link" href="#" tabindex="-1">Elements on page</a>
                </li>
                <#list [5, 10, 25, 50] as size>
                    <#if size == page.size>
                        <li class="page-item active">
                            <a class="page-link" href="#" tabindex="-1">${size}</a>
                        </li>
                    <#else>
                        <li class="page-item">
                            <a class="page-link" href="${url}?page=${page.number}&size=${size}" tabindex="-1">${size}</a>
                        </li>
                    </#if>
                </#list>
            </ul>
        </div>
    </div>
</#macro>

