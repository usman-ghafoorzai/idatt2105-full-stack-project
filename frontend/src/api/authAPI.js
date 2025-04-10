import apiClient from "./apiClient";
import { saveToken, getToken, removeToken } from "@/utils/authService";

export async function login(username, password) {
  try {
    const response = await apiClient.post("/auth/login", {
      username,
      password,
    });
    const token = response.data.token; // Assuming the token is in the response data
    saveToken(token); // Save the token to session storage

    return response.data; // Return the response data (including the token)
  } catch (error) {
    const errorMessage = error.response?.data?.message || "Login failed";
    throw new Error(errorMessage);
  }
}

export async function register({firstName, lastName, username, email, password}) {
  try {
    const response = await apiClient.post("/auth/register", {
      firstName,
      lastName,
      username,
      email,
      password,
      role: "USER", // Default role for new users
    });

    const token = response.data.token; // Assuming the token is in the response data
    saveToken(token); // Save the token to session storage

    return response.data; // Return the response data
  } catch (error) {
    const errorMessage = error.response?.data?.message || "Registration failed";
    throw new Error(errorMessage);
  }
}