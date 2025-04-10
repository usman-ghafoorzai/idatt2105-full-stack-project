import apiClient from "./apiClient";

export async function fetchSearchResults(searchTerm) {
    try {
        console.log('Fetching search results for:', searchTerm);
        const response = await apiClient.get('/items', {
            params: { title: searchTerm }
        });
        console.log('API response:', response);
        return response.data;
    } catch (error) {
        console.error('Error fetching search results:', error);
        throw error;
    }
}

export async function fetchSearchResultsByCategory(searchTerm, category) {
    try {
        console.log('Fetching search results for category:', category);
        const response = await apiClient.get('/items', {
            params: { title: searchTerm}
        });
        
        const filteredResponse = response.data.filter(item =>
            item.categories.some(cat => cat.name === category)
        );

        console.log('API response:', response);
        return filteredResponse;
    } catch (error) {
        console.error('Error fetching search results:', error);
        throw error;
    }
}