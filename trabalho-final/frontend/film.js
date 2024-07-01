document.addEventListener("DOMContentLoaded", function() {
    const BASE_URL = 'http://localhost:8080'; 
    
    const modal = document.getElementById("modal");
    const btn = document.getElementById("open-modal");
    const span = document.querySelector(".close");
    const saveBtn = document.getElementById("save-film");
    const filmTable = document.getElementById("film-table").getElementsByTagName('tbody')[0];
    const searchInput = document.getElementById("searchInput");
    const searchBtn = document.getElementById("searchBtn");
    const filmCategorySelect = document.getElementById("film-category");
    const form = document.getElementById("create-film-form");

    loadFilms();

    btn.addEventListener('click', openModal);
    span.addEventListener('click', closeModal);
    window.addEventListener('click', outsideClick);

    form.addEventListener("submit", submitForm);

    searchBtn.addEventListener('click', searchFilms);

    function loadFilms() {
        fetch(`${BASE_URL}/film`)
            .then(handleResponse)
            .then(displayFilms)
            .catch(handleError);
    }

    function openModal() {
        modal.style.display = "block";
        saveBtn.innerHTML = "Salvar Filme";
        form.dataset.action = "create";

        clearSelectOptions(filmCategorySelect);

        fetchCategories();
    }

    function closeModal() {
        modal.style.display = "none";
    }

    function outsideClick(event) {
        if (event.target == modal) {
            closeModal();
        }
    }

    function fetchCategories() {
        fetch(`${BASE_URL}/category`)
            .then(handleResponse)
            .then(displayCategories)
            .catch(handleError);
    }

    function displayCategories(categories) {
        categories.forEach(category => {
            const option = document.createElement('option');
            option.value = category.idCategory;
            option.textContent = category.name;
            filmCategorySelect.appendChild(option);
        });
    }

    function clearSelectOptions(selectElement) {
        selectElement.innerHTML = "";
    }

    function handleResponse(response) {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
    }

    function handleError(error) {
        console.error('Erro:', error);
        alert('Erro ao carregar dados da API: ' + error.message);
    }

    function submitForm(event) {
        event.preventDefault(); 
        const formData = new FormData(form);
        const action = form.dataset.action;
        const url = action === "edit" ? `${BASE_URL}/film/${formData.get('id')}` : `${BASE_URL}/film`;
        const method = action === "edit" ? 'PUT' : 'POST';

        fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(Object.fromEntries(formData))
        })
        .then(handleResponse)
        .then(data => {
            alert(`Filme ${action === 'edit' ? 'atualizado' : 'salvo'} com sucesso!`);
            closeModal();
            form.reset();
            loadFilms();
        })
        .catch(handleError);
    }

    function searchFilms() {
        const searchValue = searchInput.value.trim().toLowerCase();

        fetch(`${BASE_URL}/film`)
            .then(handleResponse)
            .then(films => {
                const filteredFilms = films.filter(film =>
                    film.title.toLowerCase().includes(searchValue)
                );
                displayFilms(filteredFilms);
            })
            .catch(handleError);
    }

    function displayFilms(films) {
        filmTable.innerHTML = "";
        films.forEach(film => {
            const row = filmTable.insertRow();
            row.setAttribute('data-id', film.idFilm);
            row.innerHTML = `
                <td>${film.title}</td>
                <td>${film.synopsis}</td>
                <td>${film.releaseYear}</td>
                <td>${film.duration}</td>
                <td>${film.language}</td>
                <td>${film.categoryModel.name}</td>
                <td>
                    <button class="edit-btn" data-id="${film.idFilm}">Editar</button>
                    <button class="delete-btn" data-id="${film.idFilm}">Excluir</button>
                </td>
            `;
            row.querySelector('.edit-btn').addEventListener('click', () => editFilm(film.idFilm));
            row.querySelector('.delete-btn').addEventListener('click', () => deleteFilm(film.idFilm));
        });
    }

function editFilm(id) {
    fetch(`${BASE_URL}/film/${id}`)
        .then(handleResponse)
        .then(film => {
            if (document.getElementById("film-id")) {
                document.getElementById("film-id").value = film.idFilm;
            }
            if (document.getElementById("film-title")) {
                document.getElementById("film-title").value = film.title;
            }
            if (document.getElementById("film-synopsis")) {
                document.getElementById("film-synopsis").value = film.synopsis;
            }
            if (document.getElementById("film-release-year")) {
                document.getElementById("film-release-year").value = film.releaseYear;
            }
            if (document.getElementById("film-duration")) {
                document.getElementById("film-duration").value = film.duration;
            }
            if (document.getElementById("film-language")) {
                document.getElementById("film-language").value = film.language;
            }
            if (document.getElementById("film-category")) {
                fetchCategoriesAndSelect(film.categoryModel.idCategory);
            }

            saveBtn.innerHTML = "Editar Filme";
            form.dataset.action = "edit";

            modal.style.display = "block";
        })
        .catch(error => {
            console.error('Erro ao carregar filme para edição:', error);
            alert('Erro ao carregar filme para edição: ' + error.message);
        });
}

function fetchCategoriesAndSelect(selectedCategoryId) {
    fetch(`${BASE_URL}/category`)
        .then(handleResponse)
        .then(categories => {
            const filmCategorySelect = document.getElementById("film-category");
            filmCategorySelect.innerHTML = "";

            categories.forEach(category => {
                const option = document.createElement('option');
                option.value = category.idCategory;
                option.textContent = category.name;
                if (category.idCategory === selectedCategoryId) {
                    option.selected = true;
                }
                filmCategorySelect.appendChild(option);
            });
        })
        .catch(handleError);
}

    function deleteFilm(id) {
        if (confirm("Tem certeza que deseja excluir este filme?")) {
            fetch(`${BASE_URL}/film/${id}`, {
                method: 'DELETE'
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao excluir filme: ' + response.statusText);
                }
                alert('Filme excluído com sucesso!');
                loadFilms();
            })
            .catch(handleError);
        }
    }
});
