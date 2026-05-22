import axios from "axios";
import { getToken, removeToken, saveToken } from "@/utils/authService";

const apiClient = axios.create({
  baseURL: "http://localhost:8888/api",
  headers: {
    "Content-Type": "application/json",
  },
});

apiClient.interceptors.request.use((config) => {
  const token = getToken();
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Handle errors globally
apiClient.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    // Handle auth errors (401 Unauthorized, 403 Forbidden)
    if ((error.response?.status === 401 || error.response?.status === 403)
      && !originalRequest._retry) {
      originalRequest._retry = true;

      // If we have a refresh token mechanism, we could use it here
      // For now, just log the error and clear the token on auth failures
      console.error("Authentication error:", error.response?.data?.message || "Access denied");

      // Remove invalid token on auth errors
      if (error.response?.status === 401) {
        removeToken();
      }
    }

    return Promise.reject(error);
  }
);

export default apiClient;
