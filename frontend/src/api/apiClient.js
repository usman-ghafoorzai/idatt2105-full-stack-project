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

// TODO: Handle token expiration

export default apiClient;