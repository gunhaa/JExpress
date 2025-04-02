document.addEventListener("DOMContentLoaded", () => {

    const table = document.querySelector("#MAIN-API-DOCS table");

    const createApiDocs = () => {
        fetch("/api-docs/v1")
            .then(res => res.json())
            .then(res => {

                console.log(res);

                res.forEach(v => {

                    const fieldsStr = Object.entries(v.fields)
                        .map(([key, value]) => `${key}: ${value}`)
                        .join("<br>");

                    table.insertAdjacentHTML("beforeend", `
                        <tr>
                            <td>${v.url}</td>
                            <td>${v.returnType}</td>
                            <td>${fieldsStr}</td>
                        </tr>
                    `);
                });
            });
    };

    createApiDocs();
});
