import { defineStore } from 'pinia';
import { getAllCategories} from '../api/categoryAPI.js'; // Adjust the import path as necessary

export const useCategoryStore = defineStore('categories', {
    state: () => ({
        categories: [],
    }),
    actions: {
        async fetchCategories() {
            try {
                const response = await getAllCategories();
                this.categories = response;
                console.log(response);
            } catch (error) {
                console.error('Error fetching categories:', error);
            }
        },
    },
});