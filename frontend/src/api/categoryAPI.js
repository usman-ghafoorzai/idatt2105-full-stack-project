import apiClient from "./apiClient";

// Get all categories
export async function getAllCategories() {
  try {
    const response = await apiClient.get("categories");
    return response.data;
  } catch (error) {
    console.error("Error fetching categories:", error);
    throw new Error(error.response?.data?.message || "Failed to fetch categories");
  }
}

// Get category by ID, eventuelt også for kategori management i en admin bruker??
export async function getCategoryById(id) {
  try {
    const response = await apiClient.get(`categories/${id}`);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || "Failed to fetch category");
  }
}
