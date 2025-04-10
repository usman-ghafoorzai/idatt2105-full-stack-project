import { defineStore } from 'pinia';
import { fetchSearchResults } from '../api/searchItemsAPI'; // Adjust the import path as necessary

export const useSearchStore = defineStore('search', {
    state: () => ({
        searchTerm: '', // The term entered by the user
        searchResults: [], // The results fetched from the backend
    }),
    actions: {
        // Set the search term
        setSearchTerm(term) {
            this.searchTerm = term;
        },
        setSearchResults(results) {
            this.searchResults = results;
        },

        // Fetch search results from the backend
        async fetchSearchResults() {
            try {
                this.searchResults = await fetchSearchResults(this.searchTerm); 
                console.log('Search results:', this.searchResults); // Log the results for debugging
            } catch (error) {
                console.error('Error fetching search results:', error);
            }
        },
    },
});