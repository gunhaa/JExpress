document.addEventListener("DOMContentLoaded", async () => {

    const table = document.querySelector("#MAIN-API-DOCS table");

    const createDocs = async (url) => {
        const res = await fetch(url);
        const data = await res.json();

        data.sort((a, b) => a.url.length - b.url.length);

        data.forEach(v => {
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
    };

    try {
        await createDocs("/api-docs/get/v1");
        await createDocs("/api-docs/post/v1");
    } catch (err) {
        console.error("err:", err);
    }
});
