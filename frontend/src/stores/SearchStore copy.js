import { defineStore } from 'pinia';
import { fetchSearchResults } from '../api/searchItemsAPI'; // Adjust the import path as necessary

export const useSearchStore = defineStore('item', {
    state: () => ({
        selectedItem: null,
    }),
    actions: {
        // Set the search term
        setSelectedItem(item) {
            this.selectedItem = item;
        },
    },
});