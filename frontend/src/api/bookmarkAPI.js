import apiClient from "./apiClient";

// Get all bookmarked items for a specific user
export async function getBookmarkedItems(userId) {
  try {
    const response = await apiClient.get(`/bookmarks/${userId}`);
    return response.data;
  } catch (error) {
    const errorMessage = error.response?.data?.message || "Failed to fetch bookmarked items";
    throw new Error(errorMessage);
  }
}

// Bookmark an item for a user
export async function bookmarkItem(userId, itemId) {
  try {
    await apiClient.post(`/bookmarks/${userId}/${itemId}`);
  } catch (error) {
    const errorMessage = error.response?.data?.message || "Failed to bookmark item";
    throw new Error(errorMessage);
  }
}

// Delete a bookmark for a user and item
export async function deleteBookmark(userId, itemId) {
  try {
    await apiClient.delete(`/bookmarks/${userId}/${itemId}`);
  } catch (error) {
    const errorMessage = error.response?.data?.message || "Failed to delete bookmark";
    throw new Error(errorMessage);
  }
}
