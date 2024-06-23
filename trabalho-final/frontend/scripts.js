// Definição da URL base da API
const BASE_URL = 'http://localhost:8080'; // Altere para a URL correta da sua API

// Função para buscar todas as categorias
function fetchCategories() {
        fetch(`${BASE_URL}/category`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
            },
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error fetching categories');
            }
            return response.json();
        })
        .then(categories => {
            // Atualiza o dropdown de categorias no formulário de filme
            const categorySelect = document.getElementById('idCategory');
            categorySelect.innerHTML = ''; // Limpa as opções atuais

            categories.forEach(category => {
                const option = document.createElement('option');
                option.value = category.idCategory;
                option.textContent = category.name;
                categorySelect.appendChild(option);
            });

            // Atualiza a lista de categorias na tela de filmes por categoria
            const categoryFilterSelect = document.getElementById('category-filter');
            categoryFilterSelect.innerHTML = ''; // Limpa as opções atuais

            categories.forEach(category => {
                const option = document.createElement('option');
                option.value = category.idCategory;
                option.textContent = category.name;
                categoryFilterSelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Error fetching categories:', error);
            alert('Error fetching categories. Please try again later.');
        });
}

// Função para buscar todos os filmes
function fetchFilms() {
    fetch(`${BASE_URL}/film`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error fetching films');
            }
            return response.json();
        })
        .then(films => {
            const filmList = document.getElementById('film-list');
            filmList.innerHTML = ''; // Limpa a lista atual de filmes

            films.forEach(film => {
                const listItem = document.createElement('li');
                listItem.textContent = `${film.title} - ${film.releaseYear}`;
                filmList.appendChild(listItem);
            });
        })
        .catch(error => {
            console.error('Error fetching films:', error);
            alert('Error fetching films. Please try again later.');
        });
}

// Evento de carregamento da página
document.addEventListener('DOMContentLoaded', function() {
    fetchCategories(); // Carrega as categorias

    // Evento de alteração no filtro de categorias
    document.getElementById('category-filter').addEventListener('change', function() {
        const categoryId = this.value;
        if (categoryId !== '') {
            fetchFilmsByCategory(categoryId); // Busca filmes por categoria selecionada
        } else {
            fetchFilms(); // Busca todos os filmes se nenhum filtro estiver selecionado
        }
    });

    fetchFilms(); // Carrega os filmes
});

// Função para cadastrar um novo filme
function saveFilm() {
    const filmForm = document.getElementById('film-form');
    const formData = new FormData(filmForm);

    fetch(`${BASE_URL}/film`, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
        },
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to save film');
        }
        return response.json();
    })
    .then(data => {
        console.log('Film saved successfully:', data);
        fetchFilms(); // Atualiza a lista de filmes após o cadastro
        filmForm.reset(); // Limpa o formulário após o cadastro
    })
    .catch(error => {
        console.error('Error saving film:', error);
        alert('Failed to save film. Please try again.');
    });
}

// Função para atualizar um filme existente
function updateFilm(filmId) {
    const filmForm = document.getElementById('film-form');
    const formData = new FormData(filmForm);

    fetch(`${BASE_URL}/film/${filmId}`, {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
        },
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to update film');
        }
        return response.json();
    })
    .then(data => {
        console.log('Film updated successfully:', data);
        fetchFilms(); // Atualiza a lista de filmes após a atualização
        filmForm.reset(); // Limpa o formulário após a atualização
    })
    .catch(error => {
        console.error('Error updating film:', error);
        alert('Failed to update film. Please try again.');
    });
}

// Função para deletar um filme
function deleteFilm(filmId) {
    fetch(`${BASE_URL}/film/${filmId}`, {
        method: 'DELETE',
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to delete film');
        }
        return response.json();
    })
    .then(data => {
        console.log('Film deleted successfully:', data);
        fetchFilms(); // Atualiza a lista de filmes após a exclusão
    })
    .catch(error => {
        console.error('Error deleting film:', error);
        alert('Failed to delete film. Please try again.');
    });
}
