import { defineStore } from 'pinia';
import { getSellerInformation } from '../api/userAPI'; // Adjust the import path as necessary


export const useSellerInformationStore = defineStore('seller', {
    state: () => ({
        sellerInformation: null,
        sellerId: 0,
    }),
    actions: {
        setSellerId(sellerId) {
            this.sellerId = sellerId;
        },
        // Fetch search results from the backend
        async getSellerInformation() {
            try {
                this.sellerInformation = await getSellerInformation(this.sellerId); 
            } catch (error) {
                console.error('Error fetching search results:', error);
            }
        },
    },
});