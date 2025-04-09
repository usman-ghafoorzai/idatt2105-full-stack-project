import apiClient from "./apiClient";


// Get item by ID
export async function getItemById(id) {
  try {
    const response = await apiClient.get(`/items/${id}`);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || "Failed to fetch item");
  }
}

// Create new item
export async function createItem(itemData) {
  try {
    const response = await apiClient.post("/items", itemData);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.headers["error-message"] || "Failed to create item");
  }
}

// Update item
export async function updateItem(id, updatedFields) {
  try {
    const response = await apiClient.put(`/items/${id}`, updatedFields);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.headers["error-message"] || "Failed to update item");
  }
}

// Delete item
export async function deleteItem(id) {
  try {
    await apiClient.delete(`/items/${id}`);
  } catch (error) {
    throw new Error(error.response?.headers["error-message"] || "Failed to delete item");
  }
}

// Filter items
export async function getFilteredItems(filters = {}) {
  try {
    const response = await apiClient.get("/items", { params: filters });
    return response.data;
  } catch (error) {
    throw new Error("Failed to fetch filtered items");
  }
}

// Upload item images
export async function uploadItemImages(itemId, images) {
  const formData = new FormData();
  images.forEach((image) => {
    formData.append("images", image);
  });

  try {
    const response = await apiClient.post(`/items/${itemId}/images`, formData, {
      headers: { "Content-Type": "multipart/form-data" },
    });
    return response.data;
  } catch (error) {
    throw new Error("Image upload failed");
  }
}

// Get all images for an item
export async function getItemImages(itemId) {
  try {
    const response = await apiClient.get(`/items/${itemId}/images`);
    return response.data;
  } catch (error) {
    throw new Error("Failed to fetch item images");
  }
}

// Get single image blob
export async function getItemImage(itemId, imageId) {
  try {
    const response = await apiClient.get(`/items/${itemId}/images/${imageId}`, {
      responseType: "blob",
    });

    return URL.createObjectURL(response.data);
  } catch (error) {
    throw new Error("Failed to fetch item image");
  }
}

// Get first image of an item
export async function getFirstImage(itemId) {
  try {
    const response = await apiClient.get(`/items/${itemId}/images/first`, {
      responseType: "blob",
    });
    return URL.createObjectURL(response.data);
  } catch (error) {
    throw new Error("Failed to fetch first item image");
  }
}

// Delete specific image
export async function deleteItemImage(itemId, imageId) {
  try {
    await apiClient.delete(`/items/${itemId}/images/${imageId}`);
  } catch (error) {
    throw new Error("Failed to delete image");
  }
}

// Delete all images
export async function deleteAllItemImages(itemId) {
  try {
    await apiClient.delete(`/items/${itemId}/images`);
  } catch (error) {
    throw new Error("Failed to delete all images");
  }
}
