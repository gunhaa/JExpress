document.addEventListener("DOMContentLoaded", async () => {

    const table = document.querySelector("#MAIN-API-DOCS table");

    const createGetDocs = async () => {
        await fetch("/api-docs/get/v1")
            .then(res => res.json())
            .then(res => {

                console.log(res);

                res.forEach(v => {

                    const fieldsStr = Object.entries(v.fields)
                        .map(([key, value]) => `${key}: ${value}`)
                        .join("<br>");

                    table.insertAdjacentHTML("beforeend", `
                        <tr>
                            <td>${v.customHttpMethod}</td>
                            <td>${v.url}</td>
                            <td>${v.returnType}</td>
                            <td>${fieldsStr}</td>
                        </tr>
                    `);
                });
            });
    };

        const createPostDocs = async () => {
            await fetch("/api-docs/post/v1")
                .then(res => res.json())
                .then(res => {

                    console.log(res);

                    res.forEach(v => {

                        const fieldsStr = Object.entries(v.fields)
                            .map(([key, value]) => `${key}: ${value}`)
                            .join("<br>");

                        table.insertAdjacentHTML("beforeend", `
                            <tr>
                                <td>${v.customHttpMethod}</td>
                                <td>${v.url}</td>
                                <td>${v.returnType}</td>
                                <td>${fieldsStr}</td>
                            </tr>
                        `);
                    });
                });
        };

    await createGetDocs();
    await createPostDocs();
});
