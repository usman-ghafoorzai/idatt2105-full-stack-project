import { defineStore } from 'pinia';


export const useItemStore = defineStore('item', {
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