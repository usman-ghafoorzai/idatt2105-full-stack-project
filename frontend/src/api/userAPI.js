import apiClient from "./apiClient";

export async function getLoggedInUser() {
  try {
    const response = await apiClient.get("/users/me");
    return response.data;
  } catch (error) {
    const errorMessage = error.response?.data?.message || "Failed to fetch user data";
    throw new Error(errorMessage);
  }
}

export async function getUserImage(userId) {
  try {
    const response = await apiClient.get(`/users/${userId}/image`, {
      responseType: "blob", // Set response type to blob for image data
    });
    return URL.createObjectURL(response.data); // Create a URL for the image blob
  } catch (error) {
    const errorMessage = error.response?.data?.message || "Failed to fetch user image";
    throw new Error(errorMessage);
  }
}