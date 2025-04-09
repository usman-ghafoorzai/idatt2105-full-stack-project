import axios from 'axios';
import apiClient from "./apiClient";
import { saveToken, getToken, removeToken } from "@/utils/authService";

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