document.addEventListener("DOMContentLoaded", function() {
    const BASE_URL = 'http://localhost:8080';

    const modal = document.getElementById("modal");
    const btn = document.getElementById("open-modal");
    const span = document.getElementsByClassName("close")[0];
    const saveBtn = document.getElementById("save-category");
    const categoryTable = document.getElementById("category-table").getElementsByTagName('tbody')[0];

    btn.onclick = function() {
        modal.style.display = "block";
        saveBtn.innerHTML = "Salvar Categoria";
        document.getElementById("create-category-form").dataset.action = "create";
    }

    span.onclick = function() {
        modal.style.display = "none";
    }

    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    loadCategories();

    function loadCategories() {
        fetch(`${BASE_URL}/category`)
            .then(response => response.json())
            .then(categories => {
                categoryTable.innerHTML = "";

                categories.forEach(category => {
                    var row = categoryTable.insertRow();
                    row.setAttribute('data-id', category.idCategory);
                    row.innerHTML = `
                        <td>${category.name}</td>
                        <td>${category.status == 'A' ? 'Ativo' : 'Inativo'}</td>
                        <td>
                            <button class="edit-btn">Editar</button>
                            <button class="delete-btn">Excluir</button>
                        </td>
                    `;

                    row.querySelector('.edit-btn').addEventListener('click', () => editCategory(category.idCategory));
                    row.querySelector('.delete-btn').addEventListener('click', () => deleteCategory(category.idCategory));
                });
            })
            .catch(error => {
                console.error('Erro ao carregar categorias:', error);
                alert('Erro ao carregar categorias: ' + error.message);
            });
    }

    function editCategory(id) {
        fetch(`${BASE_URL}/category/${id}`)
            .then(response => response.json())
            .then(category => {
                if (document.getElementById("category-id")) {
                    document.getElementById("category-id").value = category.idCategory;
                }
                if (document.getElementById("category-name")) {
                    document.getElementById("category-name").value = category.name;
                }
                if (document.getElementById("category-status")) {
                    document.getElementById("category-status").value = category.status;
                }

                saveBtn.innerHTML = "Editar Categoria";
                document.getElementById("create-category-form").dataset.action = "edit";

                modal.style.display = "block";
            })
            .catch(error => {
                console.error('Erro ao carregar categoria para edição:', error);
                alert('Erro ao carregar categoria para edição: ' + error.message);
            });
    }

    function deleteCategory(id) {
        if (confirm("Tem certeza que deseja excluir esta categoria?")) {
            fetch(`${BASE_URL}/category/${id}`, {
                method: 'DELETE'
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao excluir categoria: ' + response.statusText);
                }
                return response.text();
            })
            .then(data => {
                alert('Categoria excluída com sucesso!');
                loadCategories(); 
            })
            .catch(error => {
                console.error('Erro:', error);
                alert('Erro ao excluir categoria: ' + error.message);
            });
        }
    }

    var form = document.getElementById("create-category-form");
    form.addEventListener("submit", function(event) {
        event.preventDefault(); 

        var formData = new FormData(form); 
        var action = form.dataset.action; 

        var url = `${BASE_URL}/category`;
        var method = 'POST'; 

        if (action === "edit") {
            var categoryId = document.getElementById("category-id").value;
            url += `/${categoryId}`;
            method = 'PUT';
        }

        fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(Object.fromEntries(formData))
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Erro ao ${action === 'edit' ? 'atualizar' : 'salvar'} categoria: ${response.statusText}`);
            }
            return response.json();
        })
        .then(data => {
            alert(`Categoria ${action === 'edit' ? 'atualizada' : 'salva'} com sucesso!`);
            modal.style.display = "none"; 
            form.reset();
            loadCategories();
        })
        .catch(error => {
            console.error('Erro ao salvar categoria:', error);
            alert(`Erro ao ${action === 'edit' ? 'atualizar' : 'salvar'} categoria: ${error.message}`);
        });
    });
});
